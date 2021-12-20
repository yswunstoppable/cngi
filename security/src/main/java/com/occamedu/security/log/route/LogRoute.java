package com.occamedu.security.log.route;

import com.jfinal.config.Routes;
import com.occamedu.security.log.attack.AttackSourceLogController;
import com.occamedu.security.log.command.TimerCommandLogController;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 22:02
 * @description
 */
public class LogRoute extends Routes {
    @Override
    public void config() {
        add("/log/attack", AttackSourceLogController.class);
        add("/log/command", TimerCommandLogController.class);
    }
}
