package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.TempState;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:TempStateDao.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: 查看内存使用情况
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Repository
public interface TempStateMapper {


    public List<TempState> selectAllByParams(Map<String, Object> map) throws Exception;

    public List<TempState> selectByParams(Map<String, Object> params) throws Exception;

    public TempState selectById(String id) throws Exception;

    public void save(TempState TempState) throws Exception;

    public void insertList(List<TempState> recordList) throws Exception;

    public int deleteByAccountAndDate(Map<String, Object> map) throws Exception;

    public int deleteById(String[] id) throws Exception;


}
