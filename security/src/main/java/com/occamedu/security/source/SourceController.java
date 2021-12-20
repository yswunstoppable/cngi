package com.occamedu.security.source;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.kit.Kits;
import cn.fabrice.kit.jfinal.FileRenderKit;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Clear;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.interceptor.AuthInterceptor;
import com.occamedu.security.common.module.PerceptionSource;
import com.occamedu.security.source.constant.SourceConstants;
import com.occamedu.security.web.kit.LmKit;
import com.occamedu.security.web.kit.SxfKit;
import com.occamedu.security.web.vo.ResData;

import java.io.IOException;
import java.util.Map;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 15:08
 * @description
 */
@ValidateParam
public class SourceController extends Controller {
    @Inject
    private SourceService service;

    /**
     * 获取数据列表
     *
     * @param pageNumber 页码
     * @param pageSize   页长
     */
    public void list(int pageNumber, int pageSize) {
        Page<PerceptionSource> page = service.list(pageNumber, pageSize);
        page.getList().forEach(item -> {
            item.put("account", "******");
            item.put("password", "********");
        });
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 判断名称是否重复
     *
     * @param id   ID
     * @param name 名称
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ZERO_POSITIVE_INTEGER)
    @Param(name = "name", required = true)
    public void judgeName(long id, String name) {
        renderJson(service.isExistByInnerSql(id, "name", name) ?
                DataResult.data("fail") : DataResult.data("ok"));
    }

    /**
     * 新增感知源
     *
     * @param perceptionSource 对应实体类
     */
    @Param(name = "name", required = true)
    @Param(name = "perceptionPlatform", required = true, customRule = SourceConstants.Platform.SXF + "|" + SourceConstants.Platform.LM)
    @Param(name = "protocolType", required = true, customRule = SourceConstants.Protocol.HTTP + "|" + SourceConstants.Protocol.HTTPS)
    @Param(name = "host", required = true)
    @Param(name = "account", required = true)
    @Param(name = "password", required = true)
    public void add(@Para("") PerceptionSource perceptionSource) {
        perceptionSource.setId(null);
        if (perceptionSource.save()) {
            renderJson(DataResult.data(perceptionSource.getId()));
        } else {
            renderJson(BaseResult.fail());
        }
    }

    /**
     * 新增感知源
     *
     * @param perceptionSource 对应实体类
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "name", required = true)
    @Param(name = "perceptionPlatform", required = true, customRule = SourceConstants.Platform.SXF + "|" + SourceConstants.Platform.LM)
    @Param(name = "protocolType", required = true, customRule = SourceConstants.Protocol.HTTP + "|" + SourceConstants.Protocol.HTTPS)
    @Param(name = "host", required = true)
    @Param(name = "account", required = true)
    @Param(name = "password", required = true)
    public void update(@Para("") PerceptionSource perceptionSource) {
        perceptionSource.setCookie(null);
        perceptionSource.setIsValidated(0);
        if (perceptionSource.update()) {
            renderJson(DataResult.data(perceptionSource.getId()));
        } else {
            renderJson(BaseResult.fail());
        }
    }

    /**
     * 获取对应感知源的验证码
     *
     * @param id 感知源ID
     */
    @Clear(AuthInterceptor.class)
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    public void getCaptcha(long id) {
        PerceptionSource perceptionSource = service.get(id);
        String fileName = JFinal.me().getConstants().getBaseDownloadPath() + "/" + Kits.getUuid() + ".gif";
        Map<String, String> cookies = SxfKit.downloadValidatingCode(SourceConstants.PROTOCOL_MAP.get(perceptionSource.getProtocolType()),
                perceptionSource.getHost(), fileName);
        cookies.put("UEDC_LOGIN_POLICY_VALUE", "checked");
        perceptionSource.setCookie(JSONObject.toJSONString(cookies));
        if (perceptionSource.update()) {
            render(FileRenderKit.render(fileName, "image/gif"));
            return;
        }
        renderError(404);
    }

    /**
     * 连接获取cookie数据
     *
     * @param id      对应ID
     * @param captcha 验证码
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    public void connect(long id, String captcha) {
        PerceptionSource perceptionSource = service.get(id);
        if (perceptionSource == null) {
            renderJson(BaseResult.illegal());
            return;
        }
        BaseResult result;
        if (perceptionSource.getPerceptionPlatform() == SourceConstants.Platform.SXF) {
            Map<String, String> cookies = JSONObject.parseObject(perceptionSource.getCookie(), Map.class);
            result = SxfKit.login(cookies, SourceConstants.PROTOCOL_MAP.get(perceptionSource.getProtocolType()),
                    perceptionSource.getHost(), perceptionSource.getAccount(), perceptionSource.getPassword(), captcha);
        } else {
            String protocol = SourceConstants.PROTOCOL_MAP.get(perceptionSource.getProtocolType());
            Map<String, String> cookies = LmKit.getIndexCookie(protocol, perceptionSource.getHost());
            result = LmKit.login(cookies, protocol, perceptionSource.getHost(), perceptionSource.getAccount(), perceptionSource.getPassword());
        }
        if (result.isOk()) {
            DataResult dataResult = (DataResult) result;
            ResData resData = (ResData) dataResult.getData();
            perceptionSource.setCookie(resData.getCookie());
            perceptionSource.setExtraData(resData.getSystemInfo());
            perceptionSource.setIsValidated(1);
            if (perceptionSource.update()) {
                renderJson(BaseResult.ok());
                return;
            }
            renderJson(BaseResult.fail("保存cookie信息失败"));
            return;
        }
        renderJson(result);
    }

    /**
     * 获取数据
     *
     * @param id 对应ID
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    public void get(long id) {
        renderJson(DataResult.data(service.get(id)));
    }

    /**
     * 获取校验通过感知源数据
     */
    public void listValidated() {
        renderJson(DataResult.data(service.list("listValidated")));
    }
}
