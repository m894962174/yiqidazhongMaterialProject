package com.oracle.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oracle.service.OrderService;
import com.oracle.service.PartsRepertoryService;
import com.oracle.vo.Order;
import com.oracle.vo.PartsRepertory;

@Controller
@SessionAttributes(names="can")
@RequestMapping("/pages/ordersys/order/")
public class OrderHandler {

	@Autowired
	OrderService orderservice;
	
	@Autowired
	PartsRepertoryService partsrepertoryservice;
	
	//带分页的条件查询
	@DateTimeFormat()
	@RequestMapping("/orderlist")
	public String orderList(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,@RequestParam(name="ordercode",defaultValue="")String ordercode,@RequestParam(name="orderdates",defaultValue="")String orderdates,@RequestParam(name="orderflag",defaultValue="")String orderflag,Map<String,Object> map) {
		//将查询所需的条件放置到此condition中
		Map<String,String> condition=new HashMap<String,String>();
		condition.put("ordercode", ordercode);
		condition.put("orderdates", orderdates);
		condition.put("orderflag", orderflag);
		
		map.put("condition", condition);
		
		PageHelper.startPage(pageNum,5);
		PageInfo<Order> pageinfo=new PageInfo<Order>(orderservice.selectambiguous(ordercode, orderdates, orderflag));
		
		map.put("pageinfo", pageinfo);
		return "/pages/ordersys/order/orderlist";
	}
	
	
	//删除
	@RequestMapping("/deleteorder")
	public String deleteOrder(Integer orderid) {
		orderservice.deleteByPrimaryKey(orderid);
		return "redirect:/pages/ordersys/order/orderlist";
	}
	
//	@RequestMapping("/orderlist/selectambiguous")
//	public String selectambiguous(String ordercode,Date orderdates,String orderflag,Map<String,Object> map) {
//		PageHelper.startPage(1,5);
//		List<Order> list=orderservice.selectambiguous(ordercode,orderdates, orderflag);	//date故意写错
//		PageInfo<Order> pageinfo=new PageInfo<Order>(list);
//		map.put("pageinfo", pageinfo);
//		return "/pages/ordersys/order/orderlist";
//	}
	
	//DispatcherServlet接受的String类型转换为Date类型
//	@InitBinder
//	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
//	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    CustomDateEditor editor = new CustomDateEditor(df, true);//true表示允许为空，false反之
//	    binder.registerCustomEditor(Date.class, editor);
//	}
	
	//ajax实时校验:订单编码
	@RequestMapping("/ajaxordercode")
	@ResponseBody
	public Boolean ajaxordercode(@RequestParam(name="ordercode",defaultValue="")String ordercode) {
		return orderservice.selectByOrderCode(ordercode);
	}
	
	//选择配件：带条件的分页查询
	@RequestMapping("/selectpartsrepertory")
	public String selectpartsrepertory(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,@RequestParam(name="partsname",defaultValue="")String partsname,@RequestParam(name="partsid",defaultValue="")Integer partsid,Map<String,Object> map) {
		//将表单域所需要的信息存入condition
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("partsname", partsname);
		condition.put("pageNum", pageNum);
		//condition存入map中
		map.put("condition", condition);
		
		PageHelper.startPage(pageNum, 5);
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(partsrepertoryservice.ambiguousSelect(partsid, partsname));
		map.put("pageinfo", pageinfo);
		
		return "/pages/ordersys/order/getmater";
	}
	
	
	//接收选中的配件
	@RequestMapping("/partsdetail")
	public String savepartsrepertory(Integer[] id,Map<String,Object> map) {
		//不用做带条件分页的处理							//俩处BUG：如何获得分页的checked的id?; 跳页后如何保存已checked不变？
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(partsrepertoryservice.selectByPartsId(id));
		map.put("pageinfo", pageinfo);
		return "/pages/ordersys/order/orderadd";
	}
	
	//跳转创建订单页面
	@RequestMapping("/orderadd")
	public String goorderadd() {
		return "/pages/ordersys/order/orderadd";
	}
	
	
	//转至更新页面(修改)
	@RequestMapping("/ordercheck")
	public String orderCheck(Integer id,Integer orderid,Map<String,Object> map) {
		List<Order> list=orderservice.mySelectByPrimarykey(orderid);
		//因partsrepcount返回为null的BUG；单另查询配件库存数量并set Order中
		for(Order o:list) {
			o.setPartsrepcount(orderservice.selectCountByPrimaryKey(o.getPartsid()));
		}
		map.put("list",list);
		//仅仅是为了传一下序号id
		map.put("id", id);
		return "/pages/ordersys/order/orderedit";
	}
	
	//转至订单详情页面
	@RequestMapping("/ordershow")
	public String orderEdit(Integer id,Integer orderid,Map<String,Object> map) {
		List<Order> list=orderservice.mySelectByPrimarykey(orderid);
		//因partsrepcount返回为null的BUG；单另查询配件库存数量并set Order中
		for(Order o:list) {
			o.setPartsrepcount(orderservice.selectCountByPrimaryKey(o.getPartsid()));
		}
		map.put("list",list);
		//仅仅是为了传一下序号id
		map.put("id", id);
		return "/pages/ordersys/order/ordershow";
	}
	
	//订单保存功能的校验:
	@RequestMapping("/saveorderdetail")
	public String saveorderdetail(HttpSession session,String ordercode,String orderdate,String orderflag,Integer[] partsid,Integer[] orderpartscount,Integer[] partsreqcount,Map<String,Object> map) {
		System.out.println("orderdate:"+orderdate+",orderflag:"+orderflag+",,ordercode:"+ordercode+",ocount:"+orderpartscount[0]+",repcount:"+partsreqcount[0]);
		Boolean can=true;
		//进货数量不能超出后面的库存数量
		for(int i=0;i<orderpartscount.length;i++) {
			if(orderpartscount[i]>partsreqcount[i]) {
				can=false;
			}
		}
		//验证录入的订单编号在数据中是否存在
		if(orderservice.selectByOrderCode(ordercode)==null) {
			can=false;
		}
		System.out.println("能否成功执行？:"+can);
		map.put("can",can);
		//判断是否执行事务
		if(can) {
			//向订单主表,子表新增数据
			orderservice.saveFunction(ordercode,orderdate, orderflag, partsid, orderpartscount, partsreqcount);
			if(orderflag.equals("1")) {
				//调用订单出入库功能
				for(int i=0;i<partsid.length;i++) {
					try {
						orderservice.updateRepertoryCount(session,orderdate,"o","out1", partsid[i],orderpartscount[i],"来自订单的出库");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return "redirect:/pages/ordersys/order/orderlist";
		}else {
			return "/pages/ordersys/order/orderadd";
		}
	}

	//订单修改
	@RequestMapping("/updateOrder")
	public String updateOrder(HttpSession session,String orderflag,Integer[] partsid,String ordercode,String orderdate,Integer[] orderpartscount,Integer[] partsrepcount,Map<String,Object> map) {
		System.out.println("能否进来？orderdate:"+orderdate+"orderflag:"+orderflag+",,ordercode:"+ordercode+",ocount:"+orderpartscount[0]+",repcount:"+partsrepcount[0]);
		Boolean can=true;
		//进货数量不能超出后面的库存数量
		for(int i=0;i<orderpartscount.length;i++) {
			if(orderpartscount[i]>partsrepcount[i]) {
				can=false;
			}
		}
		//验证录入的订单编号在数据中是否存在
		if(orderservice.selectByOrderCode(ordercode)==null) {
			can=false;
		}
		System.out.println("能否成功执行？:"+can);
		map.put("can",can);
		//判断是否执行事务
		if(can) {
			//向订单主表,子表修改数据
			orderservice.updateAll(orderflag, partsid, ordercode, orderdate, orderpartscount, partsrepcount);
			//调用订单出入库功能
			if(orderflag.equals("1")) {
				for(int i=0;i<orderpartscount.length;i++) {
					try {
						orderservice.updateRepertoryCount(session,orderdate,"o","out1", partsid[i],orderpartscount[i],"来自订单的出库");
						System.out.println("配件出库完成!!");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return "redirect:/pages/ordersys/order/orderlist";
		}else {
			return "/pages/ordersys/order/orderedit";
		}
	}
	
	//订单审核:带条件的分页
	@RequestMapping("/orderchecklist")
	public String orderchecklist(@RequestParam(name="PageNum",defaultValue="0")Integer PageNum,@RequestParam(name="ordercode",defaultValue="")String ordercode,@RequestParam(name="orderdate",defaultValue="")String orderdate,@RequestParam(name="orderflag",defaultValue="1")String orderflag,Map<String,Object> map) {
		System.out. println("开始分页查询");
		//创建condition存放查询条件
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("ordercode", ordercode);
		condition.put("orderdate", orderdate);
		condition.put("orderflag", orderflag);
		
		
		map.put("condition", condition);
		//分页
		PageHelper.startPage(PageNum,5);
		PageInfo<Order> pageinfo=new PageInfo<Order>(orderservice.selectambiguous(ordercode, orderdate, orderflag));
		map.put("pageinfo",pageinfo);
		System.out.println("跳转到了审核页面");
		return "/pages/ordersys/order/ordercheck";
	}
	
	@RequestMapping("/changeorderflag")
	public String changeOrderFlag(String ordercode,String orderflag) {
		//通过则仅修改orderflag;不通过需要回退配件出库
		orderservice.changeOrderFlag(ordercode, orderflag);
		return "redirect:/pages/ordersys/order/orderchecklist";
	}
	
}
