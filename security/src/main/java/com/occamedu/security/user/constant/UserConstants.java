package com.occamedu.security.user.constant;

import cn.fabrice.common.constant.BaseResultConstants;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-10-06 14:23
 * @description 用户相关常量类
 */
public class UserConstants {
    /**
     * 用户session存储的名称
     */
    public static final String TOKEN_NAME = "token";
    /**
     * 登陆账号
     */
    public static final String ACCOUNT = "Account";
    /**
     * 登录账号ID
     */
    public static final String ACCOUNT_ID = "AccountId";

    public static final String ACCOUNT_CACHE_NAME = "loginAccount";

    /**
     * 结果常量类
     */
    public static final class Result {
        /**
         * 账号不存在
         */
        public static final int ACCOUNT_NOT_EXIST = 50001;
        /**
         * 账号被禁用
         */
        public static final int ACCOUNT_IS_FORBIDDEN = 50002;
        /**
         * 违法的token信息
         */
        public static final int ILLEGAL_TOKEN = 50003;
        /**
         * 过期的token信息
         */
        public static final int EXPIRED_TOKEN = 50004;
        /**
         * 保存短信验证码失败
         */
        public static final int SAVE_MOBILE_CODE_FAIL = 50005;
        /**
         * 账号已被登录，清空已登录账号失败
         */
        public static final int ACCOUNT_IS_LOGON = 50006;

        /**
         * 赋值父类，填充返回消息值
         */
        public static void init() {
            BaseResultConstants.addResultInfo(ILLEGAL_TOKEN, Message.ILLEGAL_TOKEN);
            BaseResultConstants.addResultInfo(EXPIRED_TOKEN, Message.EXPIRED_TOKEN);
            BaseResultConstants.addResultInfo(ACCOUNT_NOT_EXIST, Message.ACCOUNT_NOT_EXIST);
            BaseResultConstants.addResultInfo(ACCOUNT_IS_FORBIDDEN, Message.ACCOUNT_IS_FORBIDDEN);
            BaseResultConstants.addResultInfo(SAVE_MOBILE_CODE_FAIL, Message.SAVE_MOBILE_CODE_FAIL);
            BaseResultConstants.addResultInfo(ACCOUNT_IS_LOGON, Message.ACCOUNT_IS_LOGON);
        }
    }

    /**
     * 消息常量类
     */
    public static final class Message {
        public static final String ILLEGAL_TOKEN = "违法的TOKEN信息";
        public static final String EXPIRED_TOKEN = "TOKEN信息已过期";
        public static final String ACCOUNT_NOT_EXIST = "账号不存在";
        public static final String ACCOUNT_IS_FORBIDDEN = "账号被禁用";
        public static final String ACCOUNT_SESSION_SAVED_FAIL = "账号session保存失败，请联系系统管理员";
        public static final String SAVE_MOBILE_CODE_FAIL = "保存短信验证码失败";
        public static final String MOBILE_CODE_INVALID = "手机号和验证码信息无效";
        public static final String ACCOUNT_IS_LOGON = "账号已被登录，清空已登录账号失败";
        public static final String MOBILE_PHONE_IS_BIND = "手机号已被其他用户绑定";
        public static final String SAVE_MOBILE_PHONE_FAIL = "保存手机号失败";
        public static final String MOBILE_CODE_NOT_EXIST = "短信验证码不存在";
        public static final String VERIFY_CODE_ERROR = "验证码错误";
    }
}
