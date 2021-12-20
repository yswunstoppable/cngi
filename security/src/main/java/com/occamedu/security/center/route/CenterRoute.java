package com.occamedu.security.center.route;

import com.jfinal.config.Routes;
import com.occamedu.security.center.boundary.BoundaryController;
import com.occamedu.security.center.command.CommandController;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:03
 * @description
 */
public class CenterRoute extends Routes {
    @Override
    public void config() {
        add("/boundary", BoundaryController.class);
        add("/command", CommandController.class);
    }
}
