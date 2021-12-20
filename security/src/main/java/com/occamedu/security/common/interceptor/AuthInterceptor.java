package com.occamedu.security.common.interceptor;

import cn.fabrice.common.pojo.BaseResult;
import com.jfinal.aop.Aop;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.occamedu.security.common.module.UserSession;
import com.occamedu.security.user.constant.UserConstants;
import com.occamedu.security.user.session.SessionService;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-06-28 14:44
 * @description 认证拦截器
 */
public class AuthInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        if (StrKit.notBlank(controller.getHeader(UserConstants.TOKEN_NAME))) {
            String token = controller.getHeader(UserConstants.TOKEN_NAME);
            SessionService sessionService = Aop.get(SessionService.class);
            //获取session实体
            UserSession userSession = CacheKit.get(UserConstants.ACCOUNT_CACHE_NAME, token,
                    () -> sessionService.getByToken(token));
            if (userSession == null) {
                controller.renderJson(BaseResult.res(UserConstants.Result.ILLEGAL_TOKEN));
                return;
            }
            if (userSession.isExpired()) {
                controller.renderJson(BaseResult.res(UserConstants.Result.EXPIRED_TOKEN));
                return;
            }
            controller.setAttr(UserConstants.ACCOUNT, userSession);
            controller.setAttr(UserConstants.ACCOUNT_ID, userSession.getUserId().longValue());
            invocation.invoke();
            return;
        }
        controller.renderJson(BaseResult.res(UserConstants.Result.ILLEGAL_TOKEN));
    }
}
