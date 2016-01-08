<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页左下面的框架页面</title>
	<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />
	<script type="text/javascript">
	function check()
    {  
      	var check = document.getElementById("submit"); 
      	check.style.display="block";    
  
    }	
</script>
</head>
<body>			
	<div id="sidebar">
		<div class="box">					
					<!-- Box Head -->
					<div class="box-head">
						<h2>Insert Management</h2>
					</div>
					<!-- End Box Head-->
					
					<div class="box-content">				
						<s:form action="addUserByExcel" method="post" enctype="multipart/form-data" theme="simple">					
							<div id ="input"><a href="javascript:;" class="fileone" onclick="check()"><span>导入会员</span>
   								 <input type="file" name="excelFile" id="excelFile">
							</a></div>						
							<div id="submit" style="display: none"><input type="submit" class="file"  value="确定" /></div>		
						</s:form>   <!--  onclick="check()" type="button" -->						
						<div class="cl">&nbsp;</div>
						<div class="sort"></div>
						<a href="user/userAdd.jsp" class="add-button"  target="indexLeft"><span>添加会员</span></a>
						<div class="cl">&nbsp;</div>
						<div class="sort"></div>
						<a href="addActivityReady.action" class="add-button" target="indexLeft"><span>添加活动&礼品</span></a>
						<div class="cl">&nbsp;</div>
						<div class="sort"></div>
						<a href="addGiftReadyTwo.action" class="add-button" target="indexLeft"><span>添加补发礼品</span></a>
						<div class="cl">&nbsp;</div>
						<br/>
											
					</div>
				</div>
				<!-- End Box -->
			</div>
			<!-- End Sidebar -->

</body>
</html>