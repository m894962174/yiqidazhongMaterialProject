<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<link href="${pageContext.request.contextPath }/css/main.css" rel="stylesheet" type="text/css" media="all" />

</head>
<script type='text/javascript'>

	$(function(){

		
	});
	
</script>
<body class="content-pages-body">
<div class="content-pages-wrap">
    <div class="commonTitle">
        <h2>&gt;&gt; 新增配件</h2>
  </div>
        <form id="coursesCreat" name="coursesCreat" action="add" method="post">     
		  <table border="0" cellspacing="1" cellpadding="0" class="commonTable">
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>配件名称：</td>
				<td width="15%" align="left"><input type="text" name="partsname" style="height:20px"></td>
				<td width="10%" align="right" class="title"><span class="required">*</span>规格型号：</td>
				<td width="15%" align="left"><input type="text" name="partsmodel" style="height:20px"></td>
			  </tr>
			  <tr>
				<td width="10%" align="right" class="title"><span class="required">*</span>生产厂家：</td>
				<td width="15%" align="left"><input type="text" name="partsloc" style="height:20px"></td>
				<td width="10%" align="right" class="title"><span class="required">*</span>生产日期：</td>
				<td width="15%" align="left"><input type="text" name="partsprodate" style="height:20px"></td>
			  </tr>			  
			  <tr>	
				<td width="10%" align="right" class="title"><span class="required">*</span>备注：</td>
				<td width="15%" align="left" colspan="3"><textarea name="partsremark" style="width:720px;height:100px"></textarea></td>
			  </tr>
		 </table>
		 
		 <div id="formPageButton">
    	<ul>
			<li><input type="submit" id="btn_save" value="保存"/> </li>
        	<li><a href="javascript:window.history.go(-1)" title="返回" class="btnShort">返回</a></li>
        </ul>
    </div>
		 
		</form>
	 </div>
    

</body>
</html>
