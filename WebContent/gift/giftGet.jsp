<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>验证左下面的框架页面</title>
	<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />

</head>
<body>
<!-- Container -->
<div id="container">
	<div class="shell" style="margin-top:48px; margin-left: 20px;">
	<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			会员收取礼品
		</div>
		
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">会员收取礼品</h2>
						
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<s:if test = "list.size!= 0">
							<tr>
								<th>活动名称</th>
								<th>签到开始时间</th>
								<th>活动开始时间</th>
								<th>活动地点</th>
								<th width="110" class="ac">第三步现在领奖</th>
							</tr>
							</s:if>
							<tr><s:if test = "list.size== 0"><td  colspan="6" align="center" style="display: block;color: green;">您还没有签到任何活动</td></s:if></tr>
							<s:iterator value="%{list}" var="activity" >
							<tr>
								<td><h3><s:property value="#activity.actName"/></h3></td>
								<td><s:property value="#activity.signStart"/></td>
								<td><s:property value="#activity.StartTime"/></td>
								<td><s:property value="#activity.actAddress"/></td>
								<td>&nbsp;&nbsp;
								
								<s:url action="giftGet.action"  namespace="/" var="giftGet">
								<s:param  name="signActId" value="#activity.actId"></s:param>
								<s:param  name="userId" value="userId"></s:param>
								</s:url>
								
								<s:a href="%{#giftGet}" class="ico edit">现在领奖</s:a>
								<s:if test = "%{ isGet== 0&& #activity.actId==signActId}"><span id="check0" style="display: block;color: green;">奖品领取成功！</span></s:if>
								<s:if test = "%{ isGet== 1&& #activity.actId==signActId}"><span id="check1" style="display: block;color: blue;">已经领取过了，不要贪心哦~</span></s:if>
								<s:if test = "%{ isGet== 2&& #activity.actId==signActId}"><span id="check2" style="display: block;color: red;">身份验证不成功！</span></s:if>
								<s:if test = "%{ isGet== 3&& #activity.actId==signActId}"><span id="check3" style="display: block;color: blue;">奖品发完了，稍后补发！</span></s:if>
								</td>
								
								
							</tr>
							</s:iterator>
																											
						</table>
											
							<!-- Pagging -->
						<div class="pagging">
							<form  action="activityGiftGet.action">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="activityGiftGet.action?pageDivided.currentPage=1">首页</a>
								<a href="activityGiftGet.action?pageDivided.currentPage=%{pageDivided.currentPage-1}">上一页</a>
								<a href="activityGiftGet.action?pageDivided.currentPage=1">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="activityGiftGet.action?pageDivided.currentPage=2">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="activityGiftGet.action?pageDivided.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">activityGiftGet.action?pageDivided.currentPage=${pageDivided.currentPage+1}</s:if>">下一页</a>
								<a href="activityGiftGet.action?pageDivided.currentPage=${pageDivided.totalPage}">尾页</a>
							</div>
						</div>
						<!-- End Pagging -->
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
<!-- End Container -->	
</body>
</html>