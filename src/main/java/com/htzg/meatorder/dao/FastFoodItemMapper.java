package com.htzg.meatorder.dao;

import com.htzg.meatorder.domain.FastFoodItem;
import com.htzg.meatorder.domain.FastFoodItemExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FastFoodItemMapper {
    long countByExample(FastFoodItemExample example);

    int deleteByExample(FastFoodItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FastFoodItem record);

    int insertSelective(FastFoodItem record);

    List<FastFoodItem> selectByExample(FastFoodItemExample example);

    FastFoodItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FastFoodItem record, @Param("example") FastFoodItemExample example);

    int updateByExample(@Param("record") FastFoodItem record, @Param("example") FastFoodItemExample example);

    int updateByPrimaryKeySelective(FastFoodItem record);

    int updateByPrimaryKey(FastFoodItem record);
}