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
	
	//����ҳ��������ѯ
	@DateTimeFormat()
	@RequestMapping("/orderlist")
	public String orderList(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,@RequestParam(name="ordercode",defaultValue="")String ordercode,@RequestParam(name="orderdates",defaultValue="")String orderdates,@RequestParam(name="orderflag",defaultValue="")String orderflag,Map<String,Object> map) {
		//����ѯ������������õ���condition��
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
	
	
	//ɾ��
	@RequestMapping("/deleteorder")
	public String deleteOrder(Integer orderid) {
		orderservice.deleteByPrimaryKey(orderid);
		return "redirect:/pages/ordersys/order/orderlist";
	}
	
//	@RequestMapping("/orderlist/selectambiguous")
//	public String selectambiguous(String ordercode,Date orderdates,String orderflag,Map<String,Object> map) {
//		PageHelper.startPage(1,5);
//		List<Order> list=orderservice.selectambiguous(ordercode,orderdates, orderflag);	//date����д��
//		PageInfo<Order> pageinfo=new PageInfo<Order>(list);
//		map.put("pageinfo", pageinfo);
//		return "/pages/ordersys/order/orderlist";
//	}
	
	//DispatcherServlet���ܵ�String����ת��ΪDate����
//	@InitBinder
//	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
//	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    CustomDateEditor editor = new CustomDateEditor(df, true);//true��ʾ����Ϊ�գ�false��֮
//	    binder.registerCustomEditor(Date.class, editor);
//	}
	
	//ajaxʵʱУ��:��������
	@RequestMapping("/ajaxordercode")
	@ResponseBody
	public Boolean ajaxordercode(@RequestParam(name="ordercode",defaultValue="")String ordercode) {
		return orderservice.selectByOrderCode(ordercode);
	}
	
	//ѡ��������������ķ�ҳ��ѯ
	@RequestMapping("/selectpartsrepertory")
	public String selectpartsrepertory(@RequestParam(name="pageNum",defaultValue="1")Integer pageNum,@RequestParam(name="partsname",defaultValue="")String partsname,@RequestParam(name="partsid",defaultValue="")Integer partsid,Map<String,Object> map) {
		//����������Ҫ����Ϣ����condition
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("partsname", partsname);
		condition.put("pageNum", pageNum);
		//condition����map��
		map.put("condition", condition);
		
		PageHelper.startPage(pageNum, 5);
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(partsrepertoryservice.ambiguousSelect(partsid, partsname));
		map.put("pageinfo", pageinfo);
		
		return "/pages/ordersys/order/getmater";
	}
	
	
	//����ѡ�е����
	@RequestMapping("/partsdetail")
	public String savepartsrepertory(Integer[] id,Map<String,Object> map) {
		//��������������ҳ�Ĵ���							//����BUG����λ�÷�ҳ��checked��id?; ��ҳ����α�����checked���䣿
		PageInfo<PartsRepertory> pageinfo=new PageInfo<PartsRepertory>(partsrepertoryservice.selectByPartsId(id));
		map.put("pageinfo", pageinfo);
		return "/pages/ordersys/order/orderadd";
	}
	
	//��ת��������ҳ��
	@RequestMapping("/orderadd")
	public String goorderadd() {
		return "/pages/ordersys/order/orderadd";
	}
	
	
	//ת������ҳ��(�޸�)
	@RequestMapping("/ordercheck")
	public String orderCheck(Integer id,Integer orderid,Map<String,Object> map) {
		List<Order> list=orderservice.mySelectByPrimarykey(orderid);
		//��partsrepcount����Ϊnull��BUG�������ѯ������������set Order��
		for(Order o:list) {
			o.setPartsrepcount(orderservice.selectCountByPrimaryKey(o.getPartsid()));
		}
		map.put("list",list);
		//������Ϊ�˴�һ�����id
		map.put("id", id);
		return "/pages/ordersys/order/orderedit";
	}
	
	//ת����������ҳ��
	@RequestMapping("/ordershow")
	public String orderEdit(Integer id,Integer orderid,Map<String,Object> map) {
		List<Order> list=orderservice.mySelectByPrimarykey(orderid);
		//��partsrepcount����Ϊnull��BUG�������ѯ������������set Order��
		for(Order o:list) {
			o.setPartsrepcount(orderservice.selectCountByPrimaryKey(o.getPartsid()));
		}
		map.put("list",list);
		//������Ϊ�˴�һ�����id
		map.put("id", id);
		return "/pages/ordersys/order/ordershow";
	}
	
	//�������湦�ܵ�У��:
	@RequestMapping("/saveorderdetail")
	public String saveorderdetail(HttpSession session,String ordercode,String orderdate,String orderflag,Integer[] partsid,Integer[] orderpartscount,Integer[] partsreqcount,Map<String,Object> map) {
		System.out.println("orderdate:"+orderdate+",orderflag:"+orderflag+",,ordercode:"+ordercode+",ocount:"+orderpartscount[0]+",repcount:"+partsreqcount[0]);
		Boolean can=true;
		//�����������ܳ�������Ŀ������
		for(int i=0;i<orderpartscount.length;i++) {
			if(orderpartscount[i]>partsreqcount[i]) {
				can=false;
			}
		}
		//��֤¼��Ķ���������������Ƿ����
		if(orderservice.selectByOrderCode(ordercode)==null) {
			can=false;
		}
		System.out.println("�ܷ�ɹ�ִ�У�:"+can);
		map.put("can",can);
		//�ж��Ƿ�ִ������
		if(can) {
			//�򶩵�����,�ӱ���������
			orderservice.saveFunction(ordercode,orderdate, orderflag, partsid, orderpartscount, partsreqcount);
			if(orderflag.equals("1")) {
				//���ö�������⹦��
				for(int i=0;i<partsid.length;i++) {
					try {
						orderservice.updateRepertoryCount(session,orderdate,"o","out1", partsid[i],orderpartscount[i],"���Զ����ĳ���");
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

	//�����޸�
	@RequestMapping("/updateOrder")
	public String updateOrder(HttpSession session,String orderflag,Integer[] partsid,String ordercode,String orderdate,Integer[] orderpartscount,Integer[] partsrepcount,Map<String,Object> map) {
		System.out.println("�ܷ������orderdate:"+orderdate+"orderflag:"+orderflag+",,ordercode:"+ordercode+",ocount:"+orderpartscount[0]+",repcount:"+partsrepcount[0]);
		Boolean can=true;
		//�����������ܳ�������Ŀ������
		for(int i=0;i<orderpartscount.length;i++) {
			if(orderpartscount[i]>partsrepcount[i]) {
				can=false;
			}
		}
		//��֤¼��Ķ���������������Ƿ����
		if(orderservice.selectByOrderCode(ordercode)==null) {
			can=false;
		}
		System.out.println("�ܷ�ɹ�ִ�У�:"+can);
		map.put("can",can);
		//�ж��Ƿ�ִ������
		if(can) {
			//�򶩵�����,�ӱ��޸�����
			orderservice.updateAll(orderflag, partsid, ordercode, orderdate, orderpartscount, partsrepcount);
			//���ö�������⹦��
			if(orderflag.equals("1")) {
				for(int i=0;i<orderpartscount.length;i++) {
					try {
						orderservice.updateRepertoryCount(session,orderdate,"o","out1", partsid[i],orderpartscount[i],"���Զ����ĳ���");
						System.out.println("����������!!");
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
	
	//�������:�������ķ�ҳ
	@RequestMapping("/orderchecklist")
	public String orderchecklist(@RequestParam(name="PageNum",defaultValue="0")Integer PageNum,@RequestParam(name="ordercode",defaultValue="")String ordercode,@RequestParam(name="orderdate",defaultValue="")String orderdate,@RequestParam(name="orderflag",defaultValue="1")String orderflag,Map<String,Object> map) {
		System.out. println("��ʼ��ҳ��ѯ");
		//����condition��Ų�ѯ����
		Map<String,Object> condition=new HashMap<String,Object>();
		condition.put("ordercode", ordercode);
		condition.put("orderdate", orderdate);
		condition.put("orderflag", orderflag);
		
		
		map.put("condition", condition);
		//��ҳ
		PageHelper.startPage(PageNum,5);
		PageInfo<Order> pageinfo=new PageInfo<Order>(orderservice.selectambiguous(ordercode, orderdate, orderflag));
		map.put("pageinfo",pageinfo);
		System.out.println("��ת�������ҳ��");
		return "/pages/ordersys/order/ordercheck";
	}
	
	@RequestMapping("/changeorderflag")
	public String changeOrderFlag(String ordercode,String orderflag) {
		//ͨ������޸�orderflag;��ͨ����Ҫ�����������
		orderservice.changeOrderFlag(ordercode, orderflag);
		return "redirect:/pages/ordersys/order/orderchecklist";
	}
	
}
