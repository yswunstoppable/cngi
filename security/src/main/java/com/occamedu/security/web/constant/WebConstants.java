package com.occamedu.security.web.constant;

import org.jsoup.safety.Whitelist;

/**
 * @author fye
 * @team occam
 * @email fh941005@163.com
 * @date 2019-05-19 19:17
 * @description Web常量类
 */
public class WebConstants {
    /**
     * 请求最大时间
     */
    public static final int REQUEST_MAX_TIME = 60000;
    /**
     * 模拟浏览器信息
     */
    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    /**
     * 连接方式
     */
    public static final String CONNECTION = "keep-alive";

    /**
     * 数据接收形式
     */
    public interface Accept {
        String JSON = "application/json, text/javascript, */*; q=0.01";
    }

    /**
     * 传输的内容类型
     */
    public interface ContentType {
        String FORM = "application/x-www-form-urlencoded";
    }

    /**
     * 接收的编码类型
     */
    public interface AcceptEncoding {
        String GZIP = "gzip, deflate, br";
    }

    /**
     * 接收的语言类型
     */
    public interface AcceptLanguage {
        String ZHCN = "zh-CN,zh;q=0.9";
    }

    /**
     * jsoup html过滤白名单
     */
    public static Whitelist whitelist = new Whitelist().addTags("br");
}
