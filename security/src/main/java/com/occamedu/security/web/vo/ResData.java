package com.occamedu.security.web.vo;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 09:24
 * @description
 */
public class ResData {
    private String systemInfo;
    private String cookie;

    public ResData(String systemInfo, String cookie) {
        this.systemInfo = systemInfo;
        this.cookie = cookie;
    }

    public String getSystemInfo() {
        return systemInfo;
    }

    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
