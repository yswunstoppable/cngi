package com.occamedu.monitor.server.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * SMS_SET
 * @author 
 */
@Data
public class SmsSet implements Serializable {
    private String id;

    private String sendSms;

    private String tencentSmsAppId;

    private String tencentSmsSecretId;

    private String tencentSmsSecretKey;

    private String toMobile;

    private String cpuPer;

    private Date createTime;

    private String memPer;

    private String heathPer;

    private static final long serialVersionUID = 1L;
}