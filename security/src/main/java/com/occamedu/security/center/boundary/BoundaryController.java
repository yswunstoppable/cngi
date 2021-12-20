package com.occamedu.security.center.boundary;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.Boundary;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:01
 * @description
 */
@ValidateParam
public class BoundaryController extends Controller {
    @Inject
    private BoundaryService service;

    /**
     * 获取列表
     *
     * @param params 请求参数实体类
     */
    public void list(@Para("") RequestParams params) {
        Kv cond = Kv.by("rq", params);
        Page<Boundary> page = service.list(params.getPageNumber(), params.getPageSize(), cond);
        page.getList().forEach(item -> {
            item.put("username", "******");
            item.put("password", "********");
        });
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 获取正在使用的边界
     */
    public void listUsed() {
        RequestParams params = new RequestParams();
        params.setStatus(1);
        renderJson(DataResult.data(service.list(Kv.by("rq", params))));
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

    /**
     * 新增
     *
     * @param boundary 对应实体
     */
    @Param(name = "ipAddress", required = true)
    @Param(name = "username", required = true)
    @Param(name = "password", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void add(@Para("") Boundary boundary) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(boundary, "ipAddress");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(boundary.save() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 编辑
     *
     * @param boundary 对应实体
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ID)
    @Param(name = "ipAddress", required = true)
    @Param(name = "username", required = true)
    @Param(name = "password", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void update(@Para("") Boundary boundary) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(boundary, "ipAddress");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(boundary.update() ? BaseResult.ok() : BaseResult.fail());
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
