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
<body><%--  --%>
<!-- Container -->
<div id="container"  style="margin-top:48px; margin-left: -40px;">
	<div class="shell" style="width:680px;">
	<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			礼品发放情况
		</div>
		
		<div id="main" >
			<div class="cl" >&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">礼品发放情况</h2>
						<div class="right">
						<form action="showGiftCondition">
							<label style="font-size: 15px;">礼品搜索</label>
							<select name="giftId" id="giftId" style="width:200px; height:25px; float:left;">
								<c:forEach items="${giftList}" var="gift">
	  	 							<option value="${gift.giftId}">${gift.giftName}</option>
	   							</c:forEach>									  	 									   			 			
							</select>
							<span style="float:right;"><input type="submit" class="button" value="搜索" /></span>
						</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th></th>
								<th>礼品名</th>
								<th>活动名</th>
								<th>姓名</th>
								<th>工号</th>
								<th>电话</th>
								<th>邮箱</th>
							<!-- 	<th width="100" class="ac">操作</th> -->
							</tr>
							<s:if test="#signGiftList!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{signGiftList}" var="signGift">
							<tr class="odd">
								<td></td>
								<td><s:property value="#signGift.linkGiftId.giftName"/></td>
								<td><s:property value="#signGift.linkSignId.signAct.actName"/></td>
								<td><s:property value="#signGift.linkSignId.signUser.userTrueName"/></td>
								<td><s:property value="#signGift.linkSignId.signUser.userName"/></td>
								<td><s:property value="#signGift.linkSignId.signUser.userPhone"/></td>
								<td><s:property value="#signGift.linkSignId.signUser.userMail"/></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form  action="showGiftCondition"><%-- ? --%>
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">
							 <input name="actId" type="text" value="${actId}" size="1" style="display:none"> 
							<input name="giftId" type="text" value="${giftId}" size="1" style="display:none">行   <input type="submit" class="button" value="确定" /></div>
							</form>
						 	<div class="right">
								<a href="showGiftCondition?pageDivided.currentPage=1&actId=${actid}&giftId=${giftId}">首页</a>
								<a href="showGiftCondition?pageDivided.currentPage=%{pageDivided.currentPage-1}&actId=${actid}&giftId=${giftId}">上一页</a>
								<a href="showGiftCondition?pageDivided.currentPage=1&actId=${actid}&giftId=${giftId}">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="showGiftCondition?pageDivided.currentPage=2&actId=${actid}&giftId=${giftId}">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="showGiftCondition?pageDivided.currentPage=3&actId=${actid}&giftId=${giftId}">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">showGiftCondition?pageDivided.currentPage=${pageDivided.currentPage+1}&actId=${actid}&giftId=${giftId}</s:if>">下一页</a>
								<a href="showGiftCondition?pageDivided.currentPage=${pageDivided.totalPage}&actId=${actid}&giftId=${giftId}">尾页</a>
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
