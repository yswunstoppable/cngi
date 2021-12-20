package com.occamedu.security.whitelist;

import cn.fabrice.common.pojo.BasePageRequestParams;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 16:34
 * @description
 */
public class RequestParams extends BasePageRequestParams {
    private String address;
    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
