package com.oracle.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.dao.PartsRepBillMapper;
import com.oracle.dao.PartsRepertoryMapper;
import com.oracle.vo.PartsRepBill;

@Service
public class PartsRepBillService {

	@Autowired
	PartsRepBillMapper partsrepbilldao;
	
	@Autowired
	PartsRepertoryMapper partsrepertorydao;
	
	@Transactional(readOnly=true)
	public List<PartsRepBill> selectAll(){
		return partsrepbilldao.selectAll();
	}
	
	@Transactional(readOnly=true)
	public String[] selectbilltype(String billflag) {
		return partsrepertorydao.selectbilltype(billflag);
	}
	
	@Transactional(readOnly=true)
	public List<PartsRepBill> selectambiguous(String partsname,String billflag,String billtype,String billtime){
		return partsrepbilldao.selectambiguous(partsname, billflag, billtype, billtime);
	}
}
