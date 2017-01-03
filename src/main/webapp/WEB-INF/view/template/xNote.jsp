<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
	<script src="<%=contextPath%>/static/js/jquery.form.js"></script>
	<script>
	
		function f_f () {
			var url = "<%=contextPath%>/template/file/upload";
			
			$("#hiddenForm").ajaxForm({
				url : url,
				enctype : "multipart/form-data",
				success:function(data){
					console.log(data);
				},
				error:function(jqXHR, textStatus, errorThrown) {
					error_alert(jqXHR, textStatus, errorThrown);
				}
			});
			
			$("#hiddenForm").submit();
		}

		
		function f_fileUpload () {
			

			var url = "<%=contextPath%>/template/file/upload";
			
			$("#innerForm").ajaxForm({
				url : url,
				enctype : "multipart/form-data",
				success:function(data){
					console.log(data);
				},
				error:function(jqXHR, textStatus, errorThrown) {
					error_alert(jqXHR, textStatus, errorThrown);
				}
			});
			
			$("#innerForm").submit();
		}
		
		
		function f_click() {
			
			var ext = new Object();
			
			$.extend(ext, $("#fileName"));
			$("#hiddenForm").append(ext);

			$("#hiddenFile").show();
		}
	</script>	
	


						<div>
							<form id="innerForm" class="group-box" name="innerForm" method="post" enctype="multipart/form-data">
								<input type="text" id="age" name="age" class="input-box">
								<input type="file" id="fileName" name="fileName" class="input-box">
								<input type="file" id="hiddenFile" name="hiddenFile" class="input-box" hidden>
								<input type="button" class="button" value="fileUpload" onClick="javascript:f_fileUpload()">
							</form>

							<form id="hiddenForm" name="hiddenForm" method="post" enctype="multipart/form-data">
								<input type="button" class="button" value="fileUpload" onClick="javascript:f_f()">
								<input type="button" class="button" value="copy" onClick="javascript:f_click()">
							</form>
						</div>





