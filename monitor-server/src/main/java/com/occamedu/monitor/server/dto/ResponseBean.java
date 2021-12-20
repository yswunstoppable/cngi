package com.occamedu.monitor.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kco1989
 * @email kco1989@qq.com
 * @date 2019-05-08 14:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {
    private int code;
    private String message;
    private Object data;
    private boolean success;
    private long time;

    public static ResponseBean success() {
        return success(null, "");
    }


    public static ResponseBean success(Object data) {
        return success(data, "");
    }


    public static ResponseBean success(Object data, String message) {
        return new ResponseBean(message, data, System.currentTimeMillis());
    }

    public ResponseBean(String message, Object data, long time) {
        this.code = 200;
        this.message = message;
        this.data = data;
        this.time = time;
        this.success = true;
    }

    public static ResponseBean error(int code, String message, Object data) {
        return new ResponseBean(code, message, data, false, System.currentTimeMillis());
    }

    public static ResponseBean error(int code, String message) {
        return error(code, message, null);
    }

    public static ResponseBean error() {
        return error("");
    }

    public static ResponseBean error(String message) {
        return error(400, message, null);
    }

    public static ResponseBean error(String message, Object data) {
        return error(400, message, data);
    }

    public static ResponseBean of(boolean success, String message, Object data) {
        return success ? success(data, message) : error(message, data);
    }

    public static ResponseBean of(boolean success, String message) {
        return success ? success(null, message) : error(message);
    }

    public static ResponseBean of(boolean success) {
        return of(success, "");
    }
}
