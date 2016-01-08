<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>活动管理页面</title>
	<link rel="stylesheet" href="${pageScope.basePath}CSS/index.css" type="text/css" media="all" />
</head>
<body>
<!-- Container -->
<div id="container"  style="margin-top:48px; margin-left: -40px;">
	<div class="shell" style="width:680px;">
	<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			补发礼品
		</div>
		
		<div id="main" >
			<div class="cl" >&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">补发礼品</h2>
						<div class="right">
					</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th>活动名</th>
								<th>姓名</th>
								<th>工号</th>
								<th>电话</th>
								<th>邮箱</th>
								<th width="100" class="ac">操作</th>
							</tr>
							<s:if test="signInList.size== 0">
								<tr class="odd"><td colspan="6" align="center">沒有未收到礼品的会员</td></tr>
							</s:if>
							<s:iterator value="%{signInList}" var="signIn">
							<tr class="odd">
								<td><s:property value="#signIn.signAct.actName"/></td>
								<td><s:property value="#signIn.signUser.userTrueName"/></td>
								<td><s:property value="#signIn.signUser.userName"/></td>
								<td><s:property value="#signIn.signUser.userPhone"/></td>
								<td><s:property value="#signIn.signUser.userMail"/></td>
								<td><a href="giftGetForAdmin?signActId=${signIn.signAct.actId}&userId=${signIn.signUser.userId}" class="ico del">发放礼品</a></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form  action="getNumByActId">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   
							<input name="actId" type="text" value="${actId}" size="1" style="display:none">
							<input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="getNumByActId?pageDivided.currentPage=1&actId=${actId}">首页</a>
								<a href="getNumByActId?pageDivided.currentPage=%{pageDivided.currentPage-1}&actId=${actId}">上一页</a>
								<a href="getNumByActId?pageDivided.currentPage=1&actId=${actId}">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="getNumByActId?pageDivided.currentPage=2&actId=${actId}">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="getNumByActId?pageDivided.currentPage=3&actId=${actId}">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">getNumByActId?pageDivided.currentPage=${pageDivided.currentPage+1}&actId=${actId}</s:if>">下一页</a>
								<a href="getNumByActId?pageDivided.currentPage=${pageDivided.totalPage}&actId=${actId}">尾页</a>
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
