package com.occamedu.security.center.boundary;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.Boundary;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 17:04
 * @description
 */
public class BoundaryService extends BaseService<Boundary> {
    public BoundaryService() {
        super("boundary.", Boundary.class, "boundary");
    }
}
