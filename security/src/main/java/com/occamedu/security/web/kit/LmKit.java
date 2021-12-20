package com.occamedu.security.web.kit;

import cn.fabrice.kit.Kits;
import cn.fabrice.kit.text.ConvertKit;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import com.alibaba.fastjson.JSONObject;
import com.occamedu.security.web.constant.WebConstants;
import com.occamedu.security.web.vo.AttackInfo;
import com.occamedu.security.web.vo.LmAttackInfo;
import com.occamedu.security.web.vo.LmLoginRes;
import com.occamedu.security.web.vo.ResData;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 11:13
 * @description
 */
public class LmKit {
    //现在很多站点都是SSL对数据传输进行加密，这也让普通的HttpConnection无法正常的获取该页面的内容，
    // 而Jsoup起初也对此没有做出相应的处理，
    // 想了一下是否可以让Jsoup可以识别所有的SSL加密过的页面，查询了一些资料，发现可以为本地HttpsURLConnection配置一个“万能证书”，其原理是就是：
    // 重置HttpsURLConnection的DefaultHostnameVerifier，使其对任意站点进行验证时都返回true
    // 重置httpsURLConnection的DefaultSSLSocketFactory， 使其生成随机证书
    //后来Jsoup Connection提供了validateTLSCertificates(boolean validate),是否进行TLS证书验证,不推荐
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

    private static final String LOGIN_ACTION = "/user/login";
    private static final String GET_TOP5_PATH = "/bsa_tsa/api/v1/risk/getTop5Attackers/";

    public static Map<String, String> getIndexCookie(String protocol, String host) {
        String INDEX_URL = protocol + host;
        try {
            Connection.Response response = Jsoup.connect(INDEX_URL)
                    .userAgent(WebConstants.USER_AGENT)
                    .timeout(WebConstants.REQUEST_MAX_TIME)
                    .header("Host", host)
                    .header("Cache-Control", "max-age=0")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                    .header("Sec-Fetch-Dest", "document")
                    .header("Sec-Fetch-Mode", "navigate")
                    .header("Sec-Fetch-Site", "none")
                    .header("Sec-Fetch-User", "?1")
                    .header("Upgrade-Insecure-Requests", "1")
                    .ignoreContentType(true)
                    .ignoreHttpErrors(true)
                    .method(Connection.Method.GET).execute();
            return response.cookies();
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            return null;
        }
    }

    public static BaseResult login(Map<String, String> cookies, String protocol, String host, String username, String password) {
        String loginUrl = protocol + host + LOGIN_ACTION;
        try {
            Connection.Response loginResp = Jsoup.connect(loginUrl)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer(protocol + host)
                    .header("X-CSRFToken", cookies.get("csrftoken"))
                    .header("Origin", protocol + host)
                    .header("Host", host)
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                    .header("Connection", "keep-alive")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("X-Request-With", "XMLHttpRequest")
                    .data("username", username)
                    .data("password", Kits.sha1(password))
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            System.err.println(loginResp.body());
            LmLoginRes lmLoginRes = JSONObject.parseObject(loginResp.body(), LmLoginRes.class);
            if (lmLoginRes.getStatus() == 1) {
                //登录成功
                cookies.putAll(loginResp.cookies());
                return DataResult.data(new ResData(JSONObject.toJSONString(lmLoginRes), JSONObject.toJSONString(cookies)));
            }
            //登录失败，返回错误信息
            return BaseResult.fail(ConvertKit.decodeUnicode(String.valueOf(lmLoginRes.getDatas())));
        } catch (Exception e) {
            return BaseResult.fail(e.getLocalizedMessage());
        }
    }

    public static List<LmAttackInfo> getTop5(Map<String, String> cookies, String protocol, String host) {
        String getAttackTop5Url = protocol + host + GET_TOP5_PATH;
        try {
            Connection.Response getAttackTop5Res = Jsoup.connect(getAttackTop5Url)
                    .cookies(cookies)
                    .referrer(protocol + host + "/bsa_tsa")
                    .userAgent(WebConstants.USER_AGENT)
                    .header("X-CSRFToken", cookies.get("csrftoken"))
                    .header("Origin", protocol + host)
                    .header("Host", host)
                    .header("Accept", "application/json, text/plain, */*")
                    .header("Connection", "keep-alive")
                    .header("Sec-Fetch-Site", "same-origin")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("X-Request-With", "XMLHttpRequest")
                    .method(Connection.Method.GET).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            return JSONObject.parseArray(getAttackTop5Res.body(), LmAttackInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void main(String[] args) throws Exception {
        String protocol = "https://";
        String host = "202.114.235.204";
        Map<String, String> cookies = getIndexCookie(protocol, host);
        DataResult result = (DataResult) login(cookies, protocol, host, "lmtaishi", "Lm@2020@");
        System.out.println(JSONObject.toJSON(result));
        ResData resData = (ResData) result.getData();
        cookies = JSONObject.parseObject(resData.getCookie(), Map.class);
        List<LmAttackInfo> attackInfos=getTop5(cookies, protocol, host);
        attackInfos.forEach(lmAttackInfo -> System.out.println(JSONObject.toJSON(lmAttackInfo)));

//        String loginUrl = "https://10.2.13.16/apps/secvisual/auth_manage/Auth_manage/on_login";
//        try {
//            Connection.Response loginResp = Jsoup.connect(loginUrl)
//                    .cookies(cookies)
//                    .userAgent(WebConstants.USER_AGENT)
//                    .referrer(url)
//                    .header("Origin", "https://10.2.13.16")
//                    .header("Host", "10.2.13.16")
//                    .header("Accept", "text/html, */*; q=0.01")
//                    .header("Connection", "keep-alive")
//                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                    .header("X-Request-With", "XMLHttpRequest")
//                    .header("Sec-Fetch-Mode", "cors")
//                    .header("Sec-Fetch-Site", "same-origin")
//                    .data("name", "wlaq1")
//                    .data("password", "Zuel@2019")
//                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
//            System.out.println(loginResp.body());
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }
}
