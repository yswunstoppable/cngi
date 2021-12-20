package com.occamedu.security.source.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 15:09
 * @description
 */
public class SourceConstants {
    /**
     * 网络请求协议
     */
    public interface Protocol {
        int HTTP = 1;
        int HTTPS = 2;
    }

    public static final Map<Integer, String> PROTOCOL_MAP = new HashMap<Integer, String>(2) {
        {
            put(Protocol.HTTP, "http://");
            put(Protocol.HTTPS, "https://");
        }
    };


    /**
     * 感知平台
     */
    public interface Platform {
        int SXF = 1;
        int LM = 2;
    }

    public interface Top5Type {
        int SXF_HIGH_RISK = 11;
        int SXF_REMNANT = 12;
        int SXF_VIOLENCE = 13;
        int LM = 20;
    }
}
