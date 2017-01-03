<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	

	<script>
	
		/** 카테고리 목록 보여주기. */
		function f_categoryList() {
			var left = $('#category-button').position().left;
			var top = $('#category-button').position().top;

			$('#category-list').toggle();						// hide(), show() 복합기능
			$(".popup-menu").css("top", top+35);
			$(".popup-menu").css("left", left);
			
		}
		
		/** 카테고리 선택 */
		function f_clickCategoryList(categoryId, categoryName) {
			
			$("#category-button").val(categoryName+" ▼");
			$("#categoryId").val(categoryId);
			$('#categoryName').val(categoryName);
			$('#category-list').toggle();
		}
		
		/** TASK 등록 */
		function f_createTask(url) {
			var formData = form_getFormDataToJson ("task-create");
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
		}
	</script>	
	


						<div>
							<form id="task-create" class="group-box" name="task-create">
								
								<!-- category -->
								<div class="heading">category</div>
								<div class="group-box-inner">
									<input type="button" value="category ▼" id="category-button" class="button" onClick="javascript:f_categoryList()">
									<input type="text" id="categoryId" name="categoryId" hidden>
									<input type="text" id="categoryName" name="categoryName" hidden>
								</div>
								
								<br><br>
								
								<div id="category-list" class="popup-menu-box" hidden>
									<div class="popup-menu">
										<c:forEach var="list" items="${categoryList}">
										<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
											onClick="javascript:f_clickCategoryList('${list.categoryId}', '${list.categoryName}')">${list.categoryName}</li>
										</c:forEach>
										</ul>
									</div>
								</div>
								
								<!-- task -->
								<div class="heading">registration items</div>
								<div class="group-box-inner">
									<table cellpadding="5">
										<tr>
											<td>task name</td>
											<td><input type="text" id="taskName" class="input-box" name="taskName"></td>
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
								<div><input type="button" value="create" class="button" onClick="javascript:f_createTask('<%=contextPath%>/task/create/ins')"></div>
							</form>
						</div>



