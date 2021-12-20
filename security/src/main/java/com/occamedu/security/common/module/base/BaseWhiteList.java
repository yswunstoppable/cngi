package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWhiteList<M extends BaseWhiteList<M>> extends Model<M> implements IBean {

	/**
	 * 主键 自增主键
	 */
	public void setId(java.math.BigInteger id) {
		set("id", id);
	}
	
	/**
	 * 主键 自增主键
	 */
	public java.math.BigInteger getId() {
		return get("id");
	}

	/**
	 * 输入的ip地址信息
	 */
	public void setIpAddress(java.lang.String ipAddress) {
		set("ip_address", ipAddress);
	}
	
	/**
	 * 输入的ip地址信息
	 */
	public java.lang.String getIpAddress() {
		return getStr("ip_address");
	}

	/**
	 * IP段起始地址
	 */
	public void setIpStart(java.lang.String ipStart) {
		set("ip_start", ipStart);
	}
	
	/**
	 * IP段起始地址
	 */
	public java.lang.String getIpStart() {
		return getStr("ip_start");
	}

	/**
	 * IP段结束地址
	 */
	public void setIpEnd(java.lang.String ipEnd) {
		set("ip_end", ipEnd);
	}
	
	/**
	 * IP段结束地址
	 */
	public java.lang.String getIpEnd() {
		return getStr("ip_end");
	}

	/**
	 * 备注
	 */
	public void setNote(java.lang.String note) {
		set("note", note);
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getNote() {
		return getStr("note");
	}

	/**
	 * 状态 1-正常 2-禁用
	 */
	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}
	
	/**
	 * 状态 1-正常 2-禁用
	 */
	public java.lang.Integer getStatus() {
		return getInt("status");
	}

	/**
	 * 是否删除 0-未删除 1-已删除
	 */
	public void setIsDeleted(java.lang.Integer isDeleted) {
		set("is_deleted", isDeleted);
	}
	
	/**
	 * 是否删除 0-未删除 1-已删除
	 */
	public java.lang.Integer getIsDeleted() {
		return getInt("is_deleted");
	}

	/**
	 * 创建时间 创建时间
	 */
	public void setCreatedTime(java.util.Date createdTime) {
		set("created_time", createdTime);
	}
	
	/**
	 * 创建时间 创建时间
	 */
	public java.util.Date getCreatedTime() {
		return get("created_time");
	}

	/**
	 * 更新时间 更新时间
	 */
	public void setUpdatedTime(java.util.Date updatedTime) {
		set("updated_time", updatedTime);
	}
	
	/**
	 * 更新时间 更新时间
	 */
	public java.util.Date getUpdatedTime() {
		return get("updated_time");
	}

}