package com.oracle.handler;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.service.PartsRepertoryService;
import com.oracle.vo.PartsRepertory;

@Controller
@RequestMapping("/pages/partssys/partsrep/")
public class PartsRepertoryHandler {

	@Autowired
	PartsRepertoryService partsrepertoryservice;
	
	//����б�
	@RequestMapping("/repertorylist/{start}")
	public String repertoryList(@PathVariable(name="start")Integer start,Map<String,Object> map) {
		PageHelper.startPage(start, 5);
		List<PartsRepertory> list=partsrepertoryservice.selectAll();
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(list);
		map.put("pageinfo", pageinfo);
		return "/pages/partssys/partsrep/partsreplist";
	}
	
	//ת����������ҳ��;
	@RequestMapping("/repertorylist/addpartsrep")
	public String redirectAddPath(Map<String,Object> map) {
		map.put("time", new Date());
		return "/pages/partssys/partsrep/partsrep";
	}
	
	//ajaxҳ��ֲ�ˢ�¼����������
	@RequestMapping("/repertorylist/partsrepajax")
	@ResponseBody
	public List<PartsRepertory> ajax(){
		List<PartsRepertory> list=partsrepertoryservice.selectAll();
		return list;
	}
	
	//ajax:���������
	@RequestMapping("/repertorylist/billajax")
	@ResponseBody
	public String[] selectbilltype(String billflag) {
		return partsrepertoryservice.selectbilltype(billflag);
	}
	
	//��������ı���
	@RequestMapping("/repertorylist/partsbillsave")
	public String partsBillSave(HttpSession session,String billflag,String billtype,String partsname,Integer billcount,String remarks) throws Exception {
		partsrepertoryservice.save(session, billflag, billtype, partsname, billcount, remarks);
		System.out .println("�����gong!");
		return "redirect:/pages/partssys/partsrep/repertorylist/0";
	}
	
	//ģ����ѯ
	@RequestMapping("/repertorylist/ambiguous")
	public String ambiguousSelect(Integer partsid,String partsname,Map<String,Object> map) {
		PageHelper.startPage(0, 5);
		List<PartsRepertory> list=partsrepertoryservice.ambiguousSelect(partsid, partsname);
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(list);
		map.put("pageinfo",pageinfo);
		return "/pages/partssys/partsrep/partsreplist";
	}
	
	
}
