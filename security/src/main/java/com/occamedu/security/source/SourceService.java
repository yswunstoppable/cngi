package com.occamedu.security.source;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.PerceptionSource;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 15:06
 * @description
 */
public class SourceService extends BaseService<PerceptionSource> {
    public SourceService() {
        super("source.", PerceptionSource.class, "perception_source");
    }
}
