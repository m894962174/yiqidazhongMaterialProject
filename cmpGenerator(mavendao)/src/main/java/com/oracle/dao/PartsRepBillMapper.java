package com.oracle.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oracle.vo.PartsRepBill;
@Repository
public interface PartsRepBillMapper {
    int deleteByPrimaryKey(Integer billid);

    int insert(PartsRepBill record);

    int insertSelective(PartsRepBill record);

    public List<PartsRepBill> selectambiguous(@Param("partsname")String partsname,@Param("billflag")String billflag,@Param("billtype")String billtype,@Param("billtime")String billtime);
    
    PartsRepBill selectByPrimaryKey(Integer billid);
    
    public List<PartsRepBill> selectAll();

    int updateByPrimaryKeySelective(PartsRepBill record);

    int updateByPrimaryKey(PartsRepBill record);
}