package com.occamedu.security.whitelist;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.kit.http.IpKit;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.WhiteList;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 16:33
 * @description
 */
@ValidateParam
public class WhiteListController extends Controller {
    @Inject
    private WhiteListService service;

    /**
     * 获取列表
     *
     * @param params 请求参数实体类
     */
    public void list(@Para("") RequestParams params) {
        Kv cond = Kv.by("rq", params);
        Page<WhiteList> page = service.list(params.getPageNumber(), params.getPageSize(), cond);
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 判断参数值是否重复
     *
     * @param id    ID
     * @param field 校验字段
     * @param value 校验字段值
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ZERO_POSITIVE_INTEGER)
    @Param(name = "field", required = true, customRule = "(ip_address)")
    public void judge(long id, String field, String value) {
        boolean flag = service.isExistByInnerSql(id, field, value);
        renderJson(flag ? DataResult.data("fail") : DataResult.data("ok"));
    }

    private void processIp(WhiteList whiteList) {
        //分解ip段
        if (whiteList.getIpAddress().contains("/")) {
            String[] ipInfo = whiteList.getIpAddress().split("/");
            String ipStart = IpKit.getBeginIpStr(ipInfo[0], ipInfo[1]);
            String ipEnd = IpKit.getEndIpStr(ipInfo[0], ipInfo[1]);
            whiteList.setIpStart(ipStart);
            whiteList.setIpEnd(ipEnd);
        } else if (whiteList.getIpAddress().contains("-")) {
            String[] ipInfo = whiteList.getIpAddress().split("-");
            whiteList.setIpStart(ipInfo[0]);
            whiteList.setIpEnd(ipInfo[1]);
        } else {
            whiteList.setIpStart(whiteList.getIpAddress());
            whiteList.setIpEnd(whiteList.getIpAddress());
        }
    }

    /**
     * 新增
     *
     * @param whiteList 对应实体
     */
    @Param(name = "ipAddress", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void add(@Para("") WhiteList whiteList) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(whiteList, "ip_address");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        processIp(whiteList);
        renderJson(whiteList.save() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 编辑
     *
     * @param whiteList 对应实体
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ID)
    @Param(name = "ipAddress", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void update(@Para("") WhiteList whiteList) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(whiteList, "ip_address");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        processIp(whiteList);
        renderJson(whiteList.update() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 删除
     *
     * @param ids 待删除的ID字符串，多个之间以逗号分隔
     */
    @Param(name = "ids", required = true)
    public void delete(String ids) {
        renderJson(service.deleteByInnerSql(ids) ? BaseResult.ok() : BaseResult.fail());
    }
}
