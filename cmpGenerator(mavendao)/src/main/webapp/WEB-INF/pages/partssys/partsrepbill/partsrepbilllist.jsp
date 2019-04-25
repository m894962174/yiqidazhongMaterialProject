<%@ page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script>
	$(function(){
		
		//分页功能
		$(".a_page").click(function(){
			$("#pageNum").val($(this).attr("value"))
			$("#form_page").submit()
		})
		
		$("#jump").click(function(){
			var a=$("#jumpvalue").val()
			$("#pageNum").val(a)
			$("#form_page").submit()
		});
		
		
		//开始先触发事件一次
		
		$("#billflag").change(function(){
			//事件触发前先清空
			$("#billtype").html("")
			$("#billtype").append("<option value=''>请选择</option>")
			$.post("ajaxbilltype",{billflag:$("#billflag").val()},function(data){
				$.each(data,function(i,v){
					$("#billtype").append("<option value='"+v+"'>"+v+"</option>")
				})
			})
		});
	})
</script>
</head>

<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 配件库存流水账查询</h2>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
       	<form id="form-search" name="form-search" action="partsrepbilllist" method="post">
        <tr>
            <th align="right">配件名称：</th>
            <td><input name="partsname" type="text" class="inputTextNormal" id="textfield2" /></td>
            <th align="right">出/入库：</th>
            <td>
            	<select name="billflag" id="billflag" style="width:150px;">
						<option value="">请选择</option>
						<option value="I">入库</option>
						<option value="O">出库</option>
				</select>
			</td>
            <th align="right">出入库类型：</th>
            <td>
            	<select name="billtype" id="billtype" style="width:150px;">
						<option value=''>请先选择出入库</option>
				</select>
            </td>
            <th align="right">出入库日期：</th>
            <td>
            	<input name="billtime" type="text" class="inputTextNormal" id="textfield2" />
            </td>
            <th align="right">
				<input type="submit" class="btnShort" value="检索" />
			</th>
        </tr>
       	</form>
    </table>
	<br>

    <!--//commonTableSearch-->

    <table width="101%" border="0" cellpadding="0" cellspacing="1" class="commonTable">
        <tr>
            <th>序号</th>
            <th>出/入库</th>
            <th>出入库类别</th>
            <th>配件名称</th>
            <th>数量</th>
            <th>时间</th>
            <th>操作人</th>
        </tr>
        <c:forEach items="${pageinfo.list }" var="l" varStatus="li">
        	<tr>
            <td align="center">${li.count }</td>
            <td align="center">${l.name1}</td>
            <td align="center">${l.name }</td>
			<td align="center">${l.partsname }</td>
			<td align="center">${l.billcount }</td>
			<td align="center">${l.billtime }</td>
			<td align="center">${l.loginname }</td>
        </tr>
        </c:forEach>
              
        
  </table>
    <!--//commonTable-->
    <div id="pagelist">
    	<ul class="clearfix">
        	<li><a href="javascript:void(0)" class="a_page" value="${pageinfo.firstPage }">首页</a></li>
            <li ><a href="javascript:void(0)" class="a_page" value="${pageinfo.prePage }">上页</a></li>
            <li><a href="javascript:void(0)" class="a_page" value="${pageinfo.nextPage }">下页</a></li>
            <li class="current"><input type="text" value="1" id="jumpvalue" style="text-align:right" size="1"></li>
            <li><a id="jump">跳转</a></li>
            <li><a href="javascript:void(0)" class="a_page" value="${pageinfo.lastPage }">尾页</a></li>
            <li class="pageinfo">第${pageinfo.pageNum }页</li>
            <li class="pageinfo">共${pageinfo.pages }页</li>
        </ul>
    </div>
</div>
<!-- 分页条件的隐藏域 -->
<form action="partsrepbilllist" method="post" id="form_page">
	<input type="hidden" id="pageNum" name="pageNum" />
	<c:forEach items="${condition }" var="c">
		<input type="hidden" name="${c.key}" value="${c.value }">
	</c:forEach>
</form>

<!--//content pages wrap-->
</body>
</html>