<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type"  content="text/html; charset=utf-8">
	<title>单个活动详细信息查询</title>
	<link rel="stylesheet" href="CSS/activityDetailSelect.css" type="text/css" />
	<script type="text/javascript" src="javaScript/jquery.minuties.js"></script>
	<script type="text/javascript">
		var tim="<%=session.getAttribute("time")%>";//倒计时总秒数量	
		var endt="<%=session.getAttribute("endtime")%>";//倒计时总秒数量		
		var intDiff = parseInt(tim);//倒计时总秒数量	
		var endti = parseInt(endt);//倒计时总秒数量	
		function timer(intDiff){
			window.setInterval(function(){
			var day=0,
				hour=0,
				minute=0,
				second=0;//时间默认值		
			if(intDiff > 0){
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
			}
			if (minute <= 9) minute = '0' + minute;
			if (second <= 9) second = '0' + second;
			$('#day_show').html(day+"天");
			$('#hour_show').html('<s id="h"></s>'+hour+'时');
			$('#minute_show').html('<s></s>'+minute+'分');
			$('#second_show').html('<s></s>'+second+'秒');
			intDiff--;
			}, 1000);
		} 
		function timer1(intDiff){
			window.setInterval(function(){
			var day=0,
				hour=0,
				minute=0,
				second=0;//时间默认值		
			if(intDiff > 0){
				day = Math.floor(intDiff / (60 * 60 * 24));
				hour = Math.floor(intDiff / (60 * 60)) - (day * 24);
				minute = Math.floor(intDiff / 60) - (day * 24 * 60) - (hour * 60);
				second = Math.floor(intDiff) - (day * 24 * 60 * 60) - (hour * 60 * 60) - (minute * 60);
			}
			if (minute <= 9) minute = '0' + minute;
			if (second <= 9) second = '0' + second;
			$('#day_show1').html(day+"天");
			$('#hour_show1').html('<s id="h"></s>'+hour+'时');
			$('#minute_show1').html('<s></s>'+minute+'分');
			$('#second_show1').html('<s></s>'+second+'秒');
			intDiff--;
			}, 1000);
		}
		
		$(function(){
			timer(intDiff);
			timer1(endti);
		});	
		/* 礼品发放情况 */
		function link() {
			window.location.href="getAllGiftByActid?actId="+ ${activity.actId};/* ${activity.actId} */
		}
		/* 	未收到礼品情况 */
		function link1() {
			window.location.href="getNumByActId?actId="+ ${activity.actId};/* ${activity.actId} */
		}
	</script>
	<style type="text/css">
	
	.button.gray{
	color: green;
	text-shadow:1px 1px 1px #fff;
	border:1px solid #dce1e6;
	box-shadow: 0 1px 2px #fff inset,0 -1px 0 #a8abae inset;
	background: -webkit-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: -moz-linear-gradient(top,#f2f3f7,#e4e8ec);
	background: linear-gradient(top,#f2f3f7,#e4e8ec);
	}
	
	.button{
	width: 110px;
	line-height: 30px;
	text-align: center;
	font-weight: bold;
	color: green;
	text-shadow:1px 1px 1px #333;
	border-radius: 5px;
	margin:0 20px 20px 0;
	position: relative;
	overflow: hidden;
}
	</style>
</head>
<body>
	<div align="left" style="margin-top:48px; border: 1px;">
		<table border="0" width="654" cellspacing="0" cellpadding="0" bgcolor="#FAFCF5" style="padding: 8px;">
		<tr>		
			<td>
			 	<table width="654" border="0" cellspacing="0" cellpadding="0" style="margin-top:5">
            		<tr height="30">
            			<td style="text-indent:5" valign="bottom"><font color="#004790"><b>■活动详细信息</b></font>                      
                        </td>                
            		</tr> 
            		<tr height="30">
            			
                        <td style="text-indent:20; color:#DD553C;font-size:20px; "><center>活动名称：
                        <s:property value="%{activity.actName}" /> </center></td>
                     
                    </tr> 
            		<tr height="30">
            			<td><span style="color:green;">
                        	签到状态统计： &nbsp;&nbsp;总人数：<s:property value="%{totalUserNum}" />
                       	  &nbsp;&nbsp;已签到人数：<s:property value="%{signNumber}" />
                       	  &nbsp;&nbsp;未签到人数：<s:property value="%{unSignNumber}" />
                          &nbsp;&nbsp;迟到人数：<s:property value="%{lateSignNumber}" />
                        </span></td></tr>
                   <tr height="30">
            			<td><span style="color:green;">
                        	活动参加率统计： &nbsp;&nbsp;      
                        <s:property value="%{signPercent}" /></span></td></tr>
                    <tr height="30">
            			<td><span style="color:green;">
                        	礼品状态统计： &nbsp;&nbsp;签到总人数：<s:property value="%{signNumber}" />
                       	  &nbsp;&nbsp;已领礼品数：<s:property value="%{giftNumber}" />
                          &nbsp;&nbsp;未领礼品数：<s:property value="%{unGiftNumber}" /></span>
                
                          </td></tr>            
            		<tr height="1"><td></td></tr>
            	 	<tr height="30">
            			<td>
              			<span style=" margin-left:300px;"> 
                            <input type="button" class="button gray" value="礼品发放情况" style="width: 115px;" onclick="link()">
                        	<input type="button" class="button gray" value="未收到礼品情况" onclick="link1()"></span>
                          </td></tr>            
            		<tr height="1"><td></td></tr>
            		<tr bgcolor="#FAFCF5">
                		<td style="border:1 solid">
                    		<table border="0" width="100%" cellspacing="0" cellpadding="2" style="font-size:14px;">  
                                                
                            <tr height="30">
                                <td style="text-indent:20">活动开始时间：</td>
                                <td><s:property value="%{activity.StartTime}" /></td>                            
                            </tr>
                            <tr height="30">
                                <td style="text-indent:20">活动结束时间：</td>
                                <td><s:property value="%{activity.EndTime}" /></td>
                            </tr>
                            <tr height="30">
                                <td style="text-indent:20">活动开始签到时间：</td>
                                <td><s:property value="%{activity.signStart}" /></td>
                            </tr>
                            <tr height="30">
                                <td style="text-indent:20">活动礼品总数量：</td>
                                <td><s:property value="%{activity.actGiftNumber}" /></td>
                            </tr>
                            <tr height="30">
                            	<td colspan="2" style="text-indent:20">活动内容：</td>
                            </tr>
                            <tr height="60">
                            	<td style="text-indent:20;"><s:property value="%{activity.actContent}" /></td>
                            </tr>
                            <c:if test="${time!=0}">
                             <tr height="60">
                            	<td colspan="2" style="text-indent:20">活动开始倒计时：</td>
                            	<td colspan="2" >
                            		<div class="time-item" style="	margin-left:-50px;">
										<span id="day_show">0天</span>
										<strong id="hour_show">0时</strong>
										<strong id="minute_show">0分</strong>
										<strong id="second_show">0秒</strong>
									</div>
								</td>
                            </tr>  
                            </c:if>
							<c:if test="${signtime > 0}">
                            <tr height="60">
                            	<td colspan="2" style="text-indent:20">活动状态：</td>
                            	<td colspan="2" >
                            		<div class="time-item" style="	margin-left:-50px;">
										<span id="day" style="float:left;line-height:49px;color:#7a15bd;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;"	
										>活动签到时间未到!</span>								
									</div>
								</td>
                            </tr> 
                            </c:if>
                            <c:if test="${signtime == 0}">
	                            <c:if test="${time > 0}">
	                            <tr height="60">
	                            	<td colspan="2" style="text-indent:20">活动签到状态：</td>
	                            	<td colspan="2" >
	                            		<div class="time-item" style="	margin-left:-50px;">
											<span id="day" style="float:left;line-height:49px;color:#7a15bd;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;"	
											>活动正在签到中!</span>								
										</div>
									</td>
	                            </tr> 
	                            </c:if>
                            </c:if>
                      
                          <c:if test="${time==0}">
                            <tr height="60">
                            	<td colspan="2" style="text-indent:20">活动签到状态：</td>
                            	<td colspan="2" >
                            		<div class="time-item" style="	margin-left:-50px;">
										<span id="day" style="float:left;line-height:49px;color:#7a15bd;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;"	
										>活动签到已结束!</span>								
									</div>
								</td>
                            </tr> 
                          </c:if>
                          <c:if test="${time == 0}">
								<c:if test="${endtime > 0}">
	                            <tr height="60">
	                            	<td colspan="2" style="text-indent:20">活动状态：</td>
	                            	<td colspan="2" >
	                            		<div class="time-item" style="	margin-left:-50px;">
											<span id="day" style="float:left;line-height:49px;color:#7a15bd;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;"	
											>活动正在进行中!</span>								
										</div>
									</td>
	                            </tr>       						
								</c:if>
							</c:if>
							<c:if test="${time == 0}">
								<c:if test="${endtime > 0}">
                             <tr height="60">
                            	<td colspan="2" style="text-indent:20">活动结束倒计时：</td>
                            	<td colspan="2" >
                            		<div class="time-item" style="	margin-left:-50px;">
										<span id="day_show1">0天</span>
										<strong id="hour_show1">0时</strong>
										<strong id="minute_show1">0分</strong>
										<strong id="second_show1">0秒</strong>
									</div>
								</td>
                            </tr>  
                            </c:if>
                          </c:if>
							<c:if test="${endtime==0}">
                            <tr height="60">
                            	<td colspan="2" style="text-indent:20">活动状态：</td>
                            	<td colspan="2" >
                            		<div class="time-item" style="	margin-left:-50px;">
										<span id="day" style="float:left;line-height:49px;color:#7a15bd;font-size:32px;margin:0 10px;font-family:Arial, Helvetica, sans-serif;"	
										>活动已经结束!</span>								
									</div>
								</td>
                            </tr>       
							</c:if>
							
                            <tr height="40">
                            	<td  colspan="2"></td>             
                            	<td align="right"><a style="text-decoration:none; color:blue; margin-right: 30px; font-size: 20px;" href="javascript:window.history.back(-1)">返回</a></td>                            	
                            </tr>                              
                    		</table> 
                		</td>
            		</tr>
        		</table>
        		</td>
			</tr>
		</table>
	</div>
</body>
</html>