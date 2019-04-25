package com.oracle.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.vo.Parts;
@Repository
public interface PartsMapper {
    int deleteByPrimaryKey(Integer partsid);

    int insert(Parts record);

    int insertSelective(Parts record);
    
    public List<Parts> selectAll();

    Parts selectByPrimaryKey(Integer partsid);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);
}