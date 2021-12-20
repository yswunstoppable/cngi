package com.occamedu.security.center.command;

import cn.fabrice.jfinal.annotation.Param;
import cn.fabrice.jfinal.constant.ValidateRuleConstants;
import cn.fabrice.kit.Kits;
import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.Command;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:02
 * @description
 */
public class CommandController extends Controller {
    @Inject
    private CommandService service;

    /**
     * 获取列表
     *
     * @param params 请求参数实体类
     */
    public void list(@Para("") RequestParams params) {
        Kv cond = Kv.by("rq", params);
        Page<Command> page = service.list(params.getPageNumber(), params.getPageSize(), cond);
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    /**
     * 获取正在使用的命令
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
    @Param(name = "field", required = true, customRule = "(name)|(content)")
    public void judge(long id, String field, String value) {
        boolean flag = service.isExistByInnerSql(id, field, value);
        renderJson(flag ? DataResult.data("fail") : DataResult.data("ok"));
    }

    /**
     * 新增
     *
     * @param command 对应实体
     */
    @Param(name = "name", required = true)
    @Param(name = "content", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void add(@Para("") Command command) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(command, "name", "content");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(command.save() ? BaseResult.ok() : BaseResult.fail());
    }

    /**
     * 编辑
     *
     * @param command 对应实体
     */
    @Param(name = "id", rule = ValidateRuleConstants.Key.ID)
    @Param(name = "name", required = true)
    @Param(name = "content", required = true)
    @Param(name = "status", required = true, customRule = "1|2")
    public void update(@Para("") Command command) {
        BaseResult result = service.judgeFieldsIsExistByInnerSql(command, "name", "content");
        if (result.isFail()) {
            result.setMsg("字段：" + result.getMsg() + "的值已存在");
            renderJson(result);
            return;
        }
        renderJson(command.update() ? BaseResult.ok() : BaseResult.fail());
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
