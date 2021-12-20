package com.occamedu.monitor.server.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ysw
 * @email 1461182123@qq.com
 * @date 2021/9/11 5:54 下午
 * @description
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "sms")
public class SmsConfig {
    private String secretId;
    private String secretKey;
    private String appId;

    public SmsConfig() {
    }

    public String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
