package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.HostInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:HostInfoDao.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: HostInfoDao.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Repository
public interface HostInfoMapper {

    public List<HostInfo> selectAllByParams(Map<String, Object> map) throws Exception;

    public List<HostInfo> selectByParams(Map<String, Object> params) throws Exception;

    public HostInfo selectById(String id) throws Exception;

    public void save(HostInfo HostInfo) throws Exception;

    public int deleteById(String[] id) throws Exception;

    public int deleteByIp(String[] ip) throws Exception;

    public int updateById(HostInfo HostInfo) throws Exception;
}
