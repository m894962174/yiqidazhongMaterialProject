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
    
    //����partsid[] ��ѯ
    public List<PartsRepertory> selectByPartsid(@Param("partsid")Integer[] partsid);
    
    public Integer selectByPartsid(@Param("partsid")Integer partsid);
    
    //��ѯ���������
    public String[] selectbilltype(@Param("billflag")String billflag);
    
    //����partsid 
    public Integer selectByPartsname(@Param("partsname")String partsname);
    
    public List<PartsRepertory> selectAll();

    int updateByPrimaryKeySelective(PartsRepertory record);
    
    //������partsrepertorycount
    public int selectCountByPrimaryKey(@Param("partsid")Integer partsid);
    
    //�޸Ŀ������
    public void updateCount(@Param("partsid")Integer partsid,@Param("partsreqcount")Integer partsreqcount);
    
    //�������ʱ�����˵��޸Ŀ������
    public void updateAddCount(@Param("partsid")Integer partsid,@Param("partsreqcount")Integer partsreqcount);

    int updateByPrimaryKey(PartsRepertory record);
    
    public List<PartsRepertory> ambiguousSelect(@Param("partsid")Integer partsid,@Param("partsname")String partsname);
}