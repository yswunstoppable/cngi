package com.occamedu.security.timer.log;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.TimerRunnerLog;

import java.math.BigInteger;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 20:14
 * @description
 */
public class TimerRunnerLogService extends BaseService<TimerRunnerLog> {
    public TimerRunnerLogService() {
        super("timerRunnerLog", TimerRunnerLog.class, "timer_runner_log");
    }

    public TimerRunnerLog add(long timerId, int commandNumber) {
        TimerRunnerLog runnerLog = new TimerRunnerLog();
        runnerLog.setTimerId(BigInteger.valueOf(timerId));
        runnerLog.setCommandNumber(commandNumber);
        runnerLog.setCommandSuccessNumber(0);
        return runnerLog.save() ? runnerLog : null;
    }
}
