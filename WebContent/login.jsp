<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.net.URLDecoder"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>会员活动系统登录</title>
	<!--link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'-->
	<link type="text/css" rel="stylesheet" media="all" href="CSS/login.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<!-- -->
	<script>var __links = document.querySelectorAll('a');function __linkClick(e) { parent.window.postMessage(this.href, '*');} ;for (var i = 0, l = __links.length; i < l; i++) {if ( __links[i].getAttribute('data-t') == '_blank' ) { __links[i].addEventListener('click', __linkClick, false);}}</script>
	<script src="javaScript/jquery.min.js"></script>
	<script src="javaScript/checkin.js"></script>
	<script>
	
		var XMLHttp ;
		function createXMLHttpRequest(){
			try{
				XMLHttp=new ActiveXObject("Msxml2.XMLHTTP");
			}catch(e){
				try{
					XMLHttp=new ActiveXObject("Microsoft.XMLHTTP");
				}catch(e){
					try{
						XMLHttp=new XMLHttpRequest();
						if(XMLHttp.overrideMimeType){
							XMLHttp.overrideMimeType("text/xml");
						}
					}catch(e){}
				}
			}
		}
		
		function doAjax(){
			createXMLHttpRequest();
			var name = document.getElementById("username").value;
			var pass = document.getElementById("txtUserName").value;
			var sign = document.getElementById("txtPassword").value;
			if(XMLHttp!=null){
				XMLHttp.open("POST","ajaxLoginAction?user.userName="+name+"&user.userPassword="+pass+"&rand="+sign,true);   //把userName的值传到ajazCheck的action当中
				XMLHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");
				XMLHttp.onreadystatechange=processRequest;
				XMLHttp.send(null);
			}else{
				alert("不能创建XMLHttpRequest对象实例");
			}		
		}
		
		function processRequest(){
			if(XMLHttp.readyState==4){
			   if(XMLHttp.status==200){
				var sobj1=document.getElementById("check1");
				var sobj2=document.getElementById("check2");
				var sobj3=document.getElementById("check3");
				var str= XMLHttp.responseText;
				if(str==0){
					 sobj1.style.display = "block"; //用户名已存在，不可用
					 sobj2.style.display = "none";
					 sobj3.style.display = "none";
				}else if(str==1){
					 sobj1.style.display = "none"; //用户名已存在，不可用
					 sobj2.style.display = "block";
					 sobj3.style.display = "none";
				}else{
					 sobj1.style.display = "none"; //用户名已存在，不可用
					 sobj3.style.display = "block";
					 sobj2.style.display = "none";
				}
			} }
		}	
	</script>
</head>
<%
	String username = "";
	Cookie[] cookie = request.getCookies();
	if(cookie != null){
		for(int i = 0; i < cookie.length; i++ ){
			if(cookie[i].getName().equals("username")){
				username = URLDecoder.decode(cookie[i].getValue()); //解码
			}
		}
	}
	%>
<body>
<!-- contact-form -->	
<div class="message warning">

<div class="contact-form">
	<div class="logo">
		<h3>会员登录</h3>
	</div>	
<!--- form --->
<form class="form" action="login.action" method="post" name="contact_form">
	<ul>
		<li>
			 <label><img src="images/contact.png" alt=""></label>
			 <input type="text" name="user.userName" class="email" id="username" placeholder="会员名" required />		            
		 </li>
		 <li>
			 <label><img src="images/lock.png" alt=""></label>
			 <input type="Password" name="user.userPassword" id="txtUserName" placeholder="密码" required />		         
		 </li>
		 <li>
			<label><img src="images/lock.png" alt=""></label>
			<input type="text" name="rand" class="email" id="txtPassword" onblur="doAjax()" placeholder="验证码" required />	   
		 </li>
	 </ul>
	 <br>
	<p style="float: left;"> <img src="rand.action" width="80" height="25px" onclick="changeValidateCode(this)">	</p>       	      
	<span id="check1" style="display: none;color: red;">验证码错误</span>
	<span id="check2" style="display: none;color: green;">用户成功登录</span>
	<span id="check3" style="display: none;color: red;">用户名或密码错误</span><br/>   	
	<ul>	 
		<li class="style">
		     <input type="Submit" id="alogin" value="登录系统" >
		 </li>
	</ul>
		
	<div class="clear"></div>
	
		   	
</form>
</div>
<div class="alert-close"></div>
</div>
<div class="clear"></div>
<!--- footer --->
<div class="footer">
</div>
</body>
</html>
