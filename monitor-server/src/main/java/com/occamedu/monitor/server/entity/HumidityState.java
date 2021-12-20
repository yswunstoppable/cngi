package com.occamedu.monitor.server.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @version v2.3
 * @ClassName:MemState.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: 查看机房湿度情况
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
public class HumidityState extends BaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = -1412473355088780549L;


    /**
     * host名称
     */
    private String hostname;

    /**
     * 湿度
     */
    private double humidity;



    /**
     * 添加时间
     * yyyy-MM-dd hh:mm:ss
     */
    private String dateStr;

    /**
     * 创建时间
     */
    private Date createTime;


    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
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