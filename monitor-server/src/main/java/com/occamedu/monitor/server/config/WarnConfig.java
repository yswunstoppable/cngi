package com.occamedu.monitor.server.config;

import com.occamedu.monitor.server.constants.ShresholdConstant;
import com.occamedu.monitor.server.mapper.ShresholdMapper;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


@Data
@Configuration
@ConfigurationProperties(prefix = "mail")
public class WarnConfig {


    @Resource
    private ShresholdMapper shresholdMapper;

    private Double memWarnVal;
    private Double cpuWarnVal;
    private String memWarnMail;
    private String cpuWarnMail;
    private String hostDownWarnMail;
    private String appDownWarnMail;
    private String heathWarnMail;
    private String allWarnMail;


    public Double getMemWarnVal() {
        if (memWarnVal == null) {
            return 98d;
        }
        return memWarnVal;
    }

    public void setMemWarnVal(Double memWarnVal) {
        String value = shresholdMapper.selectValueByKey(ShresholdConstant.MEMORY_USED_LIMIT);
        this.memWarnVal = value != null ? Double.parseDouble(value) : memWarnVal;
    }

    public Double getCpuWarnVal() {
        if (cpuWarnVal == null) {
            return 98d;
        }
        return cpuWarnVal;
    }

    public void setCpuWarnVal(Double cpuWarnVal) {
        String value = shresholdMapper.selectValueByKey(ShresholdConstant.CPU_USED_LIMIT);
        this.cpuWarnVal = value != null ? Double.parseDouble(value) : cpuWarnVal;
    }

    public String getMemWarnMail() {
        if (StringUtils.isEmpty(memWarnMail)) {
            return "true";
        }
        return memWarnMail;
    }

    public void setMemWarnMail(String memWarnMail) {
        this.memWarnMail = memWarnMail;
    }

    public String getCpuWarnMail() {
        if (StringUtils.isEmpty(cpuWarnMail)) {
            return "true";
        }
        return cpuWarnMail;
    }

    public void setCpuWarnMail(String cpuWarnMail) {
        this.cpuWarnMail = cpuWarnMail;
    }

    public String getHostDownWarnMail() {
        if (StringUtils.isEmpty(hostDownWarnMail)) {
            return "true";
        }
        return hostDownWarnMail;
    }

    public void setHostDownWarnMail(String hostDownWarnMail) {
        this.hostDownWarnMail = hostDownWarnMail;
    }

    public String getAppDownWarnMail() {
        if (StringUtils.isEmpty(appDownWarnMail)) {
            return "true";
        }
        return appDownWarnMail;
    }

    public void setAppDownWarnMail(String appDownWarnMail) {
        this.appDownWarnMail = appDownWarnMail;
    }

    public String getHeathWarnMail() {
        if (StringUtils.isEmpty(heathWarnMail)) {
            return "true";
        }
        return heathWarnMail;
    }

    public void setHeathWarnMail(String heathWarnMail) {
        this.heathWarnMail = heathWarnMail;
    }

    public String getAllWarnMail() {
        if (StringUtils.isEmpty(allWarnMail)) {
            return "true";
        }
        return allWarnMail;
    }

    public void setAllWarnMail(String allWarnMail) {
        this.allWarnMail = allWarnMail;
    }
}
