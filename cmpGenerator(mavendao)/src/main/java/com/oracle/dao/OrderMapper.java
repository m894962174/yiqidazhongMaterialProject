package com.oracle.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oracle.vo.Order;
@Repository
public interface OrderMapper {
    int deleteByPrimaryKey(Integer orderid);

    int insert(Order record);

    int insertSelective(Order record);
    
    public void updateOrderFlag(@Param("orderflag")String orderflag,@Param("ordercode")String ordercode);
    
    public int[] selectDetailId(@Param("orderid")Integer orderid);
    
    public void updateOrder(@Param("ordercode")String ordercode,@Param("orderdate")String orderdate,@Param("orderflag")String orderflag);
    
    public void saveOrderdetail(@Param("orderid")Integer orderid,@Param("partsid")Integer partsid,@Param("orderpartscount")Integer orderpartscount);
    
    public int selectorderid(@Param("ordercode")String ordercode);
    
    public void saveOrder(@Param("ordercode")String ordercode,@Param("orderdate")String orderdate,@Param("orderflag")String orderflag);
    
    public List<Order> mySelectByPrimaryKey(@Param("orderid")Integer orderid);
    
    public String selectByordercode(@Param("ordercode")String ordercode);

    public List<Order> selectAll();
    
    public List<Order> selectambiguous(@Param("ordercode")String ordercode,@Param("orderdate")String orderdate,@Param("orderflag")String orderflag);
    
    public String selectordercode(@Param("ordercode")String ordercode);
    
    Order selectByPrimaryKey(Integer orderid);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}