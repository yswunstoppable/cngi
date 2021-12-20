package com.occamedu.security.log.attack;

import cn.fabrice.jfinal.annotation.ValidateParam;
import cn.fabrice.common.pojo.DataResult;
import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.AttackSourceLog;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 22:00
 * @description
 */
@ValidateParam
public class AttackSourceLogController extends Controller {
    @Inject
    private AttackSourceLogService service;

    /**
     * 获取攻击源日志列表
     *
     * @param params 请求参数实体
     */
    public void list(@Para("") RequestParams params) {
        Page<AttackSourceLog> page = service.list(params.getPageNumber(), params.getPageSize());
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    public void getLast() {
        renderJson(DataResult.data(service.list("getLast")));
    }

    public void listByAddress() {
        renderJson(DataResult.data(service.list("listByAddress")));
    }

    public void listStatics(@Para("") RequestParams params) {
        Page<AttackSourceLog> page = service.list(params.getPageNumber(), params.getPageSize(), "listStatics");
        int index = (page.getPageNumber() - 1) * page.getPageSize();
        for (AttackSourceLog log : page.getList()) {
            index++;
            log.put("index", index);
        }
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }

    public void staticsByMonth() {
        renderJson(DataResult.data(service.list("staticsByMonth")));
    }

}
