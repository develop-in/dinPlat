<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
	<script>
		$(function() {
			$("#logo").click(function () {
				var left = $('#logo').position().left;
				var top = $('#logo').position().top;

				$('#popup-template').toggle();						
				$("#popup-menu-template").css("top", top+40);
				$("#popup-menu-template").css("left", left);		
			});
		});
		
		function f_clickTemplateMenu(url) {
			$('#popup-template').hide();
			ajax_post (url, null, "body", success_drawHtml);
		}
	</script>	
	
					<img class="header-logo" src="<%=contextPath%>/static/images/dinPlat.png" style="width:40px;height:40px" id="logo">	
					
					<ul class="header-menu float-left">
					<c:forEach var="list" items="${categoryList}">
						<li class="float-left"><a class="header-menu-item" href="javascript:f_clickHeadMenu('<%=contextPath%>/navigation','${list.categoryId}','${list.menuName}')">${list.menuName}</a></li>
					</c:forEach>
					</ul>
					
					<ul class="header-menu float-right">
						<li class="float-right"><a class="header-menu-item plus-sign" href="javascript:f_menupopup()">+</a></li>
					</ul>
			
			
					<div class="popoup-menu-box" id="popup-template" hidden>
						<ul class="popup-menu" id="popup-menu-template">
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
									onClick="javascript:f_clickTemplateMenu('<%=contextPath%>/template/xNote')">xNote</li>
							<div class="popup-menu-divider"></div>
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"></li>
						</ul>
					</div>

