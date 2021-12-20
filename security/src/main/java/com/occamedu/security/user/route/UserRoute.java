package com.occamedu.security.user.route;

import com.jfinal.config.Routes;
import com.occamedu.security.user.UserController;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-04-24 16:42
 * @description
 */
public class UserRoute extends Routes {
    @Override
    public void config() {
        add("/user", UserController.class);
    }
}
