<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>验证左下面的框架页面</title>
<link rel="stylesheet" href="CSS/index.css" type="text/css"
	media="all" />
<style type="text/css">
td {
	text-align: center;
}
	.button.gray{
	color: 2936A1;
	text-shadow:1px 1px 1px #fff;
	border:1px solid #dce1e6;
	box-shadow: 0 1px 2px #fff inset,0 -1px 0 #a8abae inset;
	background: -webkit-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: -moz-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: linear-gradient(top,#f2f3f7,#e4e8ec);
	}
	
	.button{
	width: 130px;
	line-height: 80px;
	text-align: center;
	font-weight: bold;
	font-size: 13px;
	color: #2936A1;
	text-shadow:1px 1px 1px #333;
	border-radius: 5px;
	margin:0 20px 20px 0;
	position: relative;
	overflow: hidden;
}
</style>
	<script type="text/javascript">
	function sendEmail() {
	 window.location.href = "sendEmail";
	 }
</script>
</head>
<body>

	<!-- Container -->
	<div id="container">
		<div class="shell" style="margin-top: 48px; margin-left: 20px;">
			<!-- Small Nav -->
			<div class="small-nav">
				<a href="#">当前页面</a> <span>&gt;</span> 签到验证
			</div>

			<div id="main">
				<div class="cl">&nbsp;</div>

				<!-- Content -->
				<div id="content">

					<!-- Box -->
					<div class="box">
						<!-- Box Head -->
						<div class="box-head">
							<h2 class="left">会员验证</h2>

						</div>
						<!-- End Box Head -->

						<!-- Table -->
						<div class="table">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<th >活动名：${activity.actName}</th>
									<th>&nbsp;&nbsp;</th>
								</tr>
								<tr>
									<td width="100%" style="vertical-align: middle; text-align: center;">
								<input type="button" value="发送邮箱验证码" class="button gray" onclick="sendEmail()"></td>
								<td>&nbsp;</td>
								<tr>
									<td><s:if test="%{flag==1}">
											<span style="color: green; font-size: 15px;">验证码已发送,请检查邮箱输入验证码</span>
										</s:if> <s:if test="%{flag==2}">
											<span style="color: green; font-size: 15px;">验证失败，请检查您的验证码是否输入正确</span>
										</s:if> <s:if test="%{flag==3}">
											<span style="color: green; font-size: 15px;">验证成功，现在可以去领取奖品了</span>
										</s:if>
										<s:if test="%{flag==4}">
											<span style="color: green; font-size: 15px;">邮件发送失败 ,可能原因用户邮箱无效邮箱。</span>
										</s:if></td>
										<td>&nbsp;</td>
								</tr>


								<tr>
									<td>
										<form action="checkEmailValidate">
											<input type="text" name="verify" value="请输入收到的验证码"> <input
												type="text" name="validatecode" value="${validatecode}"
												style="display: none"> <input type="submit"
												value="审核验证码">
										</form>
									<td>
								</tr>
								<tr>
									<td><input type="button" class="button gray" value="重新发送邮箱验证码"
										onclick="sendEmail()">
									<td>
								</tr>
							</table>

							<!-- Pagging -->
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
	<!-- End Container -->
</body>
</html>