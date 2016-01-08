<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员登录的首页</title>
	<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />
</head>
<body>
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1><a href="#">FNST</a></h1>
			<div id="top-navigation" style="margin-right: 95px;">
			<c:if test="${user!=null}"><span id="user">欢迎 <strong><s:property value='%{user.userTrueName}'/></strong> 会员登录</span></c:if>
			<c:if test="${user==null}"><span id="user"><strong>请先登录，再做操作</strong></span></c:if>
			&nbsp;&nbsp;
				<a href="loginout.action"><span>退出登录</span></a>
			</div>
		</div>
		<!-- End Logo + Top Nav -->
		
		<!-- Main Nav -->
		<div id="navigation">
			<ul>
			    <li><a href="userDetailInformation.action?user.userId=${user.userId}" target="indexLeft"><span>个人信息</span></a></li>
			    <li><a href="activityUnsignList.action"  target="indexLeft"><span>①未签到活动—></span></a></li>
			    <li><a href="activitySignUncheckedList.action"  target="indexLeft"><span>②身份验证—></span></a></li>
			    <li><a href="activityGiftGet.action"  target="indexLeft"><span>③现在领奖</span></a></li>
			</ul>
		</div>
		<!-- End Main Nav -->
	</div>
</div>
<!-- End Header -->

<!-- Container -->
<div id="container">
	<div class="shell">
		<!-- Main -->
		<div id="main">
			<div class="cl">&nbsp;</div>			
			<!-- Content -->
			<div id="content">
			
			<iframe id="indexLeft" name="indexLeft" frameborder="0"  src="leftIndex.jsp" width="800"  height="800" scrolling="no"></iframe>
					
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
</div>
</div>
<!-- End Container -->

<!-- Footer -->
<div id="footer"style="margin-top: 800px;">
	<div class="shell">
		<span class="left">&copy; 2015 - FNST</span>
		<span class="right">
			Design by <a href="http://FNST.com" target="_blank" title="The Sweetest CSS Templates WorldWide">FNST.com</a>
		</span>
	</div>
</div>
<!-- End Footer -->
	
</body>
</html>