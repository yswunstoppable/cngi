package com.occamedu.monitor.server.util.msg;

import com.occamedu.monitor.server.common.ApplicationContextHelper;
import com.occamedu.monitor.server.common.SpringHolder;
import com.occamedu.monitor.server.config.WarnConfig;
import com.occamedu.monitor.server.entity.CpuState;
import com.occamedu.monitor.server.entity.MemState;
import com.occamedu.monitor.server.entity.SmsSet;
import com.occamedu.monitor.server.entity.SystemInfo;
import com.occamedu.monitor.server.service.LogInfoService;
import com.occamedu.monitor.server.sms.SmsTemplate;
import com.occamedu.monitor.server.sms.SmsUtil;
import com.occamedu.monitor.server.util.staticvar.StaticKeys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version v2.3
 * @ClassName:WarnMailUtil.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: WarnMailUtil.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
public class WarnSmsUtil {

    private static final Logger logger = LoggerFactory.getLogger(WarnSmsUtil.class);

    private static LogInfoService logInfoService = (LogInfoService) ApplicationContextHelper.getBean(LogInfoService.class);
    private static WarnConfig warnConfig = (WarnConfig) ApplicationContextHelper.getBean(WarnConfig.class);


    /**
     * 判断系统内存使用率是否超过98%，超过则发送告警短信
     *
     * @param memState
     * @return
     */
    public static boolean sendWarnInfo(MemState memState) {
        if (StaticKeys.smsSet == null) {
            return false;
        }
        SmsSet smsSet = StaticKeys.smsSet;
        String key = memState.getHostname();
        if (!StringUtils.isEmpty(WarnPools.MEM_WARN_MAP.get(key))) {
            return false;
        }
        if (memState.getUsePer() != null && memState.getUsePer() >= warnConfig.getMemWarnVal()) {
            try {
                //发送短信
                SmsUtil smsUtil = SpringHolder.getBean(SmsUtil.class);
                smsUtil.sendMsg(smsSet.getToMobile(), SmsTemplate.MEM_USED_WARN.getSmsTemp(),memState.getHostname(),memState.getUsePer().toString());
            } catch (Exception e) {
                logger.error("发送内存告警短信失败：", e);
                logInfoService.save("发送内存告警短信错误", e.toString(), StaticKeys.LOG_ERROR);
            }
        }

        return false;
    }

    /**
     * 判断系统cpu使用率是否超过98%，超过则发送告警短信
     *
     * @param cpuState
     * @return
     */
    public static boolean sendCpuWarnInfo(CpuState cpuState) {
        if (StaticKeys.smsSet == null) {
            return false;
        }
        SmsSet smsSet = StaticKeys.smsSet;
        if (StaticKeys.NO_SEND_WARN.equals(warnConfig.getAllWarnMail()) || StaticKeys.NO_SEND_WARN.equals(warnConfig.getCpuWarnMail())) {
            return false;
        }
        String key = cpuState.getHostname();
        if (!StringUtils.isEmpty(WarnPools.MEM_WARN_MAP.get(key))) {
            return false;
        }
        if (cpuState.getSys() != null && cpuState.getSys() >= warnConfig.getCpuWarnVal()) {
            try {
                //发送短信
                SmsUtil smsUtil = SpringHolder.getBean(SmsUtil.class);
                smsUtil.sendMsg(smsSet.getToMobile(), SmsTemplate.CPU_USED_WARN.getSmsTemp(),cpuState.getHostname(),cpuState.getSys().toString());
            } catch (Exception e) {
                logger.error("发送内存告警短信失败：", e);
                logInfoService.save("发送内存告警短信错误", e.toString(), StaticKeys.LOG_ERROR);
            }
        }

        return false;
    }



    /**
     * 主机下线发送告警短信
     *
     * @param systemInfo 主机信息
     * @param isDown     是否是下线告警，true下线告警，false上线恢复
     * @return
     */
    public static boolean sendHostDown(SystemInfo systemInfo, boolean isDown) {
        if (StaticKeys.smsSet == null) {
            return false;
        }
        SmsSet smsSet = StaticKeys.smsSet;
        if (StaticKeys.NO_SEND_WARN.equals(warnConfig.getAllWarnMail()) || StaticKeys.NO_SEND_WARN.equals(warnConfig.getHostDownWarnMail())) {
            return false;
        }
        String key = systemInfo.getId();
        if (isDown) {
            if (!StringUtils.isEmpty(WarnPools.MEM_WARN_MAP.get(key))) {
                return false;
            }
            try {
                String title = "主机下线告警：" + systemInfo.getHostname();
                String commContent = "主机已经超过10分钟未上报数据，可能已经下线：" + systemInfo.getHostname() + "，备注：" + systemInfo.getHostRemark()
                        + "。如果不再监控该主机在列表删除即可，同时不会再收到该主机告警短信";
                //发送短信
                SmsUtil smsUtil = SpringHolder.getBean(SmsUtil.class);
                smsUtil.sendMsg(smsSet.getToMobile(), SmsTemplate.HOST_DOWN_WARN.getSmsTemp(),systemInfo.getHostname());
            } catch (Exception e) {
                logger.error("发送主机下线告警短信失败：", e);
                logInfoService.save("发送主机下线告警短信错误", e.toString(), StaticKeys.LOG_ERROR);
            }
        }
        return false;
    }



}
