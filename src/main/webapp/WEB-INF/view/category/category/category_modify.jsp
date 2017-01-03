<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	


	<script>
	
		function f_clickCategoryList(obj, categoryId, categoryName, description, menuName, directory) {
			$("#category-info").show();
			$("#categoryId").val(categoryId);
			$("#categoryName").val(categoryName);
			$("#description").val(description);
			$("#menuName").val(menuName);
			$("#directory").val(directory);
			
			$('.navigation-item').removeClass('select-item');
			$(obj).addClass('select-item');
		}
	
	
		// category 정보 수정
		function f_modifyCategory (url) {
			var formData = form_getFormDataToJson ("category-modify");
			var param = dinplat_getRequestData (formData);
			
			var recursiveUrl = "<%=contextPath%>"+"/main/header-box";
			
			ajax_post_json_recursive (url, param, "view", success_drawHtml, recursiveUrl, null, "header-box", ajax_post_json, success_drawHtml);
			
		}

	</script>	
	

						<div>
						
							
							
							<!-- Category List -->
							<div id="category-list">
								<div id="navigation" class="width-20percent float-left">
									<ul class="navigation" id="navigation">
									<li class="navigation-title">category list</li>
									<c:forEach var="list" items="${categoryList}">
									<li class="navigation-item" id="${list.categoryId}" 
										onClick="javascript:f_clickCategoryList(this, '${list.categoryId}', '${list.categoryName}', '${list.description}', '${list.menuName}', '${list.directory}')">${list.categoryName}</li>
									</c:forEach>
									</ul>
								</div>
							</div>
							
							<!-- Category 상세 내용 -->
							<div id="category-info" class="width-60percent float-right">
								<form id="category-modify" class="group-box width-60percent" name="category-modify">
									<div class="heading" id="category-head">registration items</div>
									<div class="group-box-inner">
										<table cellpadding="5">
											<tr>
												<td>category name</td>
												<td>
													<input type="text" id="categoryId" class="input-box" name="categoryId" value="${categoryId}" hidden>
													<input type="text" id="categoryName" class="input-box" name="categoryName" value="${categoryName}">
												</td>
											</tr>
											<tr>
												<td>description</td>
												<td><textarea rows="5" cols="50" id="description" class="input-box" name="description">${description}</textarea></td>
											</tr>
											<tr>
												<td>menu</td>
												<td><input type="text" id="menuName" class="input-box" name="menuName" value="${menuName}"></td>
											</tr>
											<tr>
												<td>directory</td>
												<td><input type="text" id="directory" class="input-box" name="directory" value="${directory}"></td>
											</tr>
										</table>
									</div>
									<br>
									<div><input type="button" value="modify" class="button" onClick="f_modifyCategory('<%=contextPath%>/category/modify/upd')"></div>
								</form>
							</div>
							
						</div>


