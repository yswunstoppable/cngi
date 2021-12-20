package com.occamedu.security.web;

import com.alibaba.fastjson.JSONObject;
import com.occamedu.security.web.constant.WebConstants;
import com.occamedu.security.web.kit.WebKit;
import com.occamedu.security.web.vo.AttackInfo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 14:00
 * @description
 */
public class WebService {
    /**
     * 现在很多站点都是SSL对数据传输进行加密，这也让普通的HttpConnection无法正常的获取该页面的内容，
     * 而Jsoup起初也对此没有做出相应的处理，
     * 想了一下是否可以让Jsoup可以识别所有的SSL加密过的页面，查询了一些资料，发现可以为本地HttpsURLConnection配置一个“万能证书”，其原理是就是：
     * 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
     * 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
     * 后来Jsoup Connection提供了validateTLSCertificates(boolean validate)//是否进行TLS证书验证,不推荐
     */
    static {
        try {
            // 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            // 创建随机证书生成工厂
            //SSLContext context = SSLContext.getInstance("TLS");
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());

            // 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
                String url = "https://10.2.13.16/apps/secvisual/login/login.html";
        Map<String, String> cookies = WebKit.getCookies(url);
        String loginUrl = "https://10.2.13.16/apps/secvisual/auth_manage/Auth_manage/on_login";
        try {
            Connection.Response loginResp = Jsoup.connect(loginUrl)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer(url)
                    .header("Origin", "https://10.2.13.16")
                    .header("Host", "10.2.13.16")
                    .header("Accept", "text/html, */*; q=0.01")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Request-With", "XMLHttpRequest")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .data("name", "wlaq1")
                    .data("password", "Zuel@2019")
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            System.out.println(loginResp.body());
            cookies.putAll(loginResp.cookies());
            String getDeviceIdUrl = "https://10.2.13.16/apps/secvisual/req_analysis/req_analysis/get_deviceid";
            Connection.Response getDeviceIdRes = Jsoup.connect(getDeviceIdUrl)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer("https://10.2.13.16/apps/secvisual/index.html")
                    .header("Origin", "https://10.2.13.16")
                    .header("Host", "10.2.13.16")
                    .header("Accept", "*/*")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/json")
                    .header("X-Request-With", "XMLHttpRequest")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            System.out.println(getDeviceIdRes.body());
            String content = getDeviceIdRes.body();
            JSONObject jsonObject = JSONObject.parseObject(content);
            JSONObject temp = JSONObject.parseObject(jsonObject.getString("data"));
            String deviceId = temp.getString("deiceid");
            System.out.println(deviceId);
            cookies.put("equipmentID", deviceId);
            cookies.forEach((k, v) -> {
                System.out.println(k + "\t" + v);
            });
            String getAttackTop5Url = "https://10.2.13.16/apps/secvisual/attack_apperceive/high_risk_attack/on_get_attack_src_ip_top";
            String jsonBody = "{\"page\":1,\"limit\":5,\"start\":0,\"ignore_ci_cache\":true,\"time_range\":\"last7d\",\"view_branch_id\":0}";
            Connection.Response getAttackTop5Res = Jsoup.connect(getAttackTop5Url)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer("https://10.2.13.16/apps/secvisual/index.html")
                    .header("Origin", "https://10.2.13.16")
                    .header("Host", "10.2.13.16")
                    .header("Accept", "*/*")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/json")
                    .header("X-Request-With", "XMLHttpRequest")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .requestBody(jsonBody)
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            content = getAttackTop5Res.body();
            jsonObject = JSONObject.parseObject(content);
            jsonObject = JSONObject.parseObject(jsonObject.getString("data"));
            List<AttackInfo> attackInfoList = jsonObject.getJSONArray("data").toJavaList(AttackInfo.class);
            attackInfoList.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
