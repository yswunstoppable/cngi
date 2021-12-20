package com.occamedu.security.user;

import cn.fabrice.common.pojo.BasePageRequestParams;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-05-13 18:17
 * @description
 */
public class RequestParams extends BasePageRequestParams {
    private String account;
    private String name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
