package com.occamedu.security.center.command;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.Command;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:04
 * @description
 */
public class CommandService extends BaseService<Command> {
    public CommandService(){
        super("command.",Command.class,"command");
    }
}
