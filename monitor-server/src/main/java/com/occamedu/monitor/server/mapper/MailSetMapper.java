package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.MailSet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:MailSetMapper.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: 查看磁盘IO使用情况
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Repository
public interface MailSetMapper {


    public List<MailSet> selectAllByParams(Map<String, Object> map) throws Exception;

    public void save(MailSet MailSet) throws Exception;

    public int deleteById(String[] id) throws Exception;

    public int updateById(MailSet MailSet) throws Exception;


}
