<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	
<html>
	<head>

	
	<script>

		// 카테고리 등록
		function f_createCategory (url) {
			var formData = form_getFormDataToJson ("category-create");
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
		}
	</script>	
	
	</head>
	
	<body>
						<div>
							<form id="category-create" class="group-box" name="category-create">
								<div class="heading">registration items</div>
								<div class="group-box-inner">
									<table cellpadding="5">
										<tr>
											<td>category name</td>
											<td><input type="text" id="categoryName" class="input-box" name="categoryName"></td>
										</tr>
										<tr>
											<td>description</td>
											<td><textarea rows="5" cols="60" id="description" class="input-box" name="description"></textarea></td>
										</tr>
										<tr>
											<td>menu</td>
											<td><input type="text" id="menuName" class="input-box" name="menuName"></td>
										</tr>
										<tr>
											<td>directory</td>
											<td><input type="text" id="directory" class="input-box" name="directory"></td>
										</tr>
									</table>
								</div>
								<br>
								<div><input type="button" value="create" class="button" onClick="f_createCategory('<%=contextPath%>/category/create/ins')"></div>
							</form>
						</div>

	</body>
</html>

