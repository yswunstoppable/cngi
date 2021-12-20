package com.occamedu.security.log.command;

import cn.fabrice.common.pojo.TableResult;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Page;
import com.occamedu.security.common.module.TimerCommandLog;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 22:18
 * @description
 */
public class TimerCommandLogController extends Controller {
    @Inject
    private TimerCommandLogService service;

    /**
     * 获取攻击源日志列表
     *
     * @param params 请求参数实体
     */
    public void list(@Para("") RequestParams params) {
        Page<TimerCommandLog> page = service.list(params.getPageNumber(), params.getPageSize());
        renderJson(TableResult.set(page.getTotalRow(), page.getList()));
    }
}
