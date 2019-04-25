<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="gbk" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="../../../css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="../../../js/jquery-1.4.2.min.js" type="text/javascript"></script>
</head>
<script type="text/javascript">

	$(function(){
		
		//
		$("#a_sure").click(function(){
			$("#form_partsdetail").submit();
		});
		
		//跳转赋值
		$("#jump").click(function(){
			$("#pageNum").val($("#page_jump").attr("value"))
			$("#form_page").submit();
		})
		
		//将页数赋值给表单域pageNum
		$(".a_page").click(function(){
			$("#pageNum").val($(this).attr("value"))
			$("#form_page").submit();
		});
		
	})

var obj = window.dialogArguments;

function loadForm()
{
	document.forms[1].CZType.value = obj[0];
}


function checkAll(){
	var form = document.forms[1];
	var checkObj = form.ids;
	var ids = form.id;
	for(var i=0;i<ids.length;i++){
		ids[i].checked = checkObj.checked;
	}
}

function catchValues(){
	var form = document.forms[1];
	var ids = form.id;
	var flag = false;
	var ary = [];
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			flag = true;
			ary.push(ids[i].value);
		}
	}

	if(!flag){
		alert("请选择原料！");
		return ;
	}
	window.returnValue = ary;
	window.close();
}

</script>
<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 订单管理&nbsp;&gt;&gt;&nbsp;配件选择列表</h2>
	</div>
	<form id="form-search" name="form-search" action="selectpartsrepertory" method="post">
	    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
	        <tr>
	            <th align="right">配件名称：</th>
	            <td ><input name="partsname" id="ipt_partsname" type="text" class="inputTextNormal"  /></td>
				<td width="150" align="" >
					<input type="submit" id="btn_select" value="检索"/>
				</td>
				<td></td>
	        </tr>
    	</table>
    </form>
    <!--//commonTableSearch-->
    <div class="btnBar">
    	<ul class="clearfix">
        	<li><a href="javascript:catchValues();" title="确定" id="a_sure" class="btnLong">确定</a></li>
        	<li><a href="orderadd" title="关闭" class="btnLong">关闭</a></li>
        </ul>
    </div>
    <form action="partsdetail" id="form_partsdetail" method="post">
	    <table width="101%" border="0" cellpadding="0" cellspacing="1" class="commonTable">
	        <tr>
	            <th>
	            	<input type="checkbox"   name="ids" onclick="checkAll();">
	            </th>
	            <th>配件名称</th>
	            <th>配件库存</th>
	        </tr>
		    <c:forEach items="${pageinfo.list}" var="l" varStatus="li">
		    	<tr>
		            <td align="center" style="width:5%">
		            	<input type="checkbox" checked  class="ipt_parts" name="id" value="${l.partsid }">
		            </td>
		            <td align="center">${l.partsname }</td>
		            <td align="center">${l.partsreqcount }</td>
		        </tr>
		    </c:forEach>
	  </table>
  </form>
    <!--//commonTable-->
    <div id="pagelist">
    	<ul class="clearfix">
        	<li><a href="javaScript:void(0)" class="a_page" value="1">首页</a></li>
            <li ><a href="javaScript:void(0)" class="a_page" value="${pageinfo.prePage }">上页</a></li>
            <li><a href="javaScript:void(0)" class="a_page" value="${pageinfo.nextPage }">下页</a></li>
            <li class="current"><input type="text" id="page_jump" value="1" style="text-align:right" size="1"></li>
            <li><a href="javaScript:void(0)" class="a_page" id="jump">跳转</a></li>
            <li><a href="javaScript:void(0)" class="a_page" value="${pageinfo.lastPage }">尾页</a></li>
            <li class="pageinfo">第${pageinfo.pageNum }页</li>
            <li class="pageinfo">共${pageinfo.pages }页</li>
        </ul>
    </div>
    <!-- 隐藏表单域 -->
    <form id="form_page" method="post" action="selectpartsrepertory">
    	<input type="hidden" name="pageNum" id="pageNum" >
    	<c:forEach items="${condition }" var="c">
    		<input type="hidden" id="${c.key }" name="${c.key }" value="${c.value }" />
   		</c:forEach>
    </form>
</div>
<!--//content pages wrap-->
</body>
</html>
