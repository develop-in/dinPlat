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
			var form = $('xNoteForm')[0];
			var formData = new FormData(form);
			formData.append("uloadFile", $('input[name=fileInput]')[0].files[0]);
			
			console.log("formData : " + JSON.stringify(formData));
			
			var url = "<%=contextPath%>/template/file";
			
			$.ajax({
				url : url,
				data : formData,
				process : false,
				contentType : false,
				type: 	"POST",
				success:function(data){
					console.log(data);
				},
				error:function(jqXHR, textStatus, errorThrown) {
					error_alert(jqXHR, textStatus, errorThrown);
				}
			});
		}

		
		function f_fileUpload () {
			var url = "<%=contextPath%>/template/file/upload";
			
			$("#xNoteForm").ajaxForm({
				url : url,
				enctype : "multipart/form-data",
				success:function(data){
					console.log(data);
				},
				error:function(jqXHR, textStatus, errorThrown) {
					error_alert(jqXHR, textStatus, errorThrown);
				}
			});
			
			$("#xNoteForm").submit();
		}
	</script>	
	


						<div>
							<form id="xNoteForm" class="group-box" name="xNoteForm" method="post" enctype="multipart/form-data">
								
								<input type="file" id="fileName" name="fileName" class="input-box">
								<input type="button" class="button" value="fileUpload" onClick="javascript:f_fileUpload()">
								
							</form>
						</div>




