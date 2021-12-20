package com.occamedu.security.shreshold;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.Shreshold;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-08 15:06
 * @description
 */
public class ShresholdService extends BaseService<Shreshold> {
    public ShresholdService() {
        super("shreshold.", Shreshold.class, "shreshold");
    }
}
