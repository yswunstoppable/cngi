package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseAppInfo<M extends BaseAppInfo<M>> extends Model<M> implements IBean {

	public void setID(java.lang.String ID) {
		set("ID", ID);
	}
	
	public java.lang.String getID() {
		return getStr("ID");
	}

	public void setHostName(java.lang.String hostName) {
		set("HOST_NAME", hostName);
	}
	
	public java.lang.String getHostName() {
		return getStr("HOST_NAME");
	}

	public void setAppPid(java.lang.String appPid) {
		set("APP_PID", appPid);
	}
	
	public java.lang.String getAppPid() {
		return getStr("APP_PID");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("CREATE_TIME", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("CREATE_TIME");
	}

	public void setAppName(java.lang.String appName) {
		set("APP_NAME", appName);
	}
	
	public java.lang.String getAppName() {
		return getStr("APP_NAME");
	}

	public void setCpuPer(java.lang.Double cpuPer) {
		set("CPU_PER", cpuPer);
	}
	
	public java.lang.Double getCpuPer() {
		return getDouble("CPU_PER");
	}

	public void setMemPer(java.lang.Double memPer) {
		set("MEM_PER", memPer);
	}
	
	public java.lang.Double getMemPer() {
		return getDouble("MEM_PER");
	}

	public void setAppType(java.lang.String appType) {
		set("APP_TYPE", appType);
	}
	
	public java.lang.String getAppType() {
		return getStr("APP_TYPE");
	}

	public void setSTATE(java.lang.String STATE) {
		set("STATE", STATE);
	}
	
	public java.lang.String getSTATE() {
		return getStr("STATE");
	}

}