<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>已签到的活动会员作弊管理页面</title>
	<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />
</head>
<body>
<!-- Container -->
<div id="container"  style="margin-top:48px;">
	<div id="shell" style="width:680px;">
	<!-- Small Nav -->
		<div class="small-nav" >
			<a href="#">当前页面</a>
			<span>&gt;</span>
			出席名单
		</div>
		
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">出席名单管理</h2>	
						<a style="text-decoration:none; color:#FFA500; float:right; font-size: 18px;" href="javascript:window.history.back(-1)">返回</a>                       				
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th width="10%">序号</th>
								<th width="15%">登录名</th>
								<th width="10%">真实姓名</th>
								<th width="22%">电话</th>
							</tr>
							<s:if test="#list!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{list}" var="signIn">
							<tr class="odd">
								<td><s:property value="#signIn.signId"/></td>
								<td><s:property value="#signIn.signUser.userName"/></td>
								<td><s:property value="#signIn.signUser.userTrueName"/></td>
								<td><s:property value="#signIn.signUser.userPhone"/></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form  action="activitySelect.action">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="activitySelect.action?pageDivided.currentPage=1">首页</a>
								<a href="activitySelect.action?pageDivided.currentPage=%{pageDivided.currentPage-1}">上一页</a>
								<a href="activitySelect.action?pageDivided.currentPage=1">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="activitySelect.action?pageDivided.currentPage=2">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="activitySelect.action?pageDivided.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">activitySelect.action?pageDivided.currentPage=${pageDivided.currentPage+1}</s:if>">下一页</a>
								<a href="activitySelect.action?pageDivided.currentPage=${pageDivided.totalPage}">尾页</a>
							</div>
						</div>
					<!-- Table -->
					
				</div>
				<!-- End Box -->
			</div>
			<!-- End Content -->
			<div class="cl">&nbsp;</div>			
		</div>
		<!-- Main -->
	</div>
	</div>
</div>
<!-- End Container -->	
</body>
</html>
