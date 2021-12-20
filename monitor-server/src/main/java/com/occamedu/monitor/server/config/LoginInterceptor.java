package com.occamedu.monitor.server.config;

import com.occamedu.monitor.server.entity.AccountInfo;
import com.occamedu.monitor.server.util.staticvar.StaticKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Sandwich on 2018-08-13
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    CommonConfig commonConfig;

    String[] static_resource = {"/agent/minTask", "/agentGo/minTask", "login/toLogin", "login/login", "appInfo/agentList",
            "/code/get", ".css", ".js", ".jpg", ".png", ".ico", ".gif", "font", ".eot", ".woff", ".svg", ".ttf", ".woff2"};

    String[] dash_views = {"/dash/main", "/dash/systemInfoList", "/dash/detail", "/dash/chart"};
    /**
     * 在请求被处理之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        final HttpSession session = request.getSession();
        String uri = request.getRequestURL().toString();
        log.info("uri----" + request.getRequestURL());
        menuActive(session, uri);
        for (String ss : static_resource) {
            if (uri.contains(ss)) {
                return true;
            }
        }
        return true;
    }

    /**
     * 添加菜单标识
     *
     * @param session
     * @param uri
     */
    public void menuActive(HttpSession session, String uri) {
        if (uri.indexOf("/log/") > -1) {
            session.setAttribute("menuActive", "21");
            return;
        }
        if (uri.indexOf("/dash/main") > -1) {
            session.setAttribute("menuActive", "11");
            return;
        }
        if (uri.indexOf("/dash/systemInfoList") > -1 || uri.indexOf("/dash/detail") > -1 || uri.indexOf("/dash/chart") > -1) {
            session.setAttribute("menuActive", "12");
            return;
        }
        if (uri.indexOf("/appInfo") > -1) {
            session.setAttribute("menuActive", "13");
            return;
        }
        if (uri.indexOf("/mailset") > -1) {
            session.setAttribute("menuActive", "31");
            return;
        }
        if (uri.indexOf("/dbInfo") > -1) {
            session.setAttribute("menuActive", "41");
            return;
        }
        if (uri.indexOf("/dbTable") > -1) {
            session.setAttribute("menuActive", "42");
            return;
        }
        if (uri.indexOf("/heathMonitor") > -1) {
            session.setAttribute("menuActive", "51");
            return;
        }
        session.setAttribute("menuActive", "11");
        return;

    }

    /**
     * 在请求被处理后，视图渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
