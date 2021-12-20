package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAttackSourceLog<M extends BaseAttackSourceLog<M>> extends Model<M> implements IBean {

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
	 * 对应定时任务
	 */
	public void setTimerLogId(java.math.BigInteger timerLogId) {
		set("timer_log_id", timerLogId);
	}
	
	/**
	 * 对应定时任务
	 */
	public java.math.BigInteger getTimerLogId() {
		return get("timer_log_id");
	}

	/**
	 * 攻击ip
	 */
	public void setAttackIp(java.lang.String attackIp) {
		set("attack_ip", attackIp);
	}
	
	/**
	 * 攻击ip
	 */
	public java.lang.String getAttackIp() {
		return getStr("attack_ip");
	}

	/**
	 * 攻击地点
	 */
	public void setAttackAddress(java.lang.String attackAddress) {
		set("attack_address", attackAddress);
	}
	
	/**
	 * 攻击地点
	 */
	public java.lang.String getAttackAddress() {
		return getStr("attack_address");
	}

	/**
	 * 攻击次数
	 */
	public void setAttackNumber(java.lang.Integer attackNumber) {
		set("attack_number", attackNumber);
	}
	
	/**
	 * 攻击次数
	 */
	public java.lang.Integer getAttackNumber() {
		return getInt("attack_number");
	}

	/**
	 * 攻击源类型
	 */
	public void setAttackType(java.lang.Integer attackType) {
		set("attack_type", attackType);
	}
	
	/**
	 * 攻击源类型
	 */
	public java.lang.Integer getAttackType() {
		return getInt("attack_type");
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
