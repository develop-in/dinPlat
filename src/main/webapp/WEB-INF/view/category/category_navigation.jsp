<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>

	<script>

		// navigation menu 선택시 처리
		function f_clickNavigation(obj, url, menuId) {
			$(".navigation-item").removeClass('selected');
			$(obj).addClass('selected');
			
			var param = "menuId="+menuId;
			ajax_post(url, param, "body", success_drawHtml);
		}
	
		
		// pagehead 선택시 처리
		function f_clickPagehead(url, menuId, page, obj) {
			$(".pagehead-item").removeClass('selected');
			$(obj).addClass('selected');

			f_callViewPage(url, menuId, page);
		}
		function f_callViewPage (url, menuId, page) {
			url += menuId + "/" + page;
			
			var param = "menuId="+menuId+"&page="+page;
			ajax_post(url, param, "view", success_drawHtml);
		}
	</script>	
	
	</head>
	
	<body>

		
					<!-- Navigation -->
					<div class="div-5px-gap width-20percent" id="navigation">
						<ul class="navigation" id="navigation">
							<li class="navigation-title">project</li>
							<li class='navigation-item <c:if test="${menuId eq 'category'}"> selected</c:if>'
								onClick="javascript:f_clickNavigation(this, '<%=contextPath%>/category/navigation', 'category')">category</li>
							<li class='navigation-item <c:if test="${menuId eq 'task'}"> selected</c:if>'
								onClick="javascript:f_clickNavigation(this, '<%=contextPath%>/category/navigation', 'task')">task</li>
						</ul>
					</div>

					
					<!-- Contents -->
					<div class="div-5px-gap width-80percent" id="contents">
					
						<div class="pagehead" id="pagehead">
							<div id="create" class="pagehead-item float-left selected" onClick="javascript:f_clickPagehead('<%=contextPath%>/', '${menuId}', 'create', this)">create</div>
							<div id="modify" class="pagehead-item float-left" onClick="javascript:f_clickPagehead('<%=contextPath%>/', '${menuId}', 'modify', this)">modify</div>
						</div>
						
											
						<!-- View -->
						<div class="div-5px-gap view" id="view">
							<script>
								f_callViewPage('<%=contextPath%>/', '${menuId}', 'create', null);
							</script>
						</div>
					</div>
			
				


		</div>
			

	</body>
</html>

