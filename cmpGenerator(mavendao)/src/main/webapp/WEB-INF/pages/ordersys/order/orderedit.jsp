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
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript">
</script>
<script type="text/javascript" language="javascript">

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
	
	//提交按钮
	$("#submit").click(function(){
		
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
		//动态改变form表单的action参数
		$("#coursesCreat").attr("action","updateOrder?orderflag='1'")
		$("#coursesCreat").submit();
		//获取can的session
		var c = '<%=session.getAttribute("can")%>'
		if(!can){
			alert("配件订单数量不能超出库存数量!")
		}
	});
	
})

 //删除某行配件:
 function RemoveTr(_this) {
        var $trNode = $(_this).parent().parent();
        var textContext = $trNode.find("td:first").text().trim();
        var flag = confirm("确定删除\"" + textContext + "\"吗？");
        if (flag) { 
            $trNode.remove();
        } 
    }


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
        <form id="coursesCreat" name="coursesCreat" action="updateOrder?orderflag='0'" method="post">
		  <table border="0" cellspacing="1" cellpadding="0" class="commonTable">
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单序号：</td>
				<td width="15%" align="left">${id }</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单编码：</td>
				<td width="15%" align="left"><input type="text" hidden name="ordercode" value="${list[0].ordercode }" />${list[0].ordercode }</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单保存日期：</td>
				<td width="15%" align="left"><input type="text" hidden name="orderdate" value="${list[0].orderdate }" />${list[0].orderdate }</td>
				<td width="10%" align="right" class="title"><span class="required">*</span>订单状态：</td>
				<td width="15%" align="left">
					<select id="orderstatus">
						<option value="0" selected>未提交</option>
					</select>
				</td>
			  </tr>

		 </table>
	    <div align="left" style="margin-top:15px;margin-bottom:5px">
	    	<a href="selectpartsrepertory" title="选择原料" class="btnShort">选择配件</a>
	    </div>
		<table width="90%" border="0" cellpadding="0" cellspacing="1" id="attachmentList" class="commonTable marginTopM">
		  <tr>
	          <th>配件名称</th>
	          <th>进货数量</th>
			  <th>库存数量</th>
	          <th class="editColXS">操作</th>
	      </tr>
	      <c:forEach items="${list}" var="l" varStatus="li">
	      	<tr>
	          <td><input type="text" hidden name="partsid" value="${l.partsid}">${l.partsname}</td>
	          <td><input type="text" name="orderpartscount" value="${l.orderpartscount }"></td>
			  <td><input type="text" hidden name="partsrepcount" value="${l.partsrepcount }">${l.partsrepcount }</td>
	          <td class="editColXS"><a class="del" href="javescript:void(0)" onclick="RemoveTr(this)">删除</a></td>
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

