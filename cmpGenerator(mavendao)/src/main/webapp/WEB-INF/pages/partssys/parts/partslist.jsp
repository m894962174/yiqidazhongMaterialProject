<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		
		/* $("#btn_add").click(function(){
			alert(1);
			window.open("addparts");
		});
		 */
	});
</script>
</head>

<body class="content-pages-body">
<div class="content-pages-wrap">
	<div class="commonTitle">
	  <h2>&gt;&gt; 配件管理</h2>
	</div>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="commonTableSearch">
       	<form  action="selectbypartsid" method="post">
        <tr>
            <th align="right">配件编号：</th>
            <td><input name="partsid" type="text" class="inputTextNormal" id="textfield2" /></td>

            <th align="right">
				<input type="submit" name="sub1" class="btnShort" value="检索" />
			</th>
        </tr>
       	</form>
    </table>


    <!--//commonTableSearch-->
    
	 <a href="partsadd">新增配件</a><br/>

	<br>

    <table width="101%" border="0" cellpadding="0" cellspacing="1" class="commonTable">
        <tr>
            <th>序号</th>
            <th>配件编码</th>
            <th>配件名称</th>
            <th>生产厂家</th>
            <th>生产日期</th>
            <th>备注</th>
            <th class="editColDefault">操作</th>
        </tr>
        <c:forEach items="${pageinfo.list}" var="p" varStatus="par">
        	<tr>
            <td align="center">${ par.count}</td>
            <td align="center">${p.partsid }</td>
            <td align="center">${p.partsname }</td>
            <td align="center">${p.partsloc }</td>
            <td align="center">${p.partsprodate }</td>
			<td align="center">${p.partsremark}</td>
            <td align="center">
            	
            	<a href="updateparts?partsid=${p.partsid }" class="btnIconEdit" title="更新"></a>
                <a href="deleteparts?partsid=${p.partsid }" class="btnIconDel" title="删除"></a>
            </td>
        </tr>
        </c:forEach>
        
       
        
  </table>
    <!--//commonTable-->
    <div id="pagelist">
    	<ul class="clearfix">
        	<li><a href="${pageinfo.firstPage }">首页</a></li>
            <li ><a href="${pageinfo.prePage }">上页</a></li>
            <li><a href="${pageinfo.nextPage }">下页</a></li>
            <li class="current">
            <form action="jump" method="post">
            <input type="text" value="1" name="jumpPage" style="text-align:right" size="1"></li>
            <li><input type="submit" value="跳转"></li>
            </form>
            <li><a href="${pageinfo.lastPage }">尾页</a></li>
            <li class="pageinfo">第${pageinfo.pageNum}页</li>
            <li class="pageinfo">共${pageinfo.pages}页</li>
        </ul>
    </div>
</div>
<!--//content pages wrap-->
</body>
</html>