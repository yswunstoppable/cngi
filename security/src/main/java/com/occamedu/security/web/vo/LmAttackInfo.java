package com.occamedu.security.web.vo;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 13:41
 * @description
 */
public class LmAttackInfo {

    /**
     * ip : 154.85.201.62
     * atk_count : 2279
     * location : 南非
     * event_count : 2248
     */

    private String ip;
    private int atk_count;
    private String location;
    private int event_count;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getAtk_count() {
        return atk_count;
    }

    public void setAtk_count(int atk_count) {
        this.atk_count = atk_count;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getEvent_count() {
        return event_count;
    }

    public void setEvent_count(int event_count) {
        this.event_count = event_count;
    }
}
