package com.occamedu.security.web.vo;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 09:35
 * @description
 */
public class SxfSysInfo {

    /**
     * success : true
     * data : {"app_version":"SIS3.0.45.0  Build20200316\nSIS3.0.45.upme.SP Build20200407\nSIS3.0.45.log_del_record.SP Build20200512","system_version":"3.0.45","system_date":"2020-06-09","system_time":"09:08:10","lib_info":{"is_virus_lib_exist":true},"is_show_sas":false,"is_alarm_cfg":true,"is_version_expired":false,"index_title":"安全感知平台","deiceid":"6CC11285","config_wizard":true,"is_device_exception":false}
     */

    private boolean success;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * app_version : SIS3.0.45.0  Build20200316
         SIS3.0.45.upme.SP Build20200407
         SIS3.0.45.log_del_record.SP Build20200512
         * system_version : 3.0.45
         * system_date : 2020-06-09
         * system_time : 09:08:10
         * lib_info : {"is_virus_lib_exist":true}
         * is_show_sas : false
         * is_alarm_cfg : true
         * is_version_expired : false
         * index_title : 安全感知平台
         * deiceid : 6CC11285
         * config_wizard : true
         * is_device_exception : false
         */

        private String app_version;
        private String system_version;
        private String system_date;
        private String system_time;
        private LibInfoBean lib_info;
        private boolean is_show_sas;
        private boolean is_alarm_cfg;
        private boolean is_version_expired;
        private String index_title;
        private String deiceid;
        private boolean config_wizard;
        private boolean is_device_exception;

        public String getApp_version() {
            return app_version;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getSystem_version() {
            return system_version;
        }

        public void setSystem_version(String system_version) {
            this.system_version = system_version;
        }

        public String getSystem_date() {
            return system_date;
        }

        public void setSystem_date(String system_date) {
            this.system_date = system_date;
        }

        public String getSystem_time() {
            return system_time;
        }

        public void setSystem_time(String system_time) {
            this.system_time = system_time;
        }

        public LibInfoBean getLib_info() {
            return lib_info;
        }

        public void setLib_info(LibInfoBean lib_info) {
            this.lib_info = lib_info;
        }

        public boolean isIs_show_sas() {
            return is_show_sas;
        }

        public void setIs_show_sas(boolean is_show_sas) {
            this.is_show_sas = is_show_sas;
        }

        public boolean isIs_alarm_cfg() {
            return is_alarm_cfg;
        }

        public void setIs_alarm_cfg(boolean is_alarm_cfg) {
            this.is_alarm_cfg = is_alarm_cfg;
        }

        public boolean isIs_version_expired() {
            return is_version_expired;
        }

        public void setIs_version_expired(boolean is_version_expired) {
            this.is_version_expired = is_version_expired;
        }

        public String getIndex_title() {
            return index_title;
        }

        public void setIndex_title(String index_title) {
            this.index_title = index_title;
        }

        public String getDeiceid() {
            return deiceid;
        }

        public void setDeiceid(String deiceid) {
            this.deiceid = deiceid;
        }

        public boolean isConfig_wizard() {
            return config_wizard;
        }

        public void setConfig_wizard(boolean config_wizard) {
            this.config_wizard = config_wizard;
        }

        public boolean isIs_device_exception() {
            return is_device_exception;
        }

        public void setIs_device_exception(boolean is_device_exception) {
            this.is_device_exception = is_device_exception;
        }

        public static class LibInfoBean {
            /**
             * is_virus_lib_exist : true
             */

            private boolean is_virus_lib_exist;

            public boolean isIs_virus_lib_exist() {
                return is_virus_lib_exist;
            }

            public void setIs_virus_lib_exist(boolean is_virus_lib_exist) {
                this.is_virus_lib_exist = is_virus_lib_exist;
            }
        }
    }
}
