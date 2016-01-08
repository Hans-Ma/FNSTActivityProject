<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>管理员登录首页</title>
	<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />
</head>
<body >
<!-- Header -->
<div id="header">
	<div class="shell">
		<!-- Logo + Top Nav -->
		<div id="top">
			<h1><a href="#">FNST活动管理系统</a></h1>
			<div id="top-navigation">
			<c:if test="${user!=null}"><span id="user">欢迎 <strong><s:property value='%{user.userTrueName}'/></strong> 登录</span></c:if>
			<c:if test="${user==null}"><span id="user"><strong>请先登录，再做操作</strong></span></c:if>
			&nbsp;&nbsp;
				<a href="loginout.action"><span>退出登录</span></a>
			</div>
		</div>
		<!-- End Logo + Top Nav -->
		
		<!-- Main Nav -->
		<div id="navigation">
			<ul>
			    <li><a href="indexLeft.jsp" target="indexLeft"><span>我的主页</span></a></li>
			    <li><a href="userSelect.action"  target="indexLeft"><span>会员管理</span></a></li>
			    <li><a href="giftSelect.action"  target="indexLeft"><span>礼品管理</span></a></li>
			    <li><a href="activitySelect.action" target="indexLeft"><span>活动管理</span></a></li>
			     <li><a href="jumpToLeadExcel" target="indexLeft"><span>生成报表</span></a></li>
			</ul>
		</div>
		<!-- End Main Nav -->
			<span id="checkUser" style="display:none; color:red; font-size:18px;">请先登录，再做操作 !</span>
		<!-- 判断用户是否登录 -->			
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
			
			<iframe id="indexLeft" name="indexLeft" frameborder="0"  src="indexLeft.jsp" width="726"  height="900" scrolling="no" style="margin-left: 24px;"></iframe>
					
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
</div>
</div>
<!-- End Container -->

<iframe id="indexRight" frameborder="0"  src="indexRight.jsp" width="201"  height="800" scrolling="no"></iframe>

<!-- Footer -->
<div id="footer">
	<div class="shell">
		<span class="left">&copy; 2015 - FNST</span>
		<span class="right">
			Design by <a href="http://chocotemplates.com" target="_blank" title="The Sweetest CSS Templates WorldWide">JAQ、YXF、DB、MY</a>
		</span>
	</div>
</div>
<!-- End Footer -->
	
</body>
</html>