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
		function isCon(content, giftNum, actId) {
	//		if(isOk && content.value.length>0 && giftNum.value.length>0 && actId.value.length>0){
		//		return true;
		//	}else 
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
			添加补发礼品
		</div>		
		<div id="main">
			<div class="cl">&nbsp;</div>
			<div id="content">		
				<div class="box">
					<div class="box-head">
						<h4>添加礼品</h4>
					</div>				
					<form action="addGiftTwo.action" method="post" onsubmit="return isCon(content, giftNum, actId)">
						<div class="form">
								<p class="inline-field">
									<label>请选择该礼品为哪个活动所用<span>(Required Field)</span></label>
									<select class="field size2" name="gift.giftAct.actId" id="actId" style="width:250px; height:31px;">
										<c:forEach items="${list}" var="act">
	  	 									<option value="${act.actId}">${act.actName}</option>
	   									</c:forEach>									  	 									   			 			
									</select>					
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
									<label>礼品数量<span>(Required Field)&nbsp;数量为未领礼品的会员数量</span></label>
									<input type="text" style="width:250px; height: 25px;" id="giftNum" value="数量为未领礼品的会员数量"
										onblur = "checkNum(giftNum) "
                						/>
									<span id="check2" style="display: none;color: green;font-size:15px;">您输入的数量不合法，请输入一个自然数</span>
									<span id="check4" style="display: none;color: green;font-size:15px;">请输入礼品的数量！</span>
								</p>
																			
						</div>
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