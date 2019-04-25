<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>物资采购与产品整合管理系统</title>
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />
<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script type="text/javascript">

	 $(function(){
		
		$("#jump").click(function(){
			var a=$("#jpage").val()
			location.href='${pageContext.request.contextPath }/pages/partssys/partsrep/repertorylist/'+a
			//window.open('http://localhost:8080/cmpGenerator/pages/partssys/partsrep/repertorylist/0')
		});
		
		$("#btn_partsrep").click(function(){
			//${pageContext.request.contextPath }/pages/partssys/partsrep/partsrep.jsp'
			location.href='addpartsrep'
			
		});
		
	});
 
</script>
</head>

<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 配件管理</h2>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
       	<form id="form-search" name="form-search" action="ambiguous" method="post">
        <tr>
            <th align="right">配件编码：</th>
            <td><input name="partsid" type="text" class="inputTextNormal" id="textfield2" /></td>
             <th align="right">配件名称：</th>
            <td><input name="partsname" type="text" class="inputTextNormal" id="textfield2" /></td>
            <th align="right">
				<input type="submit" class="btnShort" value="检索" />
			</th>
        </tr>
       	</form>
    </table>


    <!--//commonTableSearch-->
    
	<input type="button" class="btnNormal" value="配件出入库" id="btn_partsrep" />	

    <table width="101%" border="0" cellpadding="0" cellspacing="1" class="commonTable">
        <tr>
            <th>序号</th>
            <th>配件编码</th>
            <th>配件名称</th>
            <th>库存数量</th>
        </tr>
        <c:forEach items="${pageinfo.list }" var="l" varStatus="li">
        	<tr>
            	<td align="center">${li.count }</td>
            	<td align="center">${l.partsid }</td>
            	<td align="center">${l.partsname }</td>
				<td align="center">${l.partsreqcount }</td>
        	</tr>
        </c:forEach>
        
        
        
        
  </table>
    <!--//commonTable-->
    <div id="pagelist">
    	<ul class="clearfix">
        	<li><a href="${pageinfo.firstPage}">首页</a></li>
            <li ><a href="${pageinfo.prePage }">上页</a></li>
            <li><a href="${pageinfo.nextPage }">下页</a></li>
            <li class="current">
            
            <input type="text" value="1" id="jpage" style="text-align:right" size="1">
            <input type="button" id="jump" value="跳转">
          
            <li><a href="${pageinfo.lastPage }">尾页</a></li>
            <li class="pageinfo">第${pageinfo.pageNum }页</li>
            <li class="pageinfo">共${pageinfo.pages }页</li>
        </ul>
    </div>
</div>
<!--//content pages wrap-->
</body>
</html>