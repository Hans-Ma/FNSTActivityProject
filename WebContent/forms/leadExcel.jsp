<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>签到左下面的框架页面</title>
<link rel="stylesheet" href="CSS/index.css" type="text/css" media="all" />
<script type="text/javascript">
	function exportNumExcel() {
	 var actid = document.getElementById("actid").value;
	 var ifgetGift = document.getElementById("ifgetGift").value;
	 window.location.href = "exportNumExcel?actid=" + actid + "&ifgetGift=" + ifgetGift;
	 }

	 function exportUnnumExcel() {
		 var actid = document.getElementById("actid").value;
		 window.location.href = "exportUnnumExcel?actid=" + actid ;
		 }
</script>
	<style type="text/css">
	.button.gray{
	border:1px solid #dce1e6;
	box-shadow: 0 1px 2px #fff inset,0 -1px 0 #a8abae inset;
	background: -webkit-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: -moz-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: linear-gradient(top,#f2f3f7,#e4e8ec);
	width: 70px;
	line-height: 30px;
	text-align: center;
	font-weight: bold;
	color: grey;
	margin:0 20px 20px 0;
	position: relative;
	overflow: hidden;
	}
	</style>
</head>
<body>
	<div id="container"  style="margin-top:48px;">
	<!-- Container -->
			<!-- Small Nav -->
			<div class="small-nav">
				<a href="#">当前页面</a> <span>&gt;</span> 生成报表
			</div>
				<div class="cl">&nbsp;</div>
				<!-- Content -->
					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head" style="width: 710px;">
							<h2 class="left">参加活动会员信息统计</h2>
							<div class="right" id="three" style="float: right"></div>

						</div>
						<!-- End Box Head -->
						<div id="four">
							<form action="signInSelect.action" >
								<label>活动</label>
								<select name="actid" id="actid" style="width: 100px;">
									<c:forEach items="${activityList}" var="activity">
										<option value="${activity.actId}">${activity.actName}</option>
									</c:forEach>
								</select> <label>类型会员：<b>会员</b>&nbsp;&nbsp; </label>
									<label>是否领取奖品</label> <select name="ifgetGift" id="ifgetGift" style="width: 70px;">
									<option value="" selected="selected"></option>
									<option value="1" style="display: block" id="getgift">已领取</option>
									<option value="2" id="123">未领取</option>
								</select>  <span id="two" style="float: right;"> <input type="button" value="生成报表" class="button gray" onclick="exportNumExcel()">
								</span> &nbsp;&nbsp;&nbsp; <span id="one" style="float: right; margin-top: 2px;">
									<input type="submit" class="button" value="搜索" />
								</span>
							</form>
							<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th>工号</th>
								<th>真实姓名</th>
								<th>是否签到</th>
								<th>是否领取礼品</th>
								<th>活动名</th>
								<th>活动地点</th>
							</tr>
							<s:if test="#list!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{list}" var="signin">
							<tr class="odd">
								<td><s:property value="#signin.signUser.userName"/></td>
								<td><h3><s:property value="#signin.signUser.userTrueName" /></h3></td>
								<td><s:if test="#signin.signState==1">已签到</s:if><s:else>未签到</s:else></td>
								<td><s:if test="#signin.giftState==1">已领奖</s:if><s:else>未领奖</s:else></td>
								<td><s:property value="#signin.signAct.actName"/></td>
								<td><s:property value="#signin.signAct.actAddress"/></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form id="selectform" action="userSelect.action">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" class="button gray" /></div>
							</form>
							<div class="right">
								<a href="signInSelect.action?pageDivided.currentPage=1">首页</a>
								<a href="signInSelect.action?pageDivided.currentPage=%{pageDivided.currentPage-1}">上一页</a>
								<a href="signInSelect.action?pageDivided.currentPage=1">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="signInSelect.action?pageDivided.currentPage=2">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="signInSelect.action?pageDivided.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">signInSelect.action?pageDivided.currentPage=${pageDivided.currentPage+1}</s:if>">下一页</a>
								<a href="signInSelect.action?pageDivided.currentPage=${pageDivided.totalPage}">尾页</a>
							</div>
						</div>
						<!-- End Pagging -->
						
					</div>
					<!-- Table -->
						<!-- Main -->
					</div>
				</div>



				<div id="main">
					<div class="cl">&nbsp;</div>
					<!-- Content -->
					<div id="content">
						<!-- Box -->
						<div class="box">
							<!-- Box Head -->
							<div class="box-head" style="width: 710px;">
								<h2 class="left">参加活动非会员信息统计</h2>
								<div class="right" id="three" style="float: right"></div>

							</div>
							<!-- End Box Head -->
							<div id="four">
								<form action="signInSelect2.action">
									<label>活动</label> <select name="actid" id="actid"
										style="width: 100px;">
										<c:forEach items="${activityList}" var="activity">
											<option value="${activity.actId}">${activity.actName}</option>
										</c:forEach>
									</select> <label>类型会员：<b>非会员</b>&nbsp;&nbsp;
									</label>  <span id="two" style="float: right;"> 
									<input type="button" value="生成报表" class="button gray"  onclick="exportUnnumExcel()">
								</span> &nbsp;&nbsp;&nbsp; <span id="one"
									style="float: right; margin-top: 2px;"> <input
									type="submit" class="button" value="搜索" />
								</span>
								</form>
								<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th>工号</th>
								<th>真实姓名</th>
								<th>是否签到</th>
								<th>活动名</th>
								<th>活动地点</th>
							</tr>
							<s:if test="#list!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{list2}" var="signin">
							<tr class="odd">
								<td><s:property value="#signin.signUser.userName"/></td>
								<td><h3><s:property value="#signin.signUser.userTrueName" /></h3></td>
								<td><s:if test="#signin.signState==1">已签到</s:if><s:else>未签到</s:else></td>
								<td><s:property value="#signin.signAct.actName"/></td>
								<td><s:property value="#signin.signAct.actAddress"/></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form id="selectform" action="signInSelect2.action">
							<div class="left">当前:第<s:property value="%{pageDivided2.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided2.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="signInSelect2.action?pageDivided2.currentPage=1">首页</a>
								<a href="signInSelect2.action?pageDivided2.currentPage=%{pageDivided2.currentPage-1}">上一页</a>
								<a href="signInSelect2.action?pageDivided2.currentPage=1">1</a>
								<s:if test="%{pageDivided2.totalPage>=2}">
								<a href="signInSelect2.action?pageDivided2.currentPage=2">2</a>
								<s:if test="%{pageDivided2.totalPage>=3}"><a href="signInSelect2.action?pageDivided2.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided2.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided2.currentPage != pageDivided2.totalPage}">signInSelect2.action?pageDivided2.currentPage=${pageDivided2.currentPage+1}</s:if>">下一页</a>
								<a href="signInSelect2.action?pageDivided2.currentPage=${pageDivided2.totalPage}">尾页</a>
							</div>
						</div>
						<!-- End Pagging -->
						
					</div>
							</div>
						</div>

						<!-- Main -->
					</div>
				</div>
	</div>

				<%-- 		<div id="main">
				<div class="cl">&nbsp;</div>
				<!-- Content -->
				<div id="content">
					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head" style="width: 680px;">
							<h2 class="left">活动礼品信息统计</h2>
							<div class="right" id="three" style="float: right"></div>

						</div>
						<!-- End Box Head -->
						<div id="four">
							<form action="">
								<label>活动</label> <select name="actid" id="actid"
									style="width: 100px;">
									<c:forEach items="${activityList}" var="activity">
										<option value="${activity.actId}">${activity.actName}</option>
									</c:forEach>
								</select><label>礼品</label> <select name="gift" id="gift"
									style="width: 100px;">
									<c:forEach items="${giftList}" var="gift">
										<option value="${gift.giftId}">${gift.giftName}</option>
									</c:forEach>
								</select> <span id="one" style="float: right; margin-top: 2px;"> <input
									type="submit" class="button" value="生成报表" />
								</span>

							</form>
						</div>
					</div>

					<!-- Main -->
				</div>
			</div> --%>
			<!-- End Container -->
</body>
</html>