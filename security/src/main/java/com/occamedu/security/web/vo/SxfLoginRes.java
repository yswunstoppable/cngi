package com.occamedu.security.web.vo;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 22:29
 * @description
 */
public class SxfLoginRes {

    /**
     * success : false
     * message : 用户名或密码错误
     */

    private boolean success;
    private String message;
    private String data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
