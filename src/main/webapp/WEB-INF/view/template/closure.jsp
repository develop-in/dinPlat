<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>
	<script src="//cdn.jsdelivr.net/bluebird/3.4.5/bluebird.min.js"></script>
	<script>
	
		function outer () {
			var count = 0;
			
			return function inner (x) {
				count += x;
				alert(count);
			}
		}
		
		var clo1 = outer();			// clo1인 closure가 됨. (새로운 객체가 생성되어 clo1에 할당 됨)
		clo1(1);					// 결과 1
		clo1(1);					// 결과 2
		clo1(1);					// 결과 3

		var clo2 = outer();			// clo2인 clouser가 됨. (새로운 객체가 생성되어 clo2에 할당 됨.)
		clo2(1);					// 결과 1
		clo2(1);					// 결과 2
		clo2(1);					// 결과 3
		

	</script>	
	
	</head>
	
	<body>
		
		<div>
			Javascript Closure!!!
		</div>
		
	</body>
</html>

