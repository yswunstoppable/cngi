package com.occamedu.monitor.agent.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @version V2.3
 * @ClassName:AppState.java
 * @author: occamedu
 * @date: 2019年11月16日
 * @Description: app状态监控
 * @Copyright: 2017-2021 www.wgstart.com. All rights reserved.
 */
public class AppState extends BaseEntity {

    /**
     *
     */
    private static final long serialVersionUID = -2913111613773445949L;


    /**
     * 应用信息ID
     */
    private String appInfoId;


    /**
     * %CPU
     */
    private Double cpuPer;

    /**
     * %MEM
     */
    private Double memPer;

    /**
     * 添加时间
     * MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;


    public Double getCpuPer() {
        return cpuPer;
    }

    public void setCpuPer(Double cpuPer) {
        this.cpuPer = cpuPer;
    }


    public String getAppInfoId() {
        return appInfoId;
    }

    public void setAppInfoId(String appInfoId) {
        this.appInfoId = appInfoId;
    }

    public Double getMemPer() {
        return memPer;
    }

    public void setMemPer(Double memPer) {
        this.memPer = memPer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDateStr() {
        if (!StringUtils.isEmpty(dateStr) && dateStr.length() > 16) {
            return dateStr.substring(5);
        }
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }


}