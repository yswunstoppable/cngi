package com.occamedu.monitor.server.service;

import com.occamedu.monitor.server.entity.SmsSet;
import com.occamedu.monitor.server.mapper.SmsSetMapper;
import com.occamedu.monitor.server.util.DateUtil;
import com.occamedu.monitor.server.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: DiskIoStateService.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Service
public class SmsSetService {


    public void save(SmsSet smsSet) throws Exception {
        smsSet.setId(UUIDUtil.getUUID());
        smsSet.setCreateTime(DateUtil.getNowTime());
        smsSetMapper.insertSelective(smsSet);
    }
    public int deleteById(String id) throws Exception {
        return smsSetMapper.deleteByPrimaryKey(id);
    }

    public List<SmsSet> selectAllByParams(Map<String, Object> params) throws Exception {
        return smsSetMapper.selectAllByParams(params);
    }

    public int updateById(SmsSet smsSet) {
        return smsSetMapper.updateByPrimaryKey(smsSet);
    }


    @Autowired
    private SmsSetMapper smsSetMapper;


}
