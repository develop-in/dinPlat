<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	

	<script>
	
		/** PAGE 등록 */
		function f_createPage(url, taskId) {
			var formData = form_getFormDataToJson ("page-create");
			var param = dinplat_getRequestData (formData);
			
			var recursiveParam = "taskId="+taskId;
			var recursiveUrl = "<%=contextPath%>"+"/contents";
			
			// ajax_post_recursive (url, param, "pagehead", success_drawHtml, recursiveUrl, param, "view", ajax_post, success_drawHtml);
			
			ajax_post_json_recursive (url, param, "view", success_drawHtml, recursiveUrl, recursiveParam, "pageHead", ajax_post, success_drawHtml);
		}
	
	</script>	
	


						<div>
							<form id="page-create" class="group-box" name="page-create">
								
								<!-- task -->
								<div class="heading">registration items</div>
								<div class="group-box-inner">
									<table cellpadding="5">
										<tr>
											<td>page name</td>
											<td>
												<input type="text" id="pageName" class="input-box" name="pageName">
												<input type="text" id="taskId" class="input-box" name="taskId" value="${taskId}" hidden>
											</td>
										</tr>
										<tr>
											<td>description</td>
											<td><textarea rows="10" cols="70" id="description" class="input-box" name="description"></textarea></td>
										</tr>
										<tr>
											<td>URL</td>
											<td><input type="text" id="linkUrl" class="input-box" name="linkUrl"></td>
										</tr>
									</table>
								</div>
								<br>
								<div><input type="button" value="create" class="button" onClick="javascript:f_createPage('<%=contextPath%>/page/newPage/ins', '${taskId}')"></div>
							</form>
						</div>



