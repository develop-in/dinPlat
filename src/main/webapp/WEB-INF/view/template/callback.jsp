<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script src="js/jquery.cookie.js"></script>
	<script src="js/user/signal.js"></script>

	<script>
	
		$(document).ready(function() {
			$('#test1').off('click').on('click', function () {
		  	testCall(function back() {
		  		alert('callback입니다.');
		  	});
		  });
			
		  $('#test2').off('click').on('click', function () {
		  	testCall(function back(data) {
		  		alert('결과를 받는 callbck(), 결과 : ' + data);
		  	});
		  });
		});
	
		function testCall(callback) {
			alert('시간 차를 위해 알림창 생성');
		  var data = 1111;
		  callback(data);
		}

	</script>	
	
	</head>
	
	<body>
		
		<div>
		  <button id="test1">
		    callback입니다.
		  </button>
		</div>
		<div>
		  <button id="test2">
		    결과를 받는 callbck()
		  </button>
		</div>
		
	</body>
</html>

