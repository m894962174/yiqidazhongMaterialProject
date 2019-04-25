package com.oracle.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.dao.PartsRepBillMapper;
import com.oracle.dao.PartsRepertoryMapper;
import com.oracle.dao.UserMapper;
import com.oracle.vo.PartsRepBill;
import com.oracle.vo.PartsRepertory;
import com.oracle.vo.User;

@Service
public class PartsRepertoryService {
	
	@Autowired 
	PartsRepertoryMapper partsrepertorydao;
	
	@Autowired 
	PartsRepBillMapper partsrepbilldao;
	
	@Autowired
	UserMapper userdao;

	@Transactional(readOnly=true)
	public List<PartsRepertory> selectAll(){
		
		return partsrepertorydao.selectAll();
	}
	
	@Transactional(readOnly=true)
	public List<PartsRepertory> ambiguousSelect(Integer partsid,String partsname){
		return partsrepertorydao.ambiguousSelect(partsid, partsname);
	}
	
	@Transactional(readOnly=true)
	public String[] selectbilltype(String billflag) {
		return partsrepertorydao.selectbilltype(billflag);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void  save(HttpSession session,String billflag,String billtype,String partsname,Integer billcount,String remarks) throws Exception {
		//获得当前配件的id 
		Integer partsid=partsrepertorydao.selectByPartsname(partsname);
		//修改库存数量
		partsrepertorydao.updateCount(partsid, billcount);
		//向配件出入库流水表增加记录
		PartsRepBill prb=new PartsRepBill();
		prb.setBillcount(billcount);
		prb.setBillflag(billflag);
		prb.setBilltime((new Date()).toString());
		prb.setBilltype(billtype);
		prb.setPartsid(partsid);
		User u=(User)session.getAttribute("user");
		prb.setBilluser(u.getUserid());
		partsrepbilldao.insert(prb);
		//判断是否数据越界
		System.out.println("is ok");
		Integer realCount=partsrepertorydao.selectByPartsid(partsid);
		System.out.println(realCount);
		if(realCount<0) {
				//事务回退:抛出异常时
				throw new Exception("出库数据不能大于真实数据");
		}
	}
	
	//根据partsid数组查询
	@Transactional(readOnly=true)
	public List<PartsRepertory> selectByPartsId(Integer[] partsid){
		return partsrepertorydao.selectByPartsid(partsid);
	}
}
