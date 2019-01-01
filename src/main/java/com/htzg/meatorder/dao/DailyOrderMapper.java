package com.htzg.meatorder.dao;

import com.htzg.meatorder.domain.DailyOrder;
import com.htzg.meatorder.domain.DailyOrderExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DailyOrderMapper {
    long countByExample(DailyOrderExample example);

    int deleteByExample(DailyOrderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DailyOrder record);

    int insertSelective(DailyOrder record);

    List<DailyOrder> selectByExample(DailyOrderExample example);

    DailyOrder selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DailyOrder record, @Param("example") DailyOrderExample example);

    int updateByExample(@Param("record") DailyOrder record, @Param("example") DailyOrderExample example);

    int updateByPrimaryKeySelective(DailyOrder record);

    int updateByPrimaryKey(DailyOrder record);
}