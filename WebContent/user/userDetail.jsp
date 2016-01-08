<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
		<title>添加活动</title>
		<link rel="stylesheet" href="${pageScope.basePath}CSS/activityAdd.css" type="text/css" />	
	</head>
</head>
<body>
<div id="container">
	<div class="shell">
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			会员详细信息查询
		</div>		
		<div id="main">
			<div class="cl">&nbsp;</div>
			<div id="content1">		
				<div class="box">
					<div class="box-head" >
						<h4>会员详细信息查询</h4>
					</div>				
						<div class="form">
								<p>
									<label>登录名称</label>
									<input type="text" style="width:250px; height: 25px;" id = "content1" value="${user.userName}"  disabled="disabled" />
								</p>
								<p>
									<br>
									<label>默认密码</label>
									<input type="text" style="width:250px; height: 25px;" disabled="disabled" value="${user.userPassword}" value="123456"/>
								</p>
								<p>
									<label>真实姓名</label>
									<input type="text" style="width:250px; height: 25px;" disabled="disabled" value="${user.userTrueName}" id = "content2"/> 
								</p>
								<p class="inline-field">			
									<label>用户类型<span style="margin-right:210px;"></span>性别<span></span></label>
									<input class="field size2" value="${user.userType.typeName}" disabled="disabled" id="actId" style="width:250px; height:31px;"/>									
									<input class="field size2" value="${user.userSex}" disabled="disabled" id="actId" style="width:250px; height:31px;">									
								</p>	
								<p class="inline-field">			
									<label>年龄</label>						 
									<input class="datetimepicker" disabled="disabled" type="text" style="width:250px; height: 25px;" value="${user.userAge}" id="age" />																					
								</p >
									<p class="inline-field">			
									<label>手机号</label>
									<input type="text" style="width:250px; height: 25px;" disabled="disabled" value="${user.userPhone}" id="phone" />
								</p>
									<p class="inline-field">			
									<label>邮箱</label>
									<input class="datetimepicker" type="text" style="width:250px; height: 25px;" disabled="disabled" id="mail" value="${user.userMail}"  onchange="checkMail(this)"/><br clear="all"/>
								</p>																		
						</div>
						<div class="buttons">						
							<input type="button" class="button" value="返回" onclick="javascript:history.go(-1);" />
						</div>
				</div>
			</div>	
	</div>			
	</div>
</div>
</body>
</html>