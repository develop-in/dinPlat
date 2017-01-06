<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>
	
	<title>WEB Platform</title>
	
	<!-- 모바일에서도 정상적으로 보여주기 위한 viewport 설정 (모든 브라우저에서 정상적으로 보이게 설정) -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
	<style type="text/css">
		@-ms-viewport{width:device-width;}
		@-o-viewport{width:devie-width;}
		@viewport{width:device-width;}
	</style>
	
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/static/css/frame.css"/>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="<%=contextPath%>/static/js/user/dinplat.js"></script>
	<script type="text/javascript" src="//apis.daum.net/maps/maps3.js?apikey=1a7e3de62f89b96f4ab563ff0da3245a"></script>
	

	<script>
		// 헤더의 메뉴박스 지우기
		$(document).ready(function() {
			$(document).click(function() {
				$("#popup-menu").hide();
			});		
		});
		
		// 헤더의 메뉴박스 보여주기
		function f_menupopup() {
			var left = $(".float-right").offset().left;
			var top = $(".float-right").offset().top;

			$("#popup-menu").show();
			$(".popup-menu").css("top", top+20);
			$(".popup-menu").css("left", left-20);
		}

		// click Header menu
		function f_clickHeadMenu(url, categoryId, menuName) {
			var param = "categoryId=" + categoryId + "&menuName="+menuName;
			ajax_post(url, param, "body", success_drawHtml);
		}
		
		// click Popup menu
		function f_clickPopupMenu(url, value) {
			var param = "menuId=" + value;
			ajax_post(url, param, "body", success_drawHtml);
		}
	</script>	
	
	</head>
	
	<body>

		<!-- Main Container -->
		<div id="page">
			
			
			<!-- Header -->
			<div class="header" id="head">
				<div class="header-box" id="header-box">
					<jsp:include page="header-box.jsp" flush="false"/>
				</div>
				
				<div class="popoup-menu-box" id="popup-menu" hidden>
						<ul class="popup-menu">
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
									onClick="javascript:f_clickPopupMenu('<%=contextPath%>/category/navigation','category')">category</li>
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
									onClick="javascript:f_clickPopupMenu('<%=contextPath%>/category/navigation','task')">task</li>
							<div class="popup-menu-divider"></div>
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"></li>
						</ul>
				</div>
				
			</div>
			<br>
			
			
			<!-- Body -->
			<div class="body" id="body">
			
		
					<!-- Navigation -->
					<div class="intro width-100percent" id="intro">
						<div class="blankslater blankslate-spacious">
							<ul class="blankslate_ul">
								<li><img src="<%=contextPath%>/static/images/intro.png" style="width:100px;height:100px"></li>
								<li>The dinplat site is a site that implements the current SW development method as a mini project and shows the results. </li>
								<li>I hope the mini project will be used for a better future.</li>
							</ul>
						</div>
					</div>
			
				</div>
				


		</div>
			

	</body>
</html>

