package com.occamedu.security.log.command;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.TimerCommandLog;

import java.math.BigInteger;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 21:21
 * @description
 */
public class TimerCommandLogService extends BaseService<TimerCommandLog> {
    public TimerCommandLogService() {
        super("timerCommandLog.", TimerCommandLog.class, "timer_command_log");
    }

    public TimerCommandLog add(long timerLogId, long boundaryId, long commandId, String ipAddress, int isWhite, String commandContent) {
        TimerCommandLog commandLog = new TimerCommandLog();
        commandLog.setTimerLogId(BigInteger.valueOf(timerLogId));
        commandLog.setBoundaryId(BigInteger.valueOf(boundaryId));
        commandLog.setCommandId(BigInteger.valueOf(commandId));
        commandLog.setIpAddress(ipAddress);
        commandLog.setIsWhite(isWhite);
        commandLog.setCommandContent(commandContent);
        return commandLog.save() ? commandLog : null;
    }
}
