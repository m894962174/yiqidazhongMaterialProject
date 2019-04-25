package com.oracle.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.service.PartsService;
import com.oracle.vo.Parts;

@Controller
@RequestMapping("/pages/partssys/parts/")
public class PartsHandler {
	
	@Autowired
	PartsService partsservice;

	Integer pid;	//修改回显时所用的partsid
	
	
	//路径映射   ;
//	@RequestMapping("/pnumber/{path}")
//	public String defaultPath(@PathVariable("path") String path) {		
//		return "/pages/partssys/parts/"+path;
//	}
	
	@RequestMapping("/pnumber/partsadd")
	public String redirectAddPath() {
		return "/pages/partssys/parts/partsadd";
	}
	
	//分页的页数路径映射
	@RequestMapping("/pnumber/{start}")
	public String partsList(@PathVariable(name="start",required=false)Integer start,Map<String,Object> map) {
		PageHelper.startPage(start, 5);
		List<Parts> list=partsservice.selectAll();
		PageInfo<Parts> pageinfo=new PageInfo<Parts>(list);
		map.put("pageinfo", pageinfo);
		return "/pages/partssys/parts/partslist";
	}
	
	//分页
//	@RequestMapping("/partslist")
//	public String partsList1(@RequestParam(name="start",defaultValue="1")Integer start,Map<String,Object> map) {
//		PageHelper.startPage(start, 5);
//		List<Parts> list=partsservice.selectAll();
//		PageInfo<Parts> pageinfo=new PageInfo<Parts>(list);
//		map.put("pageinfo", pageinfo);
//		return "/pages/partssys/parts/partslist";
//	}
	
	//跳转页面
	@RequestMapping("/pnumber/jump")
	public String jumpPage(@RequestParam(name="jumpPage",defaultValue="1")Integer jumpPage,Map<String,Object> map) {
		PageHelper.startPage(jumpPage, 5);
		List<Parts> list=partsservice.selectAll();
		PageInfo<Parts> pageinfo=new PageInfo<Parts>(list);
		map.put("pageinfo", pageinfo);
		return "/pages/partssys/parts/partslist";
	}
	
	//删除配件
	@RequestMapping(value="/pnumber/deleteparts")
	public String deleteByPrimaryKey(HttpServletResponse response,Integer partsid) {
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			partsservice.deleteByPrimaryKey(partsid);
		}catch(Exception e) {
			PrintWriter out=null;
			try {
				out=response.getWriter();
				//此处有BUG ；location.href='' 无法定位到页面  可能原因: alert在新的空白页打开 因此路径发生变化
				out.append("<script type='text/javascript'>alert('删除失败,该配件仍有配件信息存在!!')</script>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally {
				out.close();
			}
			System.out.println("删除失败,该配件仍有配件信息存在!!");
			return "redirect:/pages/partssys/parts/pnumber/0";
		}
		return "redirect:jump";
	}
	
	//检索功能
	@RequestMapping("/pnumber/selectbypartsid")
	public String selectByPrimaryKey(Integer partsid,Map<String,Object> map) {
		List<Parts> list=new ArrayList<Parts>();
		Parts parts=partsservice.selectByPrimaryKey(partsid);
		list.add(parts);
		PageInfo<Parts> pageinfo=new PageInfo<Parts>(list);
		map.put("pageinfo",pageinfo);
		return "/pages/partssys/parts/partslist";
	}
	
	//新增配件
	@RequestMapping("/pnumber/add")
	public String insert(String partsname,String partsmodel,String partsloc,String partsprodate,String partsremark) {
		Parts parts=new Parts();
		parts.setPartsname(partsname);
		parts.setPartsloc(partsloc);
		parts.setPartsmodel(partsmodel);
		parts.setPartsremark(partsremark);
		parts.setPartsprodate(Date.valueOf(partsprodate));	//BUG:只能够填写符合 String转换Date规则 的日期
		if(parts.getPartsname()!=null) {
			System.out.println("新增成功!;"+parts.getPartsname());
			partsservice.insert(parts);
		}
		return "redirect:/pages/partssys/parts/pnumber/0";	
	}
	
	//修改配件的回显
	@RequestMapping("/pnumber/updateparts")
	public String showupdate(Integer partsid,Map<String,Object> map) {
		Parts parts=partsservice.selectByPrimaryKey(partsid);
		map.put("parts", parts);
		pid=partsid;
		return "/pages/partssys/parts/partsedit";
	}
	
	//修改配件
	@RequestMapping("/pnumber/update")
	public String updateByPrimaryKeySelective(String partsname,String partsmodel,String partsloc,String partsprodate,String partsremark) {
			Parts parts=new Parts();
			parts.setPartsid(pid);
			parts.setPartsname(partsname);
			parts.setPartsloc(partsloc);
			parts.setPartsmodel(partsmodel);
			parts.setPartsremark(partsremark);
			parts.setPartsprodate(Date.valueOf(partsprodate));
			partsservice.updateByPrimaryKeySelective(parts);
			return "redirect:/pages/partssys/parts/pnumber/0";
	}
}
