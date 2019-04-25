package com.oracle.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oracle.vo.OrderDetail;
@Repository
public interface OrderDetailMapper {
    int deleteByPrimaryKey(Integer orderdetailid);
    
    public void updateOrderDetail(@Param("orderdetailid")Integer orderdetailid,@Param("partsid")Integer partsid,@Param("orderpartscount")Integer orderpartscount);
    
    public void deleteByOrderId(@Param("orderid")Integer orderid);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(Integer orderdetailid);
    
    public int[] selectCounts(@Param("orderid")Integer orderid);

    public void updateAfterSelect(@Param("orderid")Integer orderid,@Param("orderpartscount")Integer orderpartscount);
    
    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}