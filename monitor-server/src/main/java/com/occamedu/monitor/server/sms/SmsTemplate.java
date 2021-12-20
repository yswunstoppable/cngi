package com.occamedu.monitor.server.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title：agentApi2
 * @Description：短信验证码模板
 * @Author：zhangly
 * @Date：2019/5/13 11:36
 * @Version：1.0
 */
@AllArgsConstructor
@Getter
public enum SmsTemplate {

    MEM_USED_WARN("MEM_USED_WARN", "1125369", new String[]{"",""}, true, false, false),
    CPU_USED_WARN("CPU_USED_WARN", "1125744", new String[]{"",""}, true, false, false),
    HOST_DOWN_WARN("HOST_DOWN_WARN", "1125746", new String[]{"",""}, true, false, false);

    //模板名称
    private String code;
    //模板编码
    private String smsTemp;
    //模板内容
    private String[] context;
    //是否需要校验手机号合法性
    private boolean isCheckMobileNo;
    //是否需要生成6为随机数字验证码
    private boolean needVerifyCode;
    //模板内容是否有占位符，需要传递参数
    private boolean needParams;

    public static SmsTemplate getTemplateByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            SmsTemplate[] values = SmsTemplate.values();
            for (SmsTemplate value : values) {
                if (code.equals(value.getCode())) {
                    return value;
                }
            }
        }
        return null;
    }
}