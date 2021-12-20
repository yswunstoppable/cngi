package com.occamedu.security.user;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;
import com.occamedu.security.common.interceptor.AuthInterceptor;
import com.occamedu.security.common.module.User;
import com.occamedu.security.common.module.UserSession;
import com.occamedu.security.user.constant.UserConstants;
import com.occamedu.security.user.session.SessionService;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-04-24 16:42
 * @description 用户相关控制类
 */
@ValidateParam
public class UserController extends Controller {
    @Inject
    private UserService service;
    @Inject
    private SessionService sessionService;

    /**
     * 用户登陆
     *
     * @param keepLogin 是否保持登录
     * @param username  登陆账号
     * @param password  登陆密码
     */
    @Clear(AuthInterceptor.class)
    @Param(name = "username", required = true)
    @Param(name = "password", required = true)
    @Param(name = "keepLogin", rule = ValidateRuleConstants.Key.BOOLEAN)
    public void login(boolean keepLogin, String username, String password) {
        User user = service.getByAccount(username, password);
        if (user == null) {
            renderJson(BaseResult.fail(UserConstants.Message.ACCOUNT_NOT_EXIST));
            return;
        }
        renderJson(service.login(user, keepLogin));
    }

    /**
     * 获取当前用户信息
     */
    public void getInfo() {
        long uid = getAttr(UserConstants.ACCOUNT_ID);
        User user = service.get(uid);
        renderJson(DataResult.data(user));
    }

    /**
     * 退出登录
     */
    public void logout() {
        UserSession session = getAttr(UserConstants.ACCOUNT);
        String token = session.getSessionId();
        if (sessionService.deleteByToken(token)) {
            //同时删除缓存token
            CacheKit.remove(UserConstants.ACCOUNT_CACHE_NAME, token);
            renderJson(BaseResult.ok());
            return;
        }
        renderJson(BaseResult.fail());
    }

    /**
     * 获取用户列表
     *
     * @param params 请求参数实体类
     */
    public void list(@Para("") RequestParams params) {
        Kv cond = Kv.by("rq", params);
        Page<User> page = service.list(params.getPageNumber(), params.getPageSize(), cond);
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 判断参数值是否重复
     *
     * @param id    ID
     * @param field 校验字段
     * @param value 校验字段值
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ZERO_POSITIVE_INTEGER)
    @Param(name = "field", required = true, customRule = "(account)|(username)")
    public void judge(long id, String field, String value) {
        boolean flag = service.isExistByInnerSql(id, field, value);
        renderJson(flag ? DataResult.data("fail") : DataResult.data("ok"));
    }

    /**
     * 新增用户
     *
     * @param user 对应实体
     */
    @Param(name = "username", required = true)
    @Param(name = "account", required = true)
    @Param(name = "password", required = true)
    @Param(name = "isSuper", required = true, customRule = "0|1")
    public void add(@Para("") User user) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(user, "username", "account");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(user.save() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 编辑用户
     *
     * @param user 对应实体
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ID)
    @Param(name = "username", required = true)
    @Param(name = "account", required = true)
    @Param(name = "password", required = true)
    @Param(name = "isSuper", required = true, customRule = "0|1")
    public void update(@Para("") User user) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(user, "username", "account");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(user.update() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 删除
     *
     * @param ids 待删除的ID字符串，多个之间以逗号分隔
     */
    @Param(name = "ids", required = true)
    public void delete(String ids) {
        renderJson(service.deleteByInnerSql(ids) ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    public void resetPass(long id) {
        Kv cond = Kv.by("id", id).set("password", PropKit.get("defaultPassword"));
        renderJson(service.update(cond, "resetPass") == 1 ? BaseResult.ok() : BaseResult.fail());
    }
}
