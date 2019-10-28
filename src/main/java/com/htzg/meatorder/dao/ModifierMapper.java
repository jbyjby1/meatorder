package com.htzg.meatorder.dao;

import com.htzg.meatorder.domain.Modifier;
import com.htzg.meatorder.domain.ModifierExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ModifierMapper {
    long countByExample(ModifierExample example);

    int deleteByExample(ModifierExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Modifier record);

    int insertSelective(Modifier record);

    List<Modifier> selectByExample(ModifierExample example);

    Modifier selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Modifier record, @Param("example") ModifierExample example);

    int updateByExample(@Param("record") Modifier record, @Param("example") ModifierExample example);

    int updateByPrimaryKeySelective(Modifier record);

    int updateByPrimaryKey(Modifier record);
}