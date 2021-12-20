package com.occamedu.monitor.server.mapper;

import com.occamedu.monitor.server.entity.Shreshold;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ShresholdMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Shreshold record);

    int insertSelective(Shreshold record);

    Shreshold selectByPrimaryKey(Long id);

    @Select("select value from shreshold where `key` = #{key} and is_deleted = 0 limit 1")
    String selectValueByKey(String key);

    int updateByPrimaryKeySelective(Shreshold record);

    int updateByPrimaryKey(Shreshold record);
}