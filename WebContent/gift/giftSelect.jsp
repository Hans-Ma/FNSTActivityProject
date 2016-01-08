<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页左下面的框架页面</title>
	<link rel="stylesheet" href="${pageScope.basePath}CSS/index.css" type="text/css" media="all" />
</head>
<body>
<!-- Container -->
<div id="container" style="margin-top:48px; margin-left: -45px;">
	<div class="shell" style="width:680px;">
	<!-- Small Nav -->
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			礼品管理
		</div>
		
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">礼品管理</h2>
						<div class="right">
						<form action="giftSelect.action">
							<label>活动礼品单</label>
							<select class="field size2" name="actId" id="actId" style="width:200px; height:30px; float:left;">
								<c:forEach items="${list}" var="act">
									<option value="${act.actId}" <c:if test="${act.actId==sessionScope.actId}">selected="selected"</c:if>>${act.actName}</option>
								</c:forEach>									  	 									   			 			
							</select>	
							<input type="submit" style="float:right;" class="button" value="搜索" />
						</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th>序号</th>
								<th>礼品名</th>
								<th>活动名</th>
								<th>礼品数量</th>
								<th>剩余量</th>
							</tr>
							<s:if test="#list!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{giftList}" var="gift">
							<tr class="odd">
								<td><s:property value="#gift.giftId"/></td>
								<td><h3><a href=""><s:property value="#gift.giftName"/></a></h3></td>
								<td><s:property value="#gift.giftAct.actName"/></td>
								<td><s:property value="#gift.giftNum"/></td>
								<td><s:if test = "#gift.giftRest>=0"><s:property value="#gift.giftRest"/></s:if><s:if test = "#gift.giftRest<0">0</s:if></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form id="selectform" action="giftSelect.action">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="giftSelect.action?pageDivided.currentPage=1">首页</a>
								<a href="giftSelect.action?pageDivided.currentPage=%{pageDivided.currentPage-1}">上一页</a>
								<a href="giftSelect.action?pageDivided.currentPage=1">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="giftSelect.action?pageDivided.currentPage=2">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="giftSelect.action?pageDivided.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test="%{pageDivided.currentPage != pageDivided.totalPage}">giftSelect.action?pageDivided.currentPage=${pageDivided.currentPage+1}</s:if>">下一页</a>
								<a href="giftSelect.action?pageDivided.currentPage=${pageDivided.totalPage}">尾页</a>
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
