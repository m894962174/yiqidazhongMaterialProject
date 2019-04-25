package com.oracle.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.oracle.vo.PartsRepertory;
@Repository
public interface PartsRepertoryMapper {
    int deleteByPrimaryKey(Integer partsrepid);

    int insert(PartsRepertory record);

    int insertSelective(PartsRepertory record);

    PartsRepertory selectByPrimaryKey(Integer partsrepid);
    
    //根据partsid[] 查询
    public List<PartsRepertory> selectByPartsid(@Param("partsid")Integer[] partsid);
    
    public Integer selectByPartsid(@Param("partsid")Integer partsid);
    
    //查询出入库类型
    public String[] selectbilltype(@Param("billflag")String billflag);
    
    //返回partsid 
    public Integer selectByPartsname(@Param("partsname")String partsname);
    
    public List<PartsRepertory> selectAll();

    int updateByPrimaryKeySelective(PartsRepertory record);
    
    //仅返回partsrepertorycount
    public int selectCountByPrimaryKey(@Param("partsid")Integer partsid);
    
    //修改库存数量
    public void updateCount(@Param("partsid")Integer partsid,@Param("partsreqcount")Integer partsreqcount);
    
    //订单审核时；回退的修改库存数量
    public void updateAddCount(@Param("partsid")Integer partsid,@Param("partsreqcount")Integer partsreqcount);

    int updateByPrimaryKey(PartsRepertory record);
    
    public List<PartsRepertory> ambiguousSelect(@Param("partsid")Integer partsid,@Param("partsname")String partsname);
}