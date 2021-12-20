package com.occamedu.security.web.vo;

import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 13:19
 * @description
 */
public class LmLoginRes {

    /**
     * status : 1
     * modaldatas : []
     * datas : [{"issystem":1,"menuname":"系统管理","isenable":1,"menuurl":"/sysmanage/index","text":"系统管理","menuregx":"^/sysmanage|^/accountmanage|^/dataconfig|^/filemanage|^/userSetting|^/systemInterface|^/systemOperate|^/systemAlert|^/appManager|^/bcm|^/license|^/dbBackup|^/asset|^/dataconfigGuide|^/debugtool|^/exportReport|^/logAlarm","id":1,"isdefault":1,"foldername":"sysmanage,accountmanage,dataconfig,filemanage,systemInterface,systemOperate,systemAlert,appManager,bcm,license,dbBackup,asset,dataconfigGuide,debugtool,exportReport,logAlarm"},{"issystem":0,"menuname":"系统导航","isenable":1,"menuurl":"/sysmanage/index","text":"系统导航","menuregx":"^/exportReport|^/logAlarm","id":6,"isdefault":1,"foldername":"exportReport,logAlarm"},{"issystem":0,"menuname":"威胁情报","isenable":1,"menuurl":"/bsa_ti","text":"威胁情报","menuregx":"^/bsa_ti","id":101,"isdefault":0,"foldername":"bsa_ti"},{"issystem":0,"menuname":"规则引擎","isenable":1,"menuurl":"/bsa_rule_engine","text":"规则引擎","menuregx":"^/bsa_rule_engine","id":106,"isdefault":0,"foldername":"bsa_rule_engine"},{"issystem":0,"menuname":"态势感知","isenable":1,"menuurl":"/bsa_tsa","text":"态势感知","menuregx":"^/bsa_tsa","id":109,"isdefault":0,"foldername":"bsa_tsa"}]
     * pass : f44093fca984bfb572c65907d8def137d04be460
     */

    private int status;
    private String pass;
    private List<?> modaldatas;
    private Object datas;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public List<?> getModaldatas() {
        return modaldatas;
    }

    public void setModaldatas(List<?> modaldatas) {
        this.modaldatas = modaldatas;
    }

    public Object getDatas() {
        return datas;
    }

    public void setDatas(Object datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * issystem : 1
         * menuname : 系统管理
         * isenable : 1
         * menuurl : /sysmanage/index
         * text : 系统管理
         * menuregx : ^/sysmanage|^/accountmanage|^/dataconfig|^/filemanage|^/userSetting|^/systemInterface|^/systemOperate|^/systemAlert|^/appManager|^/bcm|^/license|^/dbBackup|^/asset|^/dataconfigGuide|^/debugtool|^/exportReport|^/logAlarm
         * id : 1
         * isdefault : 1
         * foldername : sysmanage,accountmanage,dataconfig,filemanage,systemInterface,systemOperate,systemAlert,appManager,bcm,license,dbBackup,asset,dataconfigGuide,debugtool,exportReport,logAlarm
         */

        private int issystem;
        private String menuname;
        private int isenable;
        private String menuurl;
        private String text;
        private String menuregx;
        private int id;
        private int isdefault;
        private String foldername;

        public int getIssystem() {
            return issystem;
        }

        public void setIssystem(int issystem) {
            this.issystem = issystem;
        }

        public String getMenuname() {
            return menuname;
        }

        public void setMenuname(String menuname) {
            this.menuname = menuname;
        }

        public int getIsenable() {
            return isenable;
        }

        public void setIsenable(int isenable) {
            this.isenable = isenable;
        }

        public String getMenuurl() {
            return menuurl;
        }

        public void setMenuurl(String menuurl) {
            this.menuurl = menuurl;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMenuregx() {
            return menuregx;
        }

        public void setMenuregx(String menuregx) {
            this.menuregx = menuregx;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsdefault() {
            return isdefault;
        }

        public void setIsdefault(int isdefault) {
            this.isdefault = isdefault;
        }

        public String getFoldername() {
            return foldername;
        }

        public void setFoldername(String foldername) {
            this.foldername = foldername;
        }
    }
}
