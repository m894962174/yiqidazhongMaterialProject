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
	
	//ֻ���¶�����ɾ�����£�����������Ͷ����ֱ������ɾ������������������
	@Transactional
	public void deleteByPrimaryKey(Integer PrimaryKey) {
		//1.�ж϶���״̬ ��Ϊ --δ�ύ-- ����Ҫ���¿������
		if(orderdao.selectByPrimaryKey(PrimaryKey).getOrderflag().equals("0")) {
			//2.--δ�ύ--:��������������
			int[] orderpartscount=orderdetaildao.selectCounts(PrimaryKey);
			for(int i:orderpartscount) {
				orderdetaildao.updateAfterSelect(PrimaryKey, i);
			}
		}
		//3.ɾ�������������ӱ�
		orderdetaildao.deleteByOrderId(PrimaryKey);
		orderdao.deleteByPrimaryKey(PrimaryKey);
		System.out.println("ɾ����ɣ�ҵ�����");
	}
	
	@Transactional(readOnly=true)
	public List<Order> selectambiguous(String ordercode,String orderdate,String orderflag){
		return orderdao.selectambiguous(ordercode, orderdate, orderflag);
	}
	
	//ajaxʵʱУ��ordercode
	@Transactional(readOnly=true)
	public Boolean selectByOrderCode(String ordercode) {
		if(orderdao.selectByordercode(ordercode)!=null) {
			return false;
		}else {
			return true;
		}
	}
	
	//����orderid ��ѯ
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
		//����������������
		orderdao.saveOrder(ordercode, orderdate, orderflag);
		//�������Զ����ɵ�orderid
		Integer orderid=orderdao.selectorderid(ordercode);
		//�񶩵��ӱ���������
		for(int i=0;i<partsid.length;i++) {
			orderdao.saveOrderdetail(orderid, partsid[i], orderpartscount[i]);
		}
		System.out.println("��������ɹ�!");
	}
	
	//���涩�����õĳ��⹦��
	@Transactional(rollbackFor=Exception.class)
	public void  updateRepertoryCount(HttpSession session,String orderdate,String billflag,String billtype,Integer partsid,Integer billcount,String remarks) throws Exception {
		//�޸Ŀ������
		partsrepertorydao.updateCount(partsid, billcount);
		//������������ˮ�����Ӽ�¼
		PartsRepBill prb=new PartsRepBill();
		prb.setBillcount(billcount);
		prb.setBillflag(billflag);
		prb.setBilltime(orderdate);
		prb.setBilltype(billtype);
		prb.setPartsid(partsid);
		User u=(User)session.getAttribute("user");
		prb.setBilluser(u.getUserid());
		partsrepbilldao.insert(prb);
		//�ж��Ƿ�����Խ��
		System.out.println("is ok");
		Integer realCount=partsrepertorydao.selectCountByPrimaryKey(partsid);
		System.out.println(realCount);
		if(realCount<0) {
				//�������:�׳��쳣ʱ
				throw new Exception("�������ݲ��ܴ�����ʵ����");
		}
	}
	
	//�޸Ķ����������ӱ�
	@Transactional()
	public void updateAll(String orderflag,Integer[] partsid,String ordercode,String orderdate,Integer[] orderpartscount,Integer[] partsrepcount) {
		
		//�޸�order��
		orderdao.updateOrder(ordercode, orderdate, orderflag);
		//����orderid��ѯ����Ҫ�޸ĵ�detailId
		Integer orderid=orderdao.selectorderid(ordercode);
		int[] did=orderdao.selectDetailId(orderid);
		//�ּ�¼��С��ԭ��¼��ʱ:ɾ������ļ�¼
//		if(partsid.length<did.length) {
//			orderdetaildao.deleteByPrimaryKey(did);
//		}
		//�жϲ��޸�detail��
		for(int i=1;i<=partsid.length;i++) {
			//update  detail��
			orderdetaildao.updateOrderDetail(did[i-1], partsid[i-1], orderpartscount[i-1]);
			//��������¼������ԭ��¼��ʱ������¼�¼
			if(i>did.length) {
				//��detail����insert�¼�¼
				orderdao.saveOrderdetail(orderid,partsid[i-1],orderpartscount[i-1]);
			}
		}
	}
	
	//�������
	@Transactional
	public void changeOrderFlag(String ordercode,String orderflag) {
		orderdao.updateOrderFlag(orderflag, ordercode);
		//�ж��Ƿ���Ҫ��������ĳ���
		if(ordercode.equals("3")) {
			Integer orderid=orderdao.selectorderid(ordercode);
			List<Order> list=orderdao.mySelectByPrimaryKey(orderid);
			for(Order o:list) {
				partsrepertorydao.updateAddCount(o.getPartsid(),o.getPartsrepcount());
			}
			
		}
	}
}
