package com.occamedu.monitor.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.occamedu.monitor.server.entity.HumidityState;
import com.occamedu.monitor.server.mapper.HumidityStateMapper;
import com.occamedu.monitor.server.util.DateUtil;
import com.occamedu.monitor.server.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:HumidityStateService.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: HumidityStateService.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Service
public class HumidityStateService {

    public PageInfo selectByParams(Map<String, Object> params, int currPage, int pageSize) throws Exception {
        PageHelper.startPage(currPage, pageSize);
        List<HumidityState> list = humidityStateMapper.selectByParams(params);
        PageInfo<HumidityState> pageInfo = new PageInfo<HumidityState>(list);
        return pageInfo;
    }

    public void save(HumidityState humidityState) throws Exception {
        humidityState.setId(UUIDUtil.getUUID());
        humidityState.setCreateTime(DateUtil.getNowTime());
        humidityState.setDateStr(DateUtil.getDateTimeString(humidityState.getCreateTime()));
        humidityStateMapper.save(humidityState);
    }

    public void saveRecord(List<HumidityState> recordList) throws Exception {
        if (recordList.size() < 1) {
            return;
        }
        for (HumidityState as : recordList) {
            as.setId(UUIDUtil.getUUID());
            as.setDateStr(DateUtil.getDateTimeString(as.getCreateTime()));
        }
        humidityStateMapper.insertList(recordList);
    }

    public int deleteById(String[] id) throws Exception {
        return humidityStateMapper.deleteById(id);
    }

    public HumidityState selectById(String id) throws Exception {
        return humidityStateMapper.selectById(id);
    }

    public List<HumidityState> selectAllByParams(Map<String, Object> params) throws Exception {
        return humidityStateMapper.selectAllByParams(params);
    }


    @Autowired
    private HumidityStateMapper humidityStateMapper;


}
