<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	


	<script>

		// navigation menu 선택시 처리
		function f_clickNavigation(obj, url, taskId) {
			$(".navigation-item").removeClass('selected');
			$(obj).addClass('selected');
			
			var param = "taskId="+taskId;
			
			var recursiveUrl = "<%=contextPath%>"+"/task/feature";
			
			ajax_post_recursive (url, param, "pagehead", success_drawHtml, recursiveUrl, param, "view", ajax_post, success_drawHtml);
			
			$("#view-inner").show();
		}
	

	</script>	
	


		
					<!-- Navigation -->
					<div class="div-5px-gap width-20percent" id="navigation">
						<ul class="navigation" id="navigation">
							<li class="navigation-title">${menuName}</li>
							<c:forEach var="list" items="${taskList}">
							<li class="navigation-item" onClick="javascript:f_clickNavigation(this, '<%=contextPath%>/contents', '${list.taskId}')">${list.taskName}</li>
							</c:forEach>
						</ul>
					</div>
					
					
					<!-- Contents -->
					<div class="div-5px-gap width-80percent" id="contents">
						<div class="pagehead" id="pagehead">

						</div>
						
						
						<!-- View -->
						<div class="div-5px-gap view" id="view">
							
							
							
						</div>
					</div>
					
