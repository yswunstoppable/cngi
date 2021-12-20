package com.occamedu.security.web.vo;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-09-11 21:48
 * @description
 */
class AttackTime {
    private String time_start;
    private String time_end;

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    @Override
    public String toString() {
        return "AttackTime{" +
                "time_start='" + time_start + '\'' +
                ", time_end='" + time_end + '\'' +
                '}';
    }
}

public class AttackInfo {
    private String src_ip;
    private String src_region;
    private String attack_count;
    private AttackTime attack_time;

    public String getSrc_ip() {
        return src_ip;
    }

    public void setSrc_ip(String src_ip) {
        this.src_ip = src_ip;
    }

    public String getSrc_region() {
        return src_region;
    }

    public void setSrc_region(String src_region) {
        this.src_region = src_region;
    }

    public String getAttack_count() {
        return attack_count;
    }

    public void setAttack_count(String attack_count) {
        this.attack_count = attack_count;
    }

    public AttackTime getAttack_time() {
        return attack_time;
    }

    public void setAttack_time(AttackTime attack_time) {
        this.attack_time = attack_time;
    }

    @Override
    public String toString() {
        return "AttackInfo{" +
                "src_ip='" + src_ip + '\'' +
                ", src_region='" + src_region + '\'' +
                ", attack_count='" + attack_count + '\'' +
                ", attack_time=" + attack_time +
                '}';
    }
}
