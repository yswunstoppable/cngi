package com.occamedu.monitor.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.occamedu.monitor.server.entity.TempState;
import com.occamedu.monitor.server.mapper.TempStateMapper;
import com.occamedu.monitor.server.util.DateUtil;
import com.occamedu.monitor.server.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:TempStateService.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: TempStateService.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Service
public class TempStateService {

    public PageInfo selectByParams(Map<String, Object> params, int currPage, int pageSize) throws Exception {
        PageHelper.startPage(currPage, pageSize);
        List<TempState> list = tempStateMapper.selectByParams(params);
        PageInfo<TempState> pageInfo = new PageInfo<TempState>(list);
        return pageInfo;
    }

    public void save(TempState tempState) throws Exception {
        tempState.setId(UUIDUtil.getUUID());
        tempState.setCreateTime(DateUtil.getNowTime());
        tempState.setDateStr(DateUtil.getDateTimeString(tempState.getCreateTime()));
        tempStateMapper.save(tempState);
    }

    public void saveRecord(List<TempState> recordList) throws Exception {
        if (recordList.size() < 1) {
            return;
        }
        for (TempState as : recordList) {
            as.setId(UUIDUtil.getUUID());
            as.setDateStr(DateUtil.getDateTimeString(as.getCreateTime()));
        }
        tempStateMapper.insertList(recordList);
    }

    public int deleteById(String[] id) throws Exception {
        return tempStateMapper.deleteById(id);
    }

    public TempState selectById(String id) throws Exception {
        return tempStateMapper.selectById(id);
    }

    public List<TempState> selectAllByParams(Map<String, Object> params) throws Exception {
        return tempStateMapper.selectAllByParams(params);
    }


    @Autowired
    private TempStateMapper tempStateMapper;


}
