<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>添加礼品</title>
	<link rel="stylesheet" href="CSS/giftAdd.css" type="text/css" />

	<script>
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
			//	alert("您输入的礼品数量不合法，请输入一个自然数");
				color=document.getElementById("check2"); // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				isOK = false;
		
			}else{
				color=document.getElementById("check2") // 找到元素	
				color.style.display="none"; 
			}
		}

		//判断是否都正确填写
		function isCon(content, giftNum) { 
			actGiftNumber.value= actGiftNumber.value-1;
			if(content.value.length<=0){
				color=document.getElementById("check3") // 找到元素	
				color.style.display="block"; 
				color.style.float="right";
				return false;
			}
			if( giftNum.value.length<=0){
				color=document.getElementById("check4") // 找到元素	
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
			添加礼品
		</div>		
		<div id="main">
			<div class="cl">&nbsp;</div>
			<div id="content">		
				<div class="box">
					<div class="box-head">
						<h4>添加礼品</h4>
					</div>				
					<form action="addGift.action?gift.giftAct.actId=${activity.actId}&activity.actId=${activity.actId}&activity.actName=${activity.actName}&activity.actGiftNumber=${activity.actGiftNumber}&giftNum=${giftNum}&kind=${kind-1}" method="post" onsubmit="return isCon(content, giftNum)">
						<div class="form">
								<p>
									<p class="inline-field">
									<label>该礼品为哪个活动所用<span>(Required Field)</span></label>
									<input type="text" style="width:250px; height: 25px;" id="actNum" value="${activity.actName}" readonly="readonly"/>	
								</p>
								<p>
									<label>礼品名称 <span>(Required Field)&nbsp;不超过10个字</span></label>
									<input type="text" style="width:250px; height: 25px;" name="gift.giftName"  id = "content" 
										onkeydown = "check(content, ContentUse, ContentRem, 10)"
                						onkeyup = "check(content, ContentUse, ContentRem, 10)"/>
									<font color="#7F7F7F" style="margin-left: 2px;">
                                                                                                                                    已用：<input type="text" name="ContentUse" value="0" size="3" disabled style="text-align:center;border:0;"> 个,
                                                                                                                                   剩余：<input type="text" name="ContentRem" value="10" size="3" disabled style="text-align:center;border:0;"> 个                      			
                        			</font>
                        			<span id="check1" style="display: none;color: green;font-size:15px;">您的输入超过 了10个字符</span>
                        			<span id="check3" style="display: none;color: green;font-size:15px;">请输入礼品名称！</span>
								</p>	
								<p class="inline-field">			
									<label>礼品数量<span>(Required Field)&nbsp;推荐的数量,可按需要自行修改数量</span></label>
									<input type="text" style="width:250px; height: 25px;" name="gift.giftNum" id="giftNum" value="${giftNum}"
										onblur = "checkNum(giftNum)  "
                						/>
									<span id="check2" style="display: none;color: green;font-size:15px;">您输入的数量不合法，请输入一个自然数</span>
									<span id="check4" style="display: none;color: green;font-size:15px;">请输入礼品的数量！</span>
								</p>
								<p class="inline-field">			
									<label>为该活动添加礼品数量对的次数</label>
									<input type="text" id="actGiftNumber" value="${kind}" readonly="readonly">		
									<c:if test="${kind == 0}">
									<span style="color: green;font-size:15px;">礼品的种类已经达到${activity.actGiftNumber}种！</span>
									</c:if>										
								</p>																										
						</div>						
						<div class="buttons">	
							<a style="text-decoration:none; color:blue; margin-right: 30px; font-size: 20px;" href="javascript:window.location.href='indexLeft.jsp';">返回</a>               			
							<input type="Submit" class="button" <c:if test="${kind == 0}">disabled="disabled"</c:if> value="提交" />
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