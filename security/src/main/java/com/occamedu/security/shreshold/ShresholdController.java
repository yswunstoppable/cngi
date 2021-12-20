package com.occamedu.security.shreshold;

import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.Shreshold;

/**
 * @author ysw
 * @email 1461182123@qq.com.com
 * @date 2021-09-08 15:08
 * @description
 */
@ValidateParam
public class ShresholdController extends Controller {
    @Inject
    private ShresholdService service;

    /**
     * 获取数据列表
     *
     * @param pageNumber 页码
     * @param pageSize   页长
     */
    public void list(int pageNumber, int pageSize) {
        Page<Shreshold> page = service.list(pageNumber, pageSize);
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }


    /**
     * 新增阈值配置
     *
     * @param shreshold 对应实体类
     */
    @Param(name = "name", required = true)
    @Param(name = "key", required = true)
    @Param(name = "value", required = true)
    public void add(@Para("") Shreshold shreshold) {
        renderJson(shreshold.save() ? DataResult.data(shreshold.getId()) : BaseResult.fail());
    }

    /**
     * 新增阈值配置
     *
     * @param shreshold 对应实体类
     */
    @Param(name = "id", required = true, rule = ValidateRuleConstants.Key.ID)
    @Param(name = "name")
    @Param(name = "key")
    @Param(name = "value")
    public void update(@Para("") Shreshold shreshold) {
        renderJson(shreshold.update() ? DataResult.data(shreshold.getId()) : BaseResult.fail());
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
     * 判断参数值是否重复
     *
     * @param id    ID
     * @param field 校验字段
     * @param value 校验字段值
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ZERO_POSITIVE_INTEGER)
    @Param(name = "field", required = true, customRule = "(name)|(key)")
    public void judge(long id, String field, String value) {
        Kv cond = Kv.by("id", id).set("field", field).set("value", value);
        boolean flag = service.isExist(cond);
        renderJson(flag ? DataResult.data("fail") : DataResult.data("ok"));
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
