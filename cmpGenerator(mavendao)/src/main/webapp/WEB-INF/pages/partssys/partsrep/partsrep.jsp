<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />

</head>
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$(function(){
		
		//页面初始化后先触发一次事件
		$("#select_partsname").click();
		
		$("#select_partsname").click(function(){
			//每次触发事件前先清空
			$("#select_partsname").html("")
			$.post("partsrepajax",{},function(data){
				$.each(data,function(index,value){
					//alert("index:"+index+";"+"value:"+value.partsname)
					$("#select_partsname").append("<option value='"+value.partsname+"'>"+value.partsname+"</option>");
				})
			})	
		});
		
		
		$("#select_billflag").change(function(){
			//每次触发事件前先清空
			$("#select_billtype").html("")
			//清空后增加'请选择'option
			$("#select_billtype").append("<option value=''>请选择</option>")
			$.post("billajax",{billflag:$("#select_billflag").val()},function(data){
				$.each(data,function(index,value){
					$("#select_billtype").append("<option value='"+value+"'>"+value+"</option>")
				})
			})
		});
		
	});
	
</script>

<body class="content-pages-body">
<div class="content-pages-wrap">
    <div class="commonTitle">
        <h2>&gt;&gt; 配件出入库</h2>
  </div>
        <form id="partsbill" name="coursesCreat" action="partsbillsave" method="post">
		  <table border="0" cellspacing="1" cellpadding="0" height="70" class="commonTable">
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>类型：</td>
				<td width="15%" align="left">
					<select id="select_billflag" name="billflag" style="width:150px;">
						<option value="">请选择</option>
						<option value="O">出</option>
						<option value="I">入</option>
					</select>
				</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>出/入库类型：</td>
				<td width="15%" align="left">
					<select id="select_billtype" name="billtype" style="width:150px;">
						<option value="">请选择类型</option>
					</select>
				</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>出/入库日期：</td>
				<td width="15%" align="left">
					${time}
				</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>操作员：</td>
				<td width="15%" align="left">${sessionScope.user.loginname }</td>
			  </tr>
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>配件：</td>
				<td width="15%" align="left">
					<select id="select_partsname" name="partsname" style="width:150px;">
						<option value="">请选择</option>
					</select>
				</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>出/入库数量：</td>
				<td width="15%" align="left">
					<input type="text" name="billcount" style="width:150px;height:20px">
				</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>说明：</td>
				<td width="15%" align="left" colspan="3"><input type="text" name="remarks" style="width:470px;height:20px"></td>
			  </tr>
		 </table>
		
	 </div>
    <!--//commonTable-->
    <div id="formPageButton">
    	<ul>
			<li><a href="#" title="保存" onclick='$("#partsbill").submit()' class="btnShort">保存</a></li>
        	<li><a href="javascript:window.history.go(-1)" title="返回" class="btnShort">返回</a></li>
        </ul>
    </div>
    </form>
    <!--//commonToolBar-->

<!--//content pages wrap-->
</body>
</html>
