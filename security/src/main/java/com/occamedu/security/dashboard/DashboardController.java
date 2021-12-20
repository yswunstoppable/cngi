package com.occamedu.security.dashboard;

import cn.fabrice.common.pojo.DataResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.occamedu.security.user.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-05-18 16:24
 * @description
 */
public class DashboardController extends Controller {
    @Inject
    private UserService userService;

    public void index() {
        Map<String, Object> resMap = new HashMap<>(3);
        resMap.put("userNumber", userService.countUser());
        renderJson(DataResult.data(resMap));
    }
}
