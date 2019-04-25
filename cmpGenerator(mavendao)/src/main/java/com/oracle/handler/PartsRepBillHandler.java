package com.oracle.handler;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.service.PartsRepBillService;
import com.oracle.vo.PartsRepBill;

@Controller
@RequestMapping("/pages/partssys/partsrepbill/")
public class PartsRepBillHandler {

	@Autowired
	PartsRepBillService partsrepbillservice;
	
	//分页
//	@RequestMapping("/partsrepbilllist")
//	public String selectAll(@PathVariable(name="start")Integer start,Map<String,Object> map) {
//		PageHelper.startPage(start,5);
//		List<PartsRepBill> list=partsrepbillservice.selectAll();
//		PageInfo<PartsRepBill> pageinfo=new PageInfo<PartsRepBill>(list);
//		map.put("pageinfo",pageinfo);
//		return "pages/partssys/partsrepbill/partsrepbilllist";
//	}
	
	//ajax：出入库类型的二级联动
	@RequestMapping("/ajaxbilltype")
	@ResponseBody
	public String[] ajaxbilltype(String billflag) {
		return partsrepbillservice.selectbilltype(billflag);
	}
	
	//带分页的条件查询
	@RequestMapping("/partsrepbilllist")
	//@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	public String selectambiguous(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,@RequestParam(name="partsname",defaultValue="")String partsname,@RequestParam(name="billflag",defaultValue="")String billflag,@RequestParam(name="billtype",defaultValue="")String billtype,@RequestParam(name="billtime",defaultValue="")String billtime,Map<String,Object> map) {
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("partsname", partsname);
		condition.put("billflag", billflag);
		condition.put("billtype", billtype);
		condition.put("billtime", billtime);
		
		//向map中存入带有分页信息的condition
		map.put("condition", condition);
		PageHelper.startPage(pageNum, 5);
		List<PartsRepBill> list=partsrepbillservice.selectambiguous(partsname, billflag, billtype, billtime);
		PageInfo<PartsRepBill> pageinfo=new PageInfo<PartsRepBill>(list);
		map.put("pageinfo", pageinfo);
		return "/pages/partssys/partsrepbill/partsrepbilllist";
	}
}
