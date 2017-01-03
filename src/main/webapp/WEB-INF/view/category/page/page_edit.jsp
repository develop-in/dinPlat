<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	

	<script>
	
		function f_modifyPage (url) {
			var formData = form_getFormDataToJson ("page-modify");
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
		}
	
	</script>	
	


						<div>
							<form id="page-modify" class="group-box" name="page-modify">
								
								<!-- task -->
								<div class="heading">registration items</div>
								<div class="group-box-inner">
									<table cellpadding="5">
										<tr>
											<td>page name</td>
											<td>
												<input type="text" id="pageName" class="input-box" name="pageName"  value="${pageName}">
												<input type="text" id="taskId" class="input-box" name="taskId" value="${taskId}" hidden>
												<input type="text" id="pageId" class="input-box" name="pageId" value="${pageId}" hidden>
											</td>
										</tr>
										<tr>
											<td>description</td>
											<td><textarea rows="10" cols="70" id="description" class="input-box" name="description">${description}</textarea></td>
										</tr>
										<tr>
											<td>URL</td>
											<td><input type="text" id="linkUrl" class="input-box" name="linkUrl" value="${linkUrl}"></td>
										</tr>
									</table>
								</div>
								<br>
								<div><input type="button" value="modify" class="button" onClick="javascript:f_modifyPage('<%=contextPath%>/page/newPage/upd')"></div>
							</form>
						</div>



