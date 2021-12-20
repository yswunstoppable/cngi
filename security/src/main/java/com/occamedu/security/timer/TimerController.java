package com.occamedu.security.timer;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.kit.Kits;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.kit.Cron4jKit;
import com.occamedu.security.common.module.Timer;
import com.occamedu.security.common.module.TimerRelation;
import com.occamedu.security.common.module.base.BaseTimerRelation;
import com.occamedu.security.timer.constant.TimerConstants;
import com.occamedu.security.timer.relation.TimerRelationService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 17:21
 * @description
 */
@ValidateParam
public class TimerController extends Controller {
    @Inject
    private TimerService service;
    @Inject
    private TimerRelationService relationService;

    /**
     * 获取数据列表
     *
     * @param params 请求实体类
     */
    public void list(@Para("") RequestParams params) {
        Page<Timer> page = service.list(params.getPageNumber(), params.getPageSize());
        page.getList().forEach(item -> {
            item.put("boundaries", relationService.listBoundary(item.getId().longValue()));
            item.put("commands", relationService.listCommand(item.getId().longValue()));
        });
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 新增定时任务
     *
     * @param timer      对应实体
     * @param boundaries 相关边界
     * @param commands   相关命令
     */
    @Param(name = "sourceId", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "cronExpress", required = true)
    @Param(name = "boundaries", required = true)
    @Param(name = "commands", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void add(@Para("") Timer timer, String boundaries, String commands) {
        timer.setId(null);
        boolean flag = Db.tx(() -> {
            if (timer.save()) {
                List<String> idStrList = Kits.parseObjStrToList(boundaries);
                boolean f = idStrList.parallelStream().allMatch(idStr -> relationService.add(timer.getId().longValue(),
                        TimerConstants.Relation.BOUNDARY, Long.parseLong(idStr)));
                if (!f) {
                    return false;
                }
                idStrList = Kits.parseObjStrToList(commands);
                return idStrList.parallelStream().allMatch(idStr -> relationService.add(timer.getId().longValue(),
                        TimerConstants.Relation.COMMAND, Long.parseLong(idStr)));
            }
            return false;
        });
        if (flag) {
            renderJson(BaseResult.ok());
            if (timer.getStatus() == 1) {
                //执行任务
                Cron4jKit.put("id_" + timer.getId(), timer.getCronExpress(), true, new TimerRunnerService(timer.getId().longValue()));
            }
            return;
        }
        renderJson(BaseResult.fail());
    }

    /**
     * 更新定时控制
     *
     * @param timer      定时任务
     * @param boundaries 相关边界
     * @param commands   相关命令
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "sourceId", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "cronExpress", required = true)
    @Param(name = "boundaries", required = true)
    @Param(name = "commands", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void update(@Para("") Timer timer, String boundaries, String commands) {
        List<TimerRelation> relations = relationService.listByTimer(timer.getId().longValue());
        Map<Integer, List<TimerRelation>> map = relations.parallelStream().collect(Collectors.groupingBy(BaseTimerRelation::getType));
        boolean flag = Db.tx(() -> {
            if (timer.update()) {
                String _id;
                List<String> idStrList = Kits.parseObjStrToList(boundaries);
                for (TimerRelation relation : map.get(TimerConstants.Relation.BOUNDARY)) {
                    _id = String.valueOf(relation.getTargetId());
                    if (idStrList.contains(_id)) {
                        //包含当前边界，删除id中存在的
                        idStrList.remove(_id);
                    } else {
                        //不包含，则需要删除当前边界
                        if (!relationService.deleteByInnerSql(relation.getId().longValue())) {
                            return false;
                        }
                    }
                }
                //添加剩余的数据
                boolean f = idStrList.parallelStream().allMatch(idStr -> relationService.add(timer.getId().longValue(),
                        TimerConstants.Relation.BOUNDARY, Long.parseLong(idStr)));
                if (!f) {
                    return false;
                }
                idStrList = Kits.parseObjStrToList(commands);
                for (TimerRelation relation : map.get(TimerConstants.Relation.COMMAND)) {
                    _id = String.valueOf(relation.getTargetId());
                    if (idStrList.contains(_id)) {
                        //包含当前边界，删除id中存在的
                        idStrList.remove(_id);
                    } else {
                        //不包含，则需要删除当前边界
                        if (!relationService.deleteByInnerSql(relation.getId().longValue())) {
                            return false;
                        }
                    }
                }
                return idStrList.parallelStream().allMatch(idStr -> relationService.add(timer.getId().longValue(),
                        TimerConstants.Relation.COMMAND, Long.parseLong(idStr)));
            }
            return false;
        });
        if (flag) {
            //停止之前的表达式
            Cron4jKit.remove("id_" + timer.getId());
            if (timer.getStatus() == 1) {
                Cron4jKit.put("id_" + timer.getId(), timer.getCronExpress(), true, new TimerRunnerService(timer.getId().longValue()));
            }
            renderJson(BaseResult.ok());
            return;
        }
        renderJson(BaseResult.fail());
    }

    /**
     * 修改定时任务状态
     *
     * @param id     主键
     * @param status 状态值
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "status", required = true, customRule = "1|2")
    public void changeStatus(long id, int status) {
        Timer timer = service.get(id);
        timer.setStatus(status);
        if (timer.update()) {
            if (status == 1) {
                Cron4jKit.put("id_" + timer.getId(), timer.getCronExpress(), true, new TimerRunnerService(timer.getId().longValue()));
            } else {
                Cron4jKit.remove("id_" + timer.getId());
            }
            renderJson(BaseResult.ok());
            return;
        }
        renderJson(BaseResult.fail());
    }
}
