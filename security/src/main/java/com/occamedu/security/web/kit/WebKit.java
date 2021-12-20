package com.occamedu.security.web.kit;

import com.occamedu.security.web.constant.WebConstants;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 14:08
 * @description
 */
public class WebKit {
    /**
     * 访问首页，获取当前操作Cookies
     *
     * @return 返回当前Cookies数据
     */
    public static Map<String, String> getCookies(String url) {
        try {
            Connection.Response response = Jsoup.connect(url)
                    .userAgent(WebConstants.USER_AGENT)
                    .timeout(WebConstants.REQUEST_MAX_TIME)
                    .ignoreContentType(true).method(Connection.Method.GET).execute();
            return response.cookies();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
