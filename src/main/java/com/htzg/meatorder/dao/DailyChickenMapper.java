package com.htzg.meatorder.dao;

import com.htzg.meatorder.domain.DailyChicken;
import com.htzg.meatorder.domain.DailyChickenExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DailyChickenMapper {
    long countByExample(DailyChickenExample example);

    int deleteByExample(DailyChickenExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DailyChicken record);

    int insertSelective(DailyChicken record);

    List<DailyChicken> selectByExample(DailyChickenExample example);

    DailyChicken selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DailyChicken record, @Param("example") DailyChickenExample example);

    int updateByExample(@Param("record") DailyChicken record, @Param("example") DailyChickenExample example);

    int updateByPrimaryKeySelective(DailyChicken record);

    int updateByPrimaryKey(DailyChicken record);
}