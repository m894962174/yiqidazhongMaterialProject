<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="../../../css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="../../../js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script>

$(function(){
	
		$("#btn_select").click(function(){
			$("#form-search").submit()
		});
	
		$(".a_pages").click(function(){
			//alert($(this).attr("value"))
			//获得页号并给pageNum重新赋值
			$("#pageNum").val($(this).attr("value"));
			$("#form_page").submit();
		});
		
		$("#jump").click(function(){
			var o=$("#ojump").val()
			$("#pageNum").val(o)
			$("#form_page").submit();
			//location.href="${pageContext.request.contextPath }/pages/ordersys/order/orderlist/"
		});
	
	
})

</script>
</head>

<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 订单管理</h2>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
       	<form id="form-search" name="form-search" action="orderchecklist" method="post">
        <tr>
            <th align="right">订单编码：</th>
            <td><input name="ordercode" type="text" class="inputTextNormal" id="textfield1" /></td>
            <th align="right">订单保存时间：</th>
            <td><input name="orderdate" type="text" class="inputTextNormal" id="textfield2" /></td>
            <td align="right">订单状态：</td>
            <td>
            		<select name="orderflag" id="orderflag">
						<option value="" selected>请选择</option>
						<option value="1">待审核</option>
						<option value="2">审核通过</option>
						<option value="3">审核不通过</option>
					</select></td>

            <th align="right">
				<input type="button" id="btn_select" class="btnShort" value="检索" />
			</th>
        </tr>
        <tr>
        </tr>
       	</form>
    </table>


 
	<br>

    <table width="101%" border="0" cellpadding="0" cellspacing="1" class="commonTable">
        <tr>
            <th>序号</th>
            <th>订单编码</th>
            <th>订单保存时间</th>
            <th>订单状态</th>
            <th class="editColDefault">操作</th>
        </tr>
        <c:forEach items="${pageinfo.list }" var="l" varStatus="li">
				<tr>
            		<td align="center">${li.count }</td>
           			<td align="center">${l.ordercode }</td>
            		<td align="center">${l.orderdate }</td>
					<td align="center"><font color="RED">${l.orderflagname}</font></td>
            		<td align="center">
            	<a href="changeorderflag?ordercode=${l.ordercode }&orderflag='2'" ><font color="BLUE">通过</font></a>
            	<a href="changeorderflag?ordercode=${l.ordercode }&orderflag='3'" ><font color="BLUE">不通过</font></a>
            </td>

        		</tr>
			</c:forEach>
  </table>
    <!--//commonTable-->
     <div id="pagelist">
    	<ul class="clearfix">
        	<li><a href="javascript:void(0)" class="a_pages" value="1" >首页</a></li>
            <li ><a href="javascript:void(0)" class="a_pages" value="${pageinfo.prePage }">上页</a></li>
            <li><a href="javascript:void(0)" class="a_pages" value="${pageinfo.nextPage }">下页</a></li>
            <li class="current"><input type="text" id="ojump" value="1" style="text-align:right" size="1"></li>
            <li><a id="jump">跳转</a></li>
            <li><a href="javascript:void(0)" class="a_pages" value="${pageinfo.lastPage}">尾页</a></li>
            <li class="pageinfo">第${pageinfo.pageNum }页</li>
            <li class="pageinfo">共${pageinfo.pages }页</li>
        </ul>
    </div>
</div>
<form action="orderchecklist" method="post" id="form_page">
	<input type="hidden" name="PageNum" id="pageNum" value="1">
	<c:forEach items="${condition}" var="c">
		<input type="hidden" name="${c.key}" value="${c.value }">
	</c:forEach>
</form>
<!--//content pages wrap-->
</body>
</html>
