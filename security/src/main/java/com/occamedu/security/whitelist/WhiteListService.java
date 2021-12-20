package com.occamedu.security.whitelist;

import cn.fabrice.kit.http.IpKit;
import cn.fabrice.jfinal.service.BaseService;
import com.jfinal.kit.Kv;
import com.occamedu.security.common.module.WhiteList;

import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-03 16:33
 * @description
 */
public class WhiteListService extends BaseService<WhiteList> {
    public WhiteListService() {
        super("whitelist.", WhiteList.class, "white_list");
    }

    public boolean isWhite(String ipAddress) {
        List<WhiteList> usedWhiteList = list("listByUsed");
        return usedWhiteList.parallelStream()
                .anyMatch(whiteList -> IpKit.ipExistsInRange(whiteList.getIpStart(), whiteList.getIpEnd(), ipAddress));
    }
}
