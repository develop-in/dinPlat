<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>
	<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/frame.css"/>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>

	<script>
		// 헤더의 메뉴박스 지우기
		$(document).ready(function() {
			$(document).click(function() {
				$("#popup-menu").hide();
			});		
		});
		
		// 헤더의 메뉴박스 보여주기
		function f_menupopup() {
			$("#popup-menu").show();
		}

		// navigation menu 선택시 처리
		function f_clickNavigation(obj) {
			$(".navigation-item").removeClass('selected');
			$(obj).addClass('selected');
		}
		
		// pageheade 선택시 처리
		function f_clickPagehead(obj) {
			$(".pagehead-item").removeClass('selected');
			$(obj).addClass('selected');
		}
	</script>	
	
	</head>
	
	<body>

		<!-- Main Container -->
		<div id="page">
			
			
			<!-- Header -->
			<div class="header" id="head">
				<div class="header-box">
					<img class="header-logo" src="<%=contextPath%>/images/dinPlat.png" style="width:40px;height:40px">	
					
					<ul class="header-menu float-left">
						<li class="float-left"><a class="header-menu-item">HTML5</a></li>
						<li class="float-left"><a class="header-menu-item">Javascript</a></li>
						<li class="float-left"><a class="header-menu-item">Angular JS</a></li>
					</ul>
					
					<ul class="header-menu float-right">
						<li class="float-right"><a class="header-menu-item plus-sign" href="javascript:f_menupopup()">+</a></li>
					</ul>
					
					<div class="popoup-menu-box" id="popup-menu" hidden>
						<ul class="popup-menu">
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'">category</li>
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'">task</li>
							<div class="popup-menu-divider"></div>
							<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"></li>
						</ul>
					</div>
				</div>
				
			</div>
			<br>
			
			
			<!-- Body -->
			<div class="body" id="body">
			
		
					<!-- Navigation -->
					<div class="div-5px-gap width-20percent" id="navigation">
						<ul class="navigation" id="navigation">
							<li class="navigation-title">HTML5</li>
							<li class="navigation-item" onClick="javascript:f_clickNavigation(this)">websocket</li>
							<li class="navigation-item" onClick="javascript:f_clickNavigation(this)">map</li>
							<li class="navigation-item" onClick="javascript:f_clickNavigation(this)">page controll</li>
						</ul>
					</div>
					
					
					<!-- Contents -->
					<div class="div-5px-gap width-80percent" id="contents">
						<div class="pagehead">
							<div class="pagehead-item float-left selected" onClick="javascript:f_clickPagehead(this)">feature</div>
							<div class="pagehead-item float-left" onClick="javascript:f_clickPagehead(this)">result1</div>
							<div class="pagehead-item float-left" onClick="javascript:f_clickPagehead(this)">result2</div>
						</div>
						
						
						<!-- View -->
						<div class="div-5px-gap view" id="view">

						</div>
					</div>
			
				</div>
				


		</div>
			

	</body>
</html>

