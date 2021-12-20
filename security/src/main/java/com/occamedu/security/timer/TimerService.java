package com.occamedu.security.timer;

import cn.fabrice.jfinal.service.BaseService;
import com.jfinal.aop.Aop;
import com.occamedu.security.common.kit.Cron4jKit;
import com.occamedu.security.common.module.Timer;

import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 17:21
 * @description
 */
public class TimerService extends BaseService<Timer> {

    public TimerService() {
        super("timer.", Timer.class, "timer");
    }


    /**
     * 执行所有正在执行的任务列表
     */
    public void start() {
        List<Timer> timerList = list("listUsed");
        timerList.parallelStream().forEach(timer -> Cron4jKit.put("id_" + timer.getId(), timer.getCronExpress(), true, new TimerRunnerService(timer.getId().longValue())));
    }
}
