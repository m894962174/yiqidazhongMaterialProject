<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript"></script>
</head>
<script>

	$(function(){
		
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
		
		/* $("#selectambiguous").click(function(){
			
			$()
			
		}) */
		
	});
	
</script>
<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 订单管理</h2>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
       	<form id="form-search" name="form-search" action="orderlist" method="post">
        <tr>
            <th align="right">订单编码：</th>
            <td><input name="ordercode" type="text" class="inputTextNormal" id="textfield1" /></td>
            <th align="right">订单保存时间：</th>
            <td><input name="orderdates" type="text" class="inputTextNormal" id="textfield2" /></td>
            <td align="right">订单状态：</td>
            <td><select name="orderflag" type="text" class="inputTextNormal" id="datepiker3" >
            		<option value="">请选择</option>
            		<option value="0">未提交</option>
            		<option value="1">待审核</option>
            		<option value="2">审核通过</option>
            		<option value="3">审核未通过</option>
            	</select>
            </td>

            <th align="right">
				<input type="submit" id="selectambiguous" class="btnShort" value="检索" />
			</th>
        </tr>
        <tr>

          </tr>
       	</form>
    </table>


    <!--//commonTableSearch-->
    
	<input type="button" class="btnNormal" value="创建订单" onclick="location.href='orderadd'"/>	

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
	<td align="center">${l.orderflagname }<font color="RED">
	<!-- 未提交(0)、待审核(1)、审核通过(2)、审核未通过(3) -->
	</font></td>
    <td align="center">
    	<a href="ordershow?orderid=${l.orderid }&id=${li.count}" class="btnIconView" title="查看详情"></a>
    	<c:if test="${l.orderflag=='0'}">	<!-- 进不去if? -->
    		<a href="ordercheck?orderid=${l.orderid }&id=${li.count}" class="btnIconEdit" title="更新"></a>
    		<a href="deleteorder?orderid=${l.orderid }"  class="btnIconDel" title="删除"></a>
    	</c:if>
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
<form action="orderlist" method="post" id="form_page">
	<input type="hidden" name="pageNum" id="pageNum" value="1">
	<c:forEach items="${condition}" var="c">
		<input type="hidden" name="${c.key}" value="${c.value }">
	</c:forEach>
</form>
<!--//content pages wrap-->
</body>
</html>
