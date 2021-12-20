package com.occamedu.security.center.command;

import cn.fabrice.common.pojo.BasePageRequestParams;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:30
 * @description
 */
public class RequestParams extends BasePageRequestParams {
    private String name;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
