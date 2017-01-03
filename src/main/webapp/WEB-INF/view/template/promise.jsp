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
	
		var _promise = function (param) {
			return new Promise(function (resolve, reject) {
				
				window.setTimeout (function () {
					if (param) {
						resolve ("해결 완료.");
					} else {
						reject (Error("실패!"));
					}
				}, 3000);
			});
		};
		
		_promise(true)
		.then 
		( 
				function (text) {
					alert("[first promise] suceess : " + text);
					JSON.parse(text);
				},
				function (error) {
					alert("[first promise] fail : " + error);
				}
		)
		.then 
		( 
				function () { alert (" second then. "); }
		)
		.catch 
		(
				function (error) { alert ("catch : " + error); }
		)
		.then 
		(
				function (text) {
					alert("[second promise] suceess : " + text);
				},
				function (error) {
					alert("[second promise] fail : " + error);
				}
		)
		.then ( 
				function () { alert (" last then. "); }
		);

	</script>	
	
	</head>
	
	<body>
		
		<div>
			Javascript Promise!!!
		</div>
		
	</body>
</html>

