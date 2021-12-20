package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.MailSet;
import com.occamedu.monitor.server.entity.SmsSet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SmsSetMapper {
    int deleteByPrimaryKey(String id);

    int insert(SmsSet record);

    int insertSelective(SmsSet record);

    SmsSet selectByPrimaryKey(String id);

    List<SmsSet> selectAllByParams(Map<String, Object> params);

    int updateByPrimaryKeySelective(SmsSet record);

    int updateByPrimaryKey(SmsSet record);
}