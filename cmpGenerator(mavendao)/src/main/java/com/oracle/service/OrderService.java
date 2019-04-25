package com.oracle.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.dao.OrderDetailMapper;
import com.oracle.dao.OrderMapper;
import com.oracle.dao.PartsRepBillMapper;
import com.oracle.dao.PartsRepertoryMapper;
import com.oracle.vo.Order;
import com.oracle.vo.PartsRepBill;
import com.oracle.vo.User;

@Service
public class OrderService {

	@Autowired
	OrderMapper orderdao;
	
	@Autowired
	OrderDetailMapper orderdetaildao;
	
	@Autowired
	PartsRepertoryMapper partsrepertorydao;
	
	@Autowired
	PartsRepBillMapper partsrepbilldao;
	
	@Transactional(readOnly=true)
	public List<Order> selectAll(){
		return orderdao.selectAll();
	}
	
	//只有新订单有删除更新！将订单主表和订单字表的内容删除，更新配件库存数量
	@Transactional
	public void deleteByPrimaryKey(Integer PrimaryKey) {
		//1.判断订单状态 若为 --未提交-- 则不需要更新库存数量
		if(orderdao.selectByPrimaryKey(PrimaryKey).getOrderflag().equals("0")) {
			//2.--未提交--:更新配件库存数量
			int[] orderpartscount=orderdetaildao.selectCounts(PrimaryKey);
			for(int i:orderpartscount) {
				orderdetaildao.updateAfterSelect(PrimaryKey, i);
			}
		}
		//3.删除订单主表与子表
		orderdetaildao.deleteByOrderId(PrimaryKey);
		orderdao.deleteByPrimaryKey(PrimaryKey);
		System.out.println("删除完成！业务完毕");
	}
	
	@Transactional(readOnly=true)
	public List<Order> selectambiguous(String ordercode,String orderdate,String orderflag){
		return orderdao.selectambiguous(ordercode, orderdate, orderflag);
	}
	
	//ajax实时校验ordercode
	@Transactional(readOnly=true)
	public Boolean selectByOrderCode(String ordercode) {
		if(orderdao.selectByordercode(ordercode)!=null) {
			return false;
		}else {
			return true;
		}
	}
	
	//根据orderid 查询
	@Transactional(readOnly=true)
	public List<Order> mySelectByPrimarykey(Integer orderid) {
		return orderdao.mySelectByPrimaryKey(orderid);
	}
	
	@Transactional(readOnly=true)
	public int selectCountByPrimaryKey(Integer partsid) {
		return partsrepertorydao.selectCountByPrimaryKey(partsid);
	}
	
	@Transactional(readOnly=true)
	public String selectordercode(String ordercode) {
		return orderdao.selectByordercode(ordercode);
	}
	
	@Transactional()
	public void saveFunction(String ordercode,String orderdate,String orderflag,Integer[] partsid,Integer[] orderpartscount,Integer[] partsreqcount) {
		//向主订单增加数据
		orderdao.saveOrder(ordercode, orderdate, orderflag);
		//主订单自动生成的orderid
		Integer orderid=orderdao.selectorderid(ordercode);
		//像订单子表增加数据
		for(int i=0;i<partsid.length;i++) {
			orderdao.saveOrderdetail(orderid, partsid[i], orderpartscount[i]);
		}
		System.out.println("订单保存成功!");
	}
	
	//保存订单所用的出库功能
	@Transactional(rollbackFor=Exception.class)
	public void  updateRepertoryCount(HttpSession session,String orderdate,String billflag,String billtype,Integer partsid,Integer billcount,String remarks) throws Exception {
		//修改库存数量
		partsrepertorydao.updateCount(partsid, billcount);
		//向配件出入库流水表增加记录
		PartsRepBill prb=new PartsRepBill();
		prb.setBillcount(billcount);
		prb.setBillflag(billflag);
		prb.setBilltime(orderdate);
		prb.setBilltype(billtype);
		prb.setPartsid(partsid);
		User u=(User)session.getAttribute("user");
		prb.setBilluser(u.getUserid());
		partsrepbilldao.insert(prb);
		//判断是否数据越界
		System.out.println("is ok");
		Integer realCount=partsrepertorydao.selectCountByPrimaryKey(partsid);
		System.out.println(realCount);
		if(realCount<0) {
				//事务回退:抛出异常时
				throw new Exception("出库数据不能大于真实数据");
		}
	}
	
	//修改订单主表及其子表
	@Transactional()
	public void updateAll(String orderflag,Integer[] partsid,String ordercode,String orderdate,Integer[] orderpartscount,Integer[] partsrepcount) {
		
		//修改order表
		orderdao.updateOrder(ordercode, orderdate, orderflag);
		//根据orderid查询出需要修改的detailId
		Integer orderid=orderdao.selectorderid(ordercode);
		int[] did=orderdao.selectDetailId(orderid);
		//现记录数小于原记录数时:删除多余的记录
//		if(partsid.length<did.length) {
//			orderdetaildao.deleteByPrimaryKey(did);
//		}
		//判断并修改detail表
		for(int i=1;i<=partsid.length;i++) {
			//update  detail表
			orderdetaildao.updateOrderDetail(did[i-1], partsid[i-1], orderpartscount[i-1]);
			//遍历到记录数大于原记录数时：添加新记录
			if(i>did.length) {
				//向detail表中insert新记录
				orderdao.saveOrderdetail(orderid,partsid[i-1],orderpartscount[i-1]);
			}
		}
	}
	
	//订单审核
	@Transactional
	public void changeOrderFlag(String ordercode,String orderflag) {
		orderdao.updateOrderFlag(orderflag, ordercode);
		//判断是否需要回退配件的出库
		if(ordercode.equals("3")) {
			Integer orderid=orderdao.selectorderid(ordercode);
			List<Order> list=orderdao.mySelectByPrimaryKey(orderid);
			for(Order o:list) {
				partsrepertorydao.updateAddCount(o.getPartsid(),o.getPartsrepcount());
			}
			
		}
	}
}
