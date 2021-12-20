package com.occamedu.security.common.module.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseNetioState<M extends BaseNetioState<M>> extends Model<M> implements IBean {

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

	public void setRXPCK(java.lang.String RXPCK) {
		set("RXPCK", RXPCK);
	}
	
	public java.lang.String getRXPCK() {
		return getStr("RXPCK");
	}

	public void setTXPCK(java.lang.String TXPCK) {
		set("TXPCK", TXPCK);
	}
	
	public java.lang.String getTXPCK() {
		return getStr("TXPCK");
	}

	public void setRXBYT(java.lang.String RXBYT) {
		set("RXBYT", RXBYT);
	}
	
	public java.lang.String getRXBYT() {
		return getStr("RXBYT");
	}

	public void setTXBYT(java.lang.String TXBYT) {
		set("TXBYT", TXBYT);
	}
	
	public java.lang.String getTXBYT() {
		return getStr("TXBYT");
	}

	public void setRXCMP(java.lang.String RXCMP) {
		set("RXCMP", RXCMP);
	}
	
	public java.lang.String getRXCMP() {
		return getStr("RXCMP");
	}

	public void setTXCMP(java.lang.String TXCMP) {
		set("TXCMP", TXCMP);
	}
	
	public java.lang.String getTXCMP() {
		return getStr("TXCMP");
	}

	public void setRXMCST(java.lang.String RXMCST) {
		set("RXMCST", RXMCST);
	}
	
	public java.lang.String getRXMCST() {
		return getStr("RXMCST");
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