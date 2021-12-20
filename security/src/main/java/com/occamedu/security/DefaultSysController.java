package com.occamedu.security;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.occamedu.security.common.interceptor.AuthInterceptor;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-04-24 18:57
 * @description
 */
@Clear(AuthInterceptor.class)
public class DefaultSysController extends Controller {

}
