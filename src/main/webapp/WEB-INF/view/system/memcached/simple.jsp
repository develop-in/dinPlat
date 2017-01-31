<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
%>

<script>

	function f_sendData () {
		var url = "<%=contextPath%>/system/memcached/simple";
		
		var param = form_getFormData ("memForm");
		console.log("param : " + param);
	
		ajax_post (url, param, null, result_memcached);
	}
	
	function result_memcached (key, data) {
		$("#memcached-result").empty();
		$("#memcached-result").append(data);
	}
	
	function f_startMemcached() {
		
	}
</script>

	<form name="memForm">
	
	<table>
		<tr>
			<td>[ Key ]</td>
		</tr>
		<tr>
			<td colspan="2">value : <input type="text" class="input-box" id="key" name="key"></td>
		</tr>
		<tr>
			<td>[ Key Map ]</td>
		</tr>
		<tr>
			<td>name : <input type="text" class="input-box" id="name" name="name"></td>
			<td>age : <input type="text" class="input-box" id="age" name="age"></td>
		</tr>
		<tr>
			<td>[ result ]</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="memcached-result"></div>
			</td>
		</tr>
		<tr><td colspan="2">&nbsp;</td></tr>
		<tr>
			<td colspan="2">
				<input type="button" class="button" value="caching" onClick="javascripg:f_sendData()">
			</td>
		</tr>
	</table>
			
	</form>					
								
								



