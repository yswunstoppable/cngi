package com.occamedu.security.user;

import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.jfinal.service.BaseService;
import com.jfinal.aop.Aop;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.ehcache.CacheKit;
import com.occamedu.security.common.module.User;
import com.occamedu.security.common.module.UserSession;
import com.occamedu.security.user.constant.UserConstants;
import com.occamedu.security.user.session.SessionService;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-04-24 11:29
 * @description
 */
public class UserService extends BaseService<User> {
    /**
     * 用户session业务处理类
     */
    private SessionService sessionService = Aop.get(SessionService.class);

    public UserService() {
        super("user.", User.class, "user");
    }


    /**
     * 登录结果
     *
     * @param user 对应实体类
     * @return 登录结果集
     */
    public BaseResult login(User user, boolean keepLogin) {
        //只允许单个地方登录，此时判断该用户是否存在session信息
        UserSession userSession = sessionService.getByAccount(user.getId().longValue());
        if (userSession != null) {
            //判断信息是否过期
            if (!userSession.isExpired()) {
                String token = userSession.getSessionId();
                //信息未过期，则处理
                if (userSession.delete()) {
                    CacheKit.remove(UserConstants.ACCOUNT_CACHE_NAME, token);
                } else {
                    return BaseResult.res(UserConstants.Result.ACCOUNT_IS_LOGON);
                }
            }
        }
        // 如果用户勾选保持登录，暂定过期时间为 3 年，否则为 7 天，单位为秒
        long liveSeconds = keepLogin ? 94608000 : 604800;
        userSession = sessionService.add(user.getId().longValue(), liveSeconds);
        if (userSession == null) {
            return BaseResult.fail(UserConstants.Message.ACCOUNT_SESSION_SAVED_FAIL);
        }
        //登录成功，放入缓存
        CacheKit.put(UserConstants.ACCOUNT_CACHE_NAME, userSession.getSessionId(), userSession);
        user.put(UserConstants.TOKEN_NAME, userSession.getSessionId());
        // 模拟登录monitor
        return DataResult.data(user);
    }

    /**
     * 通过账号密码获取用户信息
     *
     * @param account  登录账号
     * @param password 登录密码
     * @return 用户实体
     */
    public User getByAccount(String account, String password) {
        Kv cond = Kv.by("account", account).set("password", password);
        return get(cond, "login");
    }

    /**
     * 获取操作员总数
     *
     * @return 操作员总数
     */
    public long countUser() {
        return get("getTotal").getLong("number");
    }
}
