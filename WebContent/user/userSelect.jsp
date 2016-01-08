<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>首页左下面的框架页面</title>
	<link rel="stylesheet" href="${pageScope.basePath}CSS/index.css" type="text/css" media="all" />
	<script type="text/javascript">
		function del(uid){
			if(confirm("是否确认删除?")){
				window.location.href = "userDelete.action?userId=" + uid;
			}
		}
	</script>
</head>
<body>
<!-- Container -->
<div id="container"  style="margin-top:48px;">
	<div id="shell" style="width:680px;">
	<!-- Small Nav -->
		<div class="small-nav" >
			<a href="#">当前页面</a>
			<span>&gt;</span>
			用户管理
		</div>
		
		<div id="main">
			<div class="cl">&nbsp;</div>
			
			<!-- Content -->
			<div id="content">
				
				<!-- Box -->
				<div class="box">
					<!-- Box Head -->
					<div class="box-head">
						<h2 class="left">会员管理</h2>
						<div class="right">
						<form action="userSelect.action">
							<label>搜索会员</label>
							<input type="text" class="field small-field" name="userId" />
							<input type="submit" class="button" value="搜索" />
						</form>
						</div>
					</div>
					<!-- End Box Head -->	

					<!-- Table -->
					<div class="table">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<th width="10%">序号</th>
								<th width="15%">工号</th>
								<th width="15%">用户权限</th>
								<th width="22%">电话</th>
								<th width="15%">真实姓名</th>
								<th width="43%" class="ac">操作</th>
							</tr>
							<s:if test="#list!=null">
								<tr class="odd"><td colspan="6" align="center">对不起，没找到您所要查询的结果。</td></tr>
							</s:if>
							<s:iterator value="%{list}" var="user">
							<tr class="odd">
								<td><s:property value="#user.userId"/></td>
								<td><h3><a href="userDetail.action?userId=<s:property value="#user.userId" />"><s:property value="#user.userName"/></a></h3></td>
								<td><s:property value="#user.userType.typeName"/></td>
								<td><s:property value="#user.userPhone"/></td>
								<td><s:property value="#user.userTrueName"/></td>
								<td><a href="updatePrepare.action?userId=<s:property value="#user.userId"/>" class="ico del">修改&nbsp;</a><a href="javascript:del(<s:property value="#user.userId"/>);" class="ico del">删除</a><a href="activityList.action?userId=<s:property value="#user.userId"/>" class="ico edit">签到</a></td>
							</tr>
							</s:iterator>
						</table>
						<!-- Pagging -->
						<div class="pagging">
							<form id="selectform" action="userSelect.action">
							<div class="left">当前:第<s:property value="%{pageDivided.currentPage}"/>页&nbsp;&nbsp;总共<s:property value="%{pageDivided.totalPage}"/>页 每页<input name="end" type="text" value="${pageDivided.end}" size="1">行   <input type="submit" class="button" value="确定" /></div>
							</form>
							<div class="right">
								<a href="userSelect.action?pageDivided.currentPage=1">首页</a>
								<a href="userSelect.action?pageDivided.currentPage=%{pageDivided.currentPage-1}">上一页</a>
								<a href="userSelect.action?pageDivided.currentPage=1">1</a>
								<s:if test="%{pageDivided.totalPage>=2}">
								<a href="userSelect.action?pageDivided.currentPage=2">2</a>
								<s:if test="%{pageDivided.totalPage>=3}"><a href="userSelect.action?pageDivided.currentPage=3">3</a></s:if>
								</s:if>
								<s:if test="%{pageDivided.totalPage>3}">
								<span>...</span>
								</s:if>
								<a href="<s:if test = "%{pageDivided.currentPage != pageDivided.totalPage}">userSelect.action?pageDivided.currentPage=${pageDivided.currentPage+1}</s:if>">下一页</a>
								<a href="userSelect.action?pageDivided.currentPage=${pageDivided.totalPage}">尾页</a>
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
