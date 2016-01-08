<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
		<title>添加活动</title>
		<link rel="stylesheet" href="CSS/activityAdd.css" type="text/css" />	
		<link rel="stylesheet" href="CSS/main.css">
		<script type="text/javascript" language="javascript" src="javaScript/prototype-1.js"></script>
		<script type="text/javascript" language="javascript" src="javaScript/prototype-base-extensions.js"></script>
		<script type="text/javascript" language="javascript" src="javaScript/prototype-date-extensions.js"></script>
		<script type="text/javascript" language="javascript" src="javaScript/behaviour.js"></script>
		<script type="text/javascript" language="javascript" src="javaScript/datepicker.js"></script>
		<link rel="stylesheet" href="CSS/datepicker.css">
		<script type="text/javascript" language="javascript" src="javaScript/behaviors.js"></script>
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
		
		//礼品数量输入是否为自然数
	
		function checkNum(giftNum) {
			if(parseInt(giftNum.value)!=giftNum.value || giftNum.value<=0 ){
				color=document.getElementById("check2"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;		
			}else{
				color=document.getElementById("check2") // 找到元素	
				color.style.display="none"; 
				isOK = true;
			}
		}

		//判断是否都正确填写

		function isCon(address, content, content1, actStartTime, actEndTime, signStartTime, giftNum){
			if(content1.value.length<=0 || address.value.length<=0 || content.value.length<=0 ||
					actStartTime.value.length<=0 || actEndTime.value.length<=0 || signStartTime.value.length<=0 ||
					giftNum.value.length<=0){
				color=document.getElementById("check8") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}else{
				color.style.display="none"; 
				color.style.float="right";
				return true;
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
			添加活动
		</div>		
		<div id="main">
			<div class="cl">&nbsp;</div>
			<div id="content1">		
				<div class="box">
					<div class="box-head" >
						<h4>添加活动</h4>
					</div>				
					<form action="addActivity.action" method="post" onsubmit="return isCon(address, content, content1, actStartTime, actEndTime, signStartTime, giftNum)">
						<div class="form">
								<p>
									<label>活动名称 <span>(Required Field)&nbsp;不超过15个字</span></label>
									<input type="text" style="width:250px; height: 25px;" name="activity.actName"  id = "content1" 
										onkeydown = "check(content11, content1Use, content1Rem, 15)"
                						onkeyup = "check(content1, content1Use, content1Rem, 15)"/>
									<font color="#7F7F7F" style="margin-left: 5px;">
                                                                                                                                    已用：<input type="text" name="content1Use" value="0" size="4" disabled style="text-align:center;border:0;"> 个&nbsp;&nbsp;
                                                                                                                                   剩余：<input type="text" name="content1Rem" value="15" size="4" disabled style="text-align:center;border:0;"> 个                      			
                        			</font>
                        			<span id="check1" style="display: none;color: green;font-size:15px;">您的输入超过 了15个字符！</span>
                        			<span id="check3" style="display: none;color: green;font-size:15px;">请输入礼品名称！</span>
								</p>	
								<p class="inline-field">			
									<label>活动地点<span>(Required Field)</span></label>
									<input type="text" style="width:250px; height: 25px;" name="activity.actAddress" id="address"/>
								</p>
								<p class="inline-field">			
									<label>活动开始时间<span  style="margin-right:100px">(Required Field)&nbsp;</span>活动结束时间<span>(Required Field)&nbsp;</span><span style="color: blue;font-size:15px;">结束时间要在开始时间之后！</span></label>
							 
									<input class="datetimepicker" type="text" style="width:250px; height: 25px;" name="actStartTime" id="actStartTime"  />											
									<input class="datetimepicker" type="text" style="width:250px; height: 25px;" name="actEndTime" id="actEndTime" onblur = "checkDate() "/>
									
								</p >
									<p class="inline-field">			
									<label>活动内容<span>(Required Field)</span></label>
									<textarea  name="activity.actContent"  id="content" cols = "75" rows = "8"></textarea>						
								</p>
									<p class="inline-field">			
									<label>活动签到开始时间<span>(Required Field)</span><span style="color: blue;font-size:15px;">签到时间要在活动开始时间之前！</span></label>
									<input class="datetimepicker" type="text" style="width:250px; height: 25px;" name="signStartTime" id="signStartTime"/><br clear="all"/>
								</p>
								<p class="inline-field">			
									<label>活动礼品的种类数量<span>(Required Field)&nbsp;</span></label>
									<input type="text" style="width:250px; height: 25px;" name="activity.actGiftNumber" id="giftNum" onblur = "checkNum(giftNum) "/>
									<span id="check2" style="display: none;color: green;font-size:15px;">您输入的数量不合法，请输入一个自然数</span>
									<span id="check4" style="display: none;color: green;font-size:15px;">请输入礼品的数量！</span><br/>
									<span id="check8" style="display: none;color: green;font-size:15px;">请检查正确,看是否有漏填！</span>										
								</p>
																
						</div>
						<div  id="submit" style="float: right;">
					
						<a style="text-decoration:none;" href="addGiftReady.action?activity.actId=${activity.actId}&activity.actName=${activity.actName}&activity.actGiftNumber=${activity.actGiftNumber}"><span>活动提交后,点此为活动添加礼品</span>	</a>					

						</div>
						<br><br>
						<div class="buttons">						
							<input type="Submit" class="button" value="提交" />
							<input type="reset" class="button" value="重置" />
							
						</div>
					</form>
				</div>
			</div>	
		</div>			
	</div>
</div>
</body>
</html>