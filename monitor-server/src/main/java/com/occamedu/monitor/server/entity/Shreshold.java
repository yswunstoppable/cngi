package com.occamedu.monitor.server.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * shreshold
 * @author 
 */
public class Shreshold implements Serializable {
    /**
     * 主键 自增主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 键
     */
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 是否删除 0-未删除 1-已删除
     */
    private Byte isDeleted;

    /**
     * 创建时间 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间 更新时间
     */
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}