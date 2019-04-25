<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<html>
<head>
<meta charset="gbk" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
//选为待审核保存 等同于提交功能
	$(function(){
		
		$("#save").click(function(){
			var bl=true;
			//订单编码不能为空
			if($("#ajax_ordercode").val()==""){
				bl=false;
				alert("订单编码不能为空!")
			}
			//订单明细中的进货数量不能为空,进货数量不能超出后面的库存数量
			$(".c_orderpartscount").each(function(){
				if($(this).val()==""){
					alert("进货数量不能为空!")
					bl=false;
					return bl
				}
			});
			//当!bl为true时,退出整个方法
			if(!bl){
				return false
			}
			$("#coursesCreat").submit();
			//获取can的session
			var c = '<%=session.getAttribute("can")%>'
			if(!can){
				alert("配件订单数量不能超出库存数量!")
			}
		})
		
		
		<%-- $("#submit").click(function(){
			//点击提交时动态改变<form>中 
			$("#saveorderdetail")attr("action","submitSave");
			var bl=true;
			//订单编码不能为空
			if($("#ajax_ordercode").val()==""){
				bl=false;
				alert("订单编码不能为空!")
			}
			//订单明细中的进货数量不能为空,进货数量不能超出后面的库存数量
			$(".c_orderpartscount").each(function(){
				if($(this).val()==""){
					alert("进货数量不能为空!")
					bl=false;
					return bl
				}
			});
			//当!bl为true时,退出整个方法
			if(!bl){
				return false
			}
			$("#coursesCreat").submit();
			//获取can的session
			var c = '<%=session.getAttribute("can")%>'
			if(!can){
				alert("配件订单数量不能超出库存数量!")
			}
		}) --%>
		
		
	})
		
		
	$(function(){
		//ajax实时校验ordercode
		$("#ajax_ordercode").blur(function(){
			$.post("ajaxordercode",{ordercode:$("#ajax_ordercode").val()},function(data){
				if(!data){
					$("#span_ordercode").html("该编码已存在！")
				}else{
					$("#span_ordercode").html("")
				}
			})
		});
	})	
		
	


//定时器:每隔1000秒更新一下当前时间
setInterval(function(){
	$("#td_time").html(new Date().toString())
},1000);

function showMaterials(){
	var date = new Date();
	var time = date.getTime();
	var obj = new Array('INSERT');
	//时间戳
	var url = "getmater.html";
	var resultValue = window.showModalDialog(url,obj,'dialogWidth:800px;dialogHeight:400px');

	//获取已经存在明细中的原料ID集合
	var materIds = document.getElementsByName('materId');
	if(resultValue!=null && resultValue!=undefined){
		for(var i=0;i<resultValue.length;i++){
			var tempAry = resultValue[i].split(',');
			var flag = false;
			//判断列表中是否已经选择了某种原料
			for(var j=0;j<materIds.length;j++){
				if(tempAry[0]==materIds[j].value){
					flag = true;
				}
			}
			if(!flag){
				insertMaterialMsg(tempAry);
			}
			
		}
		
	}
}


function insertMaterialMsg(tempAry){
	//原料名ID
	var id = tempAry[0];
	//原料名称
	var materName = tempAry[1];
	//原料库存
	var storage = tempAry[2];
	
	var trObj = attachmentList.insertRow();
	trObj.setAttribute("align","center");

	var tdObj = trObj.insertCell();
	tdObj.setAttribute("align","left");
	tdObj.innerHTML = "<input type='hidden' name='materId' value='"+id+"'>"+materName;
	
	var tdObj = trObj.insertCell();
	tdObj.innerHTML = "<input type='text' name='count' value='' class='inputTextNormal'>";
	
	var tdObj = trObj.insertCell();
	tdObj.setAttribute("align","left");
	tdObj.innerHTML = storage;
	
	var tdObj = trObj.insertCell();
	tdObj.setAttribute("align","left");
	tdObj.innerHTML = "<button onclick=\"deleteRow('attachmentList',this);\" class=\"btnIconDel\" title=\"删除\"></button>";
}


//删除行
function deleteRow(tableID,t){
	var tIndex = t.parentNode.parentNode.rowIndex;
		if(confirm('确定要执行此操作吗?')) {
			 t.parentNode.parentNode.parentNode.deleteRow(tIndex);
		}
		return false; 		

}

</script>
</head>

<body class="content-pages-body">
<div class="content-pages-wrap">
    <div class="commonTitle">
        <h2>&gt;&gt; 订单信息修改</h2>
  </div>
        <form id="coursesCreat" name="coursesCreat" action="saveorderdetail" method="post">
		  <table border="0" cellspacing="1" cellpadding="0" class="commonTable">
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单编码：</td>
				<td width="15%" align="left"><input type="text" id="ajax_ordercode" name="ordercode" style="width:150px"><span id="span_ordercode"></span></td>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单保存日期：</td>
				<td width="15%" align="left" ><input type="date" id="td_time" name="orderdate" /></td>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单状态：</td>
				<td width="15%" align="left">
				
					<select id="orderstatus" name="orderflag">
						<option value="0" selected>未提交</option>
						<option value="1">待审核</option>
					</select>
				</td>
			  </tr>

		 </table>
	    <div align="left" style="margin-top:15px;margin-bottom:5px">
	    	<a href="selectpartsrepertory" title="选择原料" class="btnShort">选择配件</a>
	    </div>
		<table width="90%" border="0" cellpadding="0" cellspacing="1" id="attachmentList" class="commonTable marginTopM">
		  <tr>
		  	  <th>序号</th>
	          <th>配件名称</th>
	          <th>进货数量</th>
			  <th>库存数量</th>
	          <th class="editColXS">操作</th>
	      </tr>
	      <c:forEach items="${pageinfo.list }" var="l" varStatus="li">
	      	<tr>
	      	  <td>${li.count}</td>
	          <td><input value="${l.partsid }" name="partsid" hidden/>${l.partsname }</td>
	          <td>
	          	<input type="text" name="orderpartscount" id="orderpartscount" class="c_orderpartscount"/>
	          </td>
			  <td class="t_reqcount"><input name="partsreqcount" hidden value="${l.partsreqcount }" />${l.partsreqcount }</td>
	          <td class="editColXS">操作</td>
	      </tr>
	      </c:forEach>
		</table>
		
		</form>
	 </div>
    <!--//commonTable-->
    <div id="formPageButton">
    	<ul>
			<li><a href="javascript:void(0)" id="save" title="保存" class="btnShort">保存</a></li>
			<li><a href="javascript:void(0)" id="submit" title="提交" class="btnShort">提交</a></li>
        	<li><a href="javascript:window.history.go(-1)" title="返回" class="btnShort">返回</a></li>
        </ul>
    </div>
    <!--//commonToolBar-->
</div>
<!--//content pages wrap-->
</body>
</html>
