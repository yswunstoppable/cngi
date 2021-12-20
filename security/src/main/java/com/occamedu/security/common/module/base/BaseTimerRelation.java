package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseTimerRelation<M extends BaseTimerRelation<M>> extends Model<M> implements IBean {

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
	 * 定时任务ID
	 */
	public void setTimerId(java.math.BigInteger timerId) {
		set("timer_id", timerId);
	}
	
	/**
	 * 定时任务ID
	 */
	public java.math.BigInteger getTimerId() {
		return get("timer_id");
	}

	/**
	 * 关联类型 1-边界 2-命令
	 */
	public void setType(java.lang.Integer type) {
		set("type", type);
	}
	
	/**
	 * 关联类型 1-边界 2-命令
	 */
	public java.lang.Integer getType() {
		return getInt("type");
	}

	/**
	 * 对应ID
	 */
	public void setTargetId(java.math.BigInteger targetId) {
		set("target_id", targetId);
	}
	
	/**
	 * 对应ID
	 */
	public java.math.BigInteger getTargetId() {
		return get("target_id");
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