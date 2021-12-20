package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.HumidityState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:HumidityStateDao.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: 查看内存使用情况
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Repository
public interface HumidityStateMapper {


    public List<HumidityState> selectAllByParams(Map<String, Object> map) throws Exception;

    public List<HumidityState> selectByParams(Map<String, Object> params) throws Exception;

    public HumidityState selectById(String id) throws Exception;

    public void save(HumidityState HumidityState) throws Exception;

    public void insertList(List<HumidityState> recordList) throws Exception;

    public int deleteByAccountAndDate(Map<String, Object> map) throws Exception;

    public int deleteById(String[] id) throws Exception;


}
