package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMemState<M extends BaseMemState<M>> extends Model<M> implements IBean {

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

	public void setTOTAL(java.lang.String TOTAL) {
		set("TOTAL", TOTAL);
	}
	
	public java.lang.String getTOTAL() {
		return getStr("TOTAL");
	}

	public void setUSED(java.lang.String USED) {
		set("USED", USED);
	}
	
	public java.lang.String getUSED() {
		return getStr("USED");
	}

	public void setFREE(java.lang.String FREE) {
		set("FREE", FREE);
	}
	
	public java.lang.String getFREE() {
		return getStr("FREE");
	}

	public void setUsePer(java.lang.Double usePer) {
		set("USE_PER", usePer);
	}
	
	public java.lang.Double getUsePer() {
		return getDouble("USE_PER");
	}

	public void setDateStr(java.lang.String dateStr) {
		set("DATE_STR", dateStr);
	}
	
	public java.lang.String getDateStr() {
		return getStr("DATE_STR");
	}

	public void setCreateTime(java.util.Date createTime) {
		set("CREATE_TIME", createTime);
	}
	
	public java.util.Date getCreateTime() {
		return get("CREATE_TIME");
	}

}
