package com.occamedu.security.web.kit;

import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import com.alibaba.fastjson.JSONObject;
import com.occamedu.security.web.constant.WebConstants;
import com.occamedu.security.web.vo.AttackInfo;
import com.occamedu.security.web.vo.ResData;
import com.occamedu.security.web.vo.SxfLoginRes;
import com.occamedu.security.web.vo.SxfSysInfo;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
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
 * @date 2020-06-08 14:22
 * @description
 */
public class SxfKit {

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

    private static final String CAPTCHA_PATH = "/apps/secvisual/auth_manage/auth_manage/req_captcha?";
    private static final String LOGIN_PATH = "/apps/secvisual/login/login.html";
    private static final String LOGIN_ACTION = "/apps/secvisual/auth_manage/Auth_manage/on_login";
    private static final String INDEX_PATH = "/apps/secvisual/index.html";
    private static final String GET_SYSTEM_INFO_PATH = "/apps/secvisual/system/system_manage/get_system_info";
    private static final String GET_TOP5_PATH = "/apps/secvisual/attack_apperceive/high_risk_attack/on_get_attack_src_ip_top";
    private static final String GET_REMNANT_TOP5_PATH = "/apps/secvisual/attack_apperceive/survival_attack/on_get_attack_src_ip_top";
    private static final String GET_VIOLENCE_TOP5_PATH = "/apps/secvisual/attack_apperceive/brute_force_attack/on_get_attack_src_ip_top";

    public static Map<String, String> downloadValidatingCode(String protocol, String host, String filename) {
        try {
            Connection.Response response = Jsoup
                    .connect(protocol + host + CAPTCHA_PATH + System.currentTimeMillis())
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer(protocol + host + LOGIN_PATH)
                    .header("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    .header("Connection", "keep-alive")
                    .header("Host", host)
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Sec-Fetch-Dest", "image")
                    .header("Sec-Fetch-Mode", "no-cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .ignoreContentType(true).method(Connection.Method.GET)
                    .timeout(WebConstants.REQUEST_MAX_TIME).execute();
            File to = new File(filename);
            FileOutputStream out = (new FileOutputStream(to));
            out.write(response.bodyAsBytes());
            out.close();
            //下载完验证码之后会对 cookies 进行更新,添加了一个 cookie 项
            return response.cookies();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static BaseResult login(Map<String, String> cookies, String protocol, String host,
                                   String account, String password, String captcha) {
        String loginAction = protocol + host + LOGIN_ACTION;
        try {
            Connection.Response loginResp = Jsoup.connect(loginAction)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer(protocol + host + LOGIN_PATH)
                    .header("Origin", protocol + host)
                    .header("Host", host)
                    .header("Accept", "text/html, */*; q=0.01")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .header("X-Request-With", "XMLHttpRequest")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .data("name", account)
                    .data("password", password)
                    .data("captcha", captcha)
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            SxfLoginRes loginRes = JSONObject.parseObject(loginResp.body(), SxfLoginRes.class);
            cookies.putAll(loginResp.cookies());
            if (loginRes.isSuccess()) {
                //获取系统信息
                Connection.Response getSysInfoResp = Jsoup.connect(protocol + host + GET_SYSTEM_INFO_PATH)
                        .cookies(cookies)
                        .userAgent(WebConstants.USER_AGENT)
                        .referrer(protocol + host + LOGIN_PATH)
                        .userAgent(WebConstants.USER_AGENT)
                        .referrer(protocol + host + INDEX_PATH)
                        .header("Origin", protocol + host)
                        .header("Host", host)
                        .header("Accept", "*/*")
                        .header("Connection", "keep-alive")
                        .header("Content-Type", "application/json")
                        .header("X-Request-With", "XMLHttpRequest")
                        .header("Sec-Fetch-Mode", "cors")
                        .header("Sec-Fetch-Site", "same-origin")
                        .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
                SxfSysInfo sxfSysInfo = JSONObject.parseObject(getSysInfoResp.body(), SxfSysInfo.class);
                if (sxfSysInfo.isSuccess()) {
                    cookies.put("equipmentID", sxfSysInfo.getData().getDeiceid());
                    return DataResult.data(new ResData(JSONObject.toJSONString(sxfSysInfo), JSONObject.toJSONString(cookies)));
                }
            }
            //登录失败，返回错误信息
            return BaseResult.fail(loginRes.getMessage());
        } catch (Exception e) {
            return BaseResult.fail(e.getMessage());
        }
    }

    private static List<AttackInfo> getTop5(Map<String, String> cookies, String protocol, String host,
                                            String path, String jsonBody) {
        String getAttackTop5Url = protocol + host + path;
        try {
            Connection.Response getAttackTop5Res = Jsoup.connect(getAttackTop5Url)
                    .cookies(cookies)
                    .userAgent(WebConstants.USER_AGENT)
                    .referrer(protocol + host + INDEX_PATH)
                    .header("Origin", protocol + host)
                    .header("Host", host)
                    .header("Accept", "*/*")
                    .header("Connection", "keep-alive")
                    .header("Content-Type", "application/json")
                    .header("X-Request-With", "XMLHttpRequest")
                    .header("Sec-Fetch-Mode", "cors")
                    .header("Sec-Fetch-Site", "same-origin")
                    .requestBody(jsonBody)
                    .method(Connection.Method.POST).ignoreContentType(true).timeout(WebConstants.REQUEST_MAX_TIME).execute();
            JSONObject jsonObject = JSONObject.parseObject(getAttackTop5Res.body());
            jsonObject = JSONObject.parseObject(jsonObject.getString("data"));
            return jsonObject.getJSONArray("data").toJavaList(AttackInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<AttackInfo> getHighRiskTop5(Map<String, String> cookies, String protocol, String host) {
        String jsonBody = "{\"page\":1,\"limit\":5,\"start\":0,\"ignore_ci_cache\":true,\"time_range\":\"last24h\",\"view_branch_id\":0}";
        return getTop5(cookies, protocol, host, GET_TOP5_PATH, jsonBody);
    }

    public static List<AttackInfo> getRemnantTop5(Map<String, String> cookies, String protocol, String host) {
        String jsonBody = "{\"page\":1,\"limit\":5,\"start\":0,\"time_range\":\"last24h\",\"high_weight\":1,\"attack_type\":\"\",\"view_branch_id\":0}";
        return getTop5(cookies, protocol, host, GET_REMNANT_TOP5_PATH, jsonBody);
    }

    public static List<AttackInfo> getViolenceTop5(Map<String, String> cookies, String protocol, String host) {
        String jsonBody = "{\"page\":1,\"limit\":5,\"start\":0,\"time_range\":\"last24h\",\"view_branch_id\":0}";
        return getTop5(cookies, protocol, host, GET_VIOLENCE_TOP5_PATH, jsonBody);
    }

    public static void main(String[] args) {
        String protocol = "https://";
        String host = "10.2.13.16";
        Map<String, String> cookies = downloadValidatingCode(protocol, host, "/Users/fenghao/Desktop/sxf.gif");
        cookies.forEach((k, v) -> System.out.println(k + "：" + v));
    }
}
