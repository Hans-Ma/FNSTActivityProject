<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
		<title>添加活动</title>
		<link rel="stylesheet" href="${pageScope.basePath}CSS/activityAdd.css" type="text/css" />	
	</head>
	<script type="text/javascript">
		var isOk = true;		
		//字符数统计
		function check(fieldname, username, remname, len) {
			if(fieldname.value.length > len){
				fieldname.value = (fieldname.value).substring(0, len);
			//	alert("您的输入超过" + len + "个字符了！");
				color=document.getElementById("check1"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
			}else{
				username.value = eval(fieldname.value.length);
				remname.value = len - username.value;
				color=document.getElementById("check1"); // 找到元素
				color.style.display="none";
			}
		}	
		function check1(fieldname, username, remname, len) {
			if(fieldname.value.length > len){
				fieldname.value = (fieldname.value).substring(0, len);
			//	alert("您的输入超过" + len + "个字符了！");	
				color=document.getElementById("check3"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
			}else{
				username.value = eval(fieldname.value.length);
				remname.value = len - username.value;
				color=document.getElementById("check3"); // 找到元素	
				color.style.display="none";
			}
		}
		//输入是否为自然数
		function checkNum(age) {
			if(parseInt(age.value)!=age.value || age.value<=0 ||  age.value>=100){
			//	alert("您输入的礼品数量不合法，请输入一个自然数");
				color=document.getElementById("check5"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;	
			}else{
				color=document.getElementById("check5") // 找到元素	
				color.style.display="none"; 
				isOk = true;
			}
		}
		
		function checkMail(obj){
			var strReg = "";
			var r;
			var strText = obj.value;
			strReg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;//正则表达式
			var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
			r = strText.search(strReg);
			if(r == -1){
				color=document.getElementById("check8"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
			}else{
				color=document.getElementById("check8") // 找到元素	
				color.style.display="none"; 
				isOk = true;
			}
		}
		//验证手机号
		function checkPhone(obj){
			var strReg = "";
			var r;
			var strText = obj.value;
			strReg = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
			r = strText.search(strReg);
			if(r == -1){
				color=document.getElementById("check7"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
			}else if(strText.length>11){
				color=document.getElementById("check7"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
			}else{
				color=document.getElementById("check7") // 找到元素	
				color.style.display="none"; 
				isOk = true;
			}
		} 
		//判断是否都正确填写
		function isCon(content1, content2, age, phone, mail) {
			if( content1.value.length<=0){
				color=document.getElementById("check2") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
			if( content2.value.length<=0){
				color=document.getElementById("check4") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
			if( age.value.length<=0){
				color=document.getElementById("check5") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
			if( phone.value.length<=0){
				color=document.getElementById("check7") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
			if( mail.value.length<=0){
				color=document.getElementById("check8") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
		}
		
	</script>
</head>
<body>
<div id="container">
	<div class="shell">
		<div class="small-nav">
			<a href="#">当前页面</a>
			<span>&gt;</span>
			修改会员信息
		</div>		
		<div id="main">
			<div class="cl">&nbsp;</div>
			<div id="content1">		
				<div class="box">
					<div class="box-head" >
						<h4>修改会员信息</h4>
					</div>				
					<form action="userUpdate.action" method="post" onsubmit="return isCon(content1, content2, age, phone, mail)">
						<div class="form">
								<input type="hidden" name="user.userId" value="${user.userId}">
								<input type="hidden" name="user.userName" value="${user.userName}"> 
								<p>
									<label>登录名称 <span>(Required Field)&nbsp;不超过15个字</span></label>
									<input type="text" disabled="disabled" value="${user.userName}" style="width:250px; height: 25px;" id = "content1" 
										onkeydown = "check(content1, contentUse1, contentRem1, 15)"
                						onkeyup = "check(content1, contentUse1, contentRem1, 15)"/>
									<font color="#7F7F7F" style="margin-left: 5px;">
                                                                                 已用：<input type="text" name="contentUse1" value="0" size="4" disabled style="text-align:center;border:0;"> 个&nbsp;&nbsp;
                                                                                剩余：<input type="text" name="contentRem1" value="15" size="4" disabled style="text-align:center;border:0;"> 个                      			
                        			</font>
                        			<span id="check1" style="display: none;color: green;font-size:15px;">您的输入超过 了15个字符！</span>
                        			<span id="check2" style="display: none;color: green;font-size:15px;">请输入登录名称！</span>
								</p>
								<p class="inline-field">			
									<label>默认密码<span style="margin-right:145px;">(Required Field)</span></label>
									<input type="text" style="width:250px; height: 25px;" name="user.userPassword" value="${user.userPassword}"/>
								</p>
								<p>
									<label>真实姓名 <span>(Required Field)&nbsp;不超过15个字</span></label>
									<input type="text" style="width:250px; height: 25px;" name="user.userTrueName" id = "content2" value="${user.userTrueName}"
										onkeydown = "check1(content2, contentUse2, contentRem2, 6)"
                						onkeyup = "check1(content2, contentUse2, contentRem2, 6)"/>
									<font color="#7F7F7F" style="margin-left: 5px;">
                                                                                                                                    已用：<input type="text" name="contentUse2" value="0" size="4" disabled style="text-align:center;border:0;"> 个&nbsp;&nbsp;
                                                                                                                                   剩余：<input type="text" name="contentRem2" value="6" size="4" disabled style="text-align:center;border:0;"> 个                      			
                        			</font>
                        			<span id="check3" style="display: none;color: green;font-size:15px;">您的输入超过 了6个字符！</span>
                        			<span id="check4" style="display: none;color: green;font-size:15px;">请输入真实姓名！</span>
								</p>
								<p class="inline-field">			
									<label>用户类型<span style="margin-right:110px;">(Required Field)</span>性别<span>(Required Field)</span></label>
									<select class="field size2" name="user.userType.typeId" id="actId" style="width:250px; height:31px;">
											<option value="1" <c:if test="${user.userType.typeId==1}">selected="selected"</c:if>>管理员</option>									
	  	 									<option value="2" <c:if test="${user.userType.typeId==2}">selected="selected"</c:if>>会员</option>	
	  	 									<option value="3" <c:if test="${user.userType.typeId==3}">selected="selected"</c:if>>VIP会员</option>		
	  	 									<option value="4" <c:if test="${user.userType.typeId==4}">selected="selected"</c:if>>非会员</option>										  	 									   			 			
									</select>
									<input type="text" value="${user.userSex}" class="field size2" name="user.userSex" id="actId" style="width:250px; height:25px;">
								</p>	
								<p class="inline-field">			
									<label>年龄<span  style="margin-right:50px">(Required Field)&nbsp;请输入一个自然数</span></label>						 
									<input value="${user.userAge}" class="datetimepicker" type="text" style="width:250px; height: 25px;" name="user.userAge" id="age" onblur = "checkNum(age) " />																					
									<span id="check5" style="display: none;color: green;font-size:15px;">年龄输入不合法</span>
								</p>
									<p class="inline-field">			
									<label>手机号<span>(Required Field)</span></label>
									<input value="${user.userPhone}" type="text" style="width:250px; height: 25px;" name="user.userPhone" id="phone" onchange="checkPhone(this)"/>
									<span id="check7" style="display: none;color: green;font-size:15px;">请输入正确的手机号！</span>
								</p>
									<p class="inline-field">			
									<label>邮箱<span>(Required Field)</span></label>
									<input value="${user.userMail}" class="datetimepicker" type="text" style="width:250px; height: 25px;" id="mail" name="user.userMail"  onchange="checkMail(this)"/><br clear="all"/>
									<span id="check8" style="display: none;color: green;font-size:15px;">请输入正确的邮箱！</span>
								</p>																		
						</div>
						<div class="buttons">						
							<input type="Submit" class="button" value="更新" />
							<input type="button" class="button" value="返回" onclick="javascript:window.history.go(-1)" />
						</div>
					</form>
				</div>
			</div>	
	</div>			
	</div>
</div>
</body>
</html>