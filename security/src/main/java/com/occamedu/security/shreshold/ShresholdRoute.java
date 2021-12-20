package com.occamedu.security.shreshold;

import com.jfinal.config.Routes;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 22:02
 * @description
 */
public class ShresholdRoute extends Routes {
    @Override
    public void config() {
        add("/shreshold", ShresholdController.class);
    }
}
