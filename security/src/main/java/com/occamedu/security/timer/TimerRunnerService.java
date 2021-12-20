package com.occamedu.security.timer;

import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Aop;
import com.occamedu.security.common.kit.SshKitWithExpect4j;
import com.occamedu.security.common.module.*;
import com.occamedu.security.log.attack.AttackSourceLogService;
import com.occamedu.security.log.command.TimerCommandLogService;
import com.occamedu.security.source.SourceService;
import com.occamedu.security.source.constant.SourceConstants;
import com.occamedu.security.timer.log.TimerRunnerLogService;
import com.occamedu.security.timer.relation.TimerRelationService;
import com.occamedu.security.web.kit.LmKit;
import com.occamedu.security.web.kit.SxfKit;
import com.occamedu.security.web.vo.AttackInfo;
import com.occamedu.security.web.vo.LmAttackInfo;
import com.occamedu.security.whitelist.WhiteListService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 19:52
 * @description
 */
public class TimerRunnerService implements Runnable {
    private long timerId;

    private TimerService timerService = Aop.get(TimerService.class);
    private TimerRunnerLogService timerRunnerLogService = Aop.get(TimerRunnerLogService.class);
    private TimerRelationService relationService = Aop.get(TimerRelationService.class);
    private SourceService sourceService = Aop.get(SourceService.class);
    private AttackSourceLogService attackSourceLogService = Aop.get(AttackSourceLogService.class);
    private TimerCommandLogService commandLogService = Aop.get(TimerCommandLogService.class);
    private WhiteListService whiteListService = Aop.get(WhiteListService.class);

    public TimerRunnerService(long timerId) {
        this.timerId = timerId;
    }

    @Override
    public void run() {
        //获取此次待执行的命令
        List<TimerRelation> commandList = relationService.listCommand(this.timerId);
        //获取此次待执行的边界
        List<TimerRelation> boundaryList = relationService.listBoundary(this.timerId);
        //记录此次执行
        TimerRunnerLog runnerLog = timerRunnerLogService.add(this.timerId, commandList.size() * boundaryList.size() * 5);
        if (runnerLog == null) {
            return;
        }
        Timer timer = timerService.get(this.timerId);
        PerceptionSource perceptionSource = sourceService.get(timer.getSourceId().longValue());
        //获取此次感知源对应的top5
        Map<String, String> cookies = JSONObject.parseObject(perceptionSource.getCookie(), Map.class);
        String protocol = SourceConstants.PROTOCOL_MAP.get(perceptionSource.getProtocolType());
        String host = perceptionSource.getHost();
        List<String> ipList = new ArrayList<>();
        if (perceptionSource.getPerceptionPlatform() == SourceConstants.Platform.SXF) {
            //获取高危攻击top5
            List<AttackInfo> attackInfoList = SxfKit.getHighRiskTop5(cookies, protocol, host);
            if (!attackInfoList.isEmpty()) {
                //抓取有效，进行存储
                attackSourceLogService.addBySxf(runnerLog.getId().longValue(), SourceConstants.Top5Type.SXF_HIGH_RISK, attackInfoList);
                ipList.addAll(attackInfoList.parallelStream().map(AttackInfo::getSrc_ip).collect(Collectors.toList()));
            }
            //获取残余攻击top5
            attackInfoList = SxfKit.getRemnantTop5(cookies, protocol, host);
            if (!attackInfoList.isEmpty()) {
                //抓取有效，进行存储
                attackSourceLogService.addBySxf(runnerLog.getId().longValue(), SourceConstants.Top5Type.SXF_REMNANT, attackInfoList);
                ipList.addAll(attackInfoList.parallelStream().map(AttackInfo::getSrc_ip).collect(Collectors.toList()));
            }
            //获取暴力破解top5
//            attackInfoList = SxfKit.getViolenceTop5(cookies, protocol, host);
//            if (!attackInfoList.isEmpty()) {
//                //抓取有效，进行存储
//                attackSourceLogService.addBySxf(runnerLog.getId().longValue(), SourceConstants.Top5Type.SXF_VIOLENCE, attackInfoList);
//                ipList.addAll(attackInfoList.parallelStream().map(AttackInfo::getSrc_ip).collect(Collectors.toList()));
//            }
            runnerLog.setIsSuccess(1);
            runnerLog.update();
        } else {
            List<LmAttackInfo> attackInfoList = LmKit.getTop5(cookies, protocol, host);
            if (!attackInfoList.isEmpty()) {
                //抓取有效，进行存储
                runnerLog.setIsSuccess(1);
                runnerLog.setTop5Info(JSONObject.toJSONString(attackInfoList));
                runnerLog.update();
                attackSourceLogService.addByLm(runnerLog.getId().longValue(), SourceConstants.Top5Type.LM, attackInfoList);
                ipList = attackInfoList.parallelStream().map(LmAttackInfo::getIp).collect(Collectors.toList());
            } else {
                return;
            }
        }
        //执行命令
        int isWhite;
        for (TimerRelation boundary : boundaryList) {
            for (TimerRelation command : commandList) {
                for (String ip : ipList) {
                    isWhite = whiteListService.isWhite(ip) ? 1 : 0;
                    if (isWhite == 1) {
                        runnerLog.setCommandNumber(runnerLog.getCommandNumber() - 1);
                    }
                    String commandContent = command.getStr("content").replaceAll("\\$\\{ip\\}", ip + "/32");
                    String[] commandContentList = commandContent.split("\n");
                    TimerCommandLog commandLog = commandLogService.add(runnerLog.getId().longValue(), boundary.getId().longValue(), command.getId().longValue(),
                            ip, isWhite, commandContent);
                    if (isWhite == 0) {
                        SshKitWithExpect4j sshKitWithExpect4j = new SshKitWithExpect4j(boundary.getStr("ip_address"), boundary.getStr("username"), boundary.getStr("password"));
                        BaseResult baseResult = sshKitWithExpect4j.executeCommands(commandContentList, 1000);
                        if (baseResult.isOk()) {
                            //命令发送成功
                            DataResult dataResult = (DataResult) baseResult;
                            commandLog.setIsSuccess(1);
                            commandLog.setCommandResult(String.valueOf(dataResult.getData()));
                            runnerLog.setCommandSuccessNumber(runnerLog.getCommandSuccessNumber() + 1);
                        } else {
                            //命令发送失败
                            commandLog.setIsSuccess(0);
                            commandLog.setErrMsg(baseResult.getMsg());
                        }
                    } else {
                        commandLog.setIsSuccess(2);
                    }
                    commandLog.update();
                }
            }
        }
        runnerLog.update();
    }
}
