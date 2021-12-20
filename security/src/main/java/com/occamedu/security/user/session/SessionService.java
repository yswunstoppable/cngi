package com.occamedu.security.user.session;

import cn.fabrice.kit.Kits;
import cn.fabrice.jfinal.service.BaseService;
import com.jfinal.kit.Kv;
import com.occamedu.security.common.module.UserSession;

import java.math.BigInteger;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-12-15 14:34
 * @description
 */
public class SessionService extends BaseService<UserSession> {
    public SessionService() {
        super("session.", UserSession.class, "user_session");
    }

    /**
     * 添加用户登陆账号session存储
     *
     * @param accountId 登陆账号
     * @param expiresIn session有效时间（s）
     * @return 操作成功-session实体类/操作失败-null
     */
    public UserSession add(long accountId, long expiresIn) {
        UserSession userSession = new UserSession();
        userSession.setUserId(BigInteger.valueOf(accountId));
        userSession.setExpiresIn(expiresIn);
        //记录过期时间戳（毫秒）
        userSession.setExpiresTime(System.currentTimeMillis() + expiresIn * 1000);
        userSession.setSessionId(Kits.getUuid());
        return userSession.save() ? userSession : null;
    }

    /**
     * 通过token获取用户session信息
     *
     * @param token token信息
     * @return 存在-返回对应实体类/不存在-null
     */
    public UserSession getByToken(String token) {
        return get(Kv.by("sessionId", token), "getByToken");
    }

    /**
     * 通过账号ID获取用户session信息
     *
     * @param accountId 账号ID
     * @return 存在-返回对应实体类/不存在-null
     */
    public UserSession getByAccount(long accountId) {
        return get(Kv.by("accountId", accountId), "getByAccount");
    }

    /**
     * 根据账号删除UserSession
     *
     * @param accountId 账号ID
     * @return 操作成功-true/操作失败-false
     */
    public boolean deleteByAccount(long accountId) {
        return update(Kv.by("accountId", accountId), "deleteByAccount") >= 1;
    }

    /**
     * 根据token删除登录信息
     *
     * @param token 对应数据表的session_id
     * @return 操作成功-true/操作失败-false
     */
    public boolean deleteByToken(String token) {
        return update(Kv.by("token", token), "deleteByToken") == 1;
    }
}
