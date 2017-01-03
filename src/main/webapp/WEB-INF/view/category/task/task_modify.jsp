<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>


	<script>
	
		// Task List 가져오기
		function f_clickCategoryList(url, obj, categoryId) {
		
			$('.navigation-item').removeClass('select-item');
			$(obj).addClass('select-item');
			
			var jsonData = {};
			jsonData["categoryId"] = categoryId;
			var param = dinplat_getRequestData (jsonData);
			
			ajax_post_json (url, param, "0000", f_taskList_callback);
		}
	
		// Task List가져와서 Data를 담아 두는 function (callback)
		function f_taskList_callback (key, taskList) {
			var jsonData = JSON.parse(taskList);
			
			var html="";
			var preHtml = "<li class=\"popup-menu-item\" onmouseover=\"this.className='mouse over'\" onmouseout=\"this.className='mouse out'\" onClick=\"javascript:f_clickTaskList(";
			var postHtml = "</li>";
			
			for (var i=0; i<jsonData.data.length; i++) {
				var task = "'" + jsonData.data[i].taskId + "', '" + jsonData.data[i].taskName + "', '" + jsonData.data[i].description + "', '" + jsonData.data[i].menuName + "', '" + jsonData.data[i].directory + 
						   "', '" + jsonData.data[i].categoryId + "')\">" + jsonData.data[i].taskName;
				html = html + preHtml + task + postHtml;
			}
			
			$("#taks-list-menu").empty();
			$("#taks-list-menu").append(html);

			// 화면 클리어
			$("#task-button").val("task ▼");
			$("#taskId").val("");
			$("#taskName").val("");
			$("#description").val("");
			$("#menuName").val("");
			$("#directory").val("");
			$("#categorId").val("");
		}
	
		// Task List 보여주기
		function f_taskList() {
			var left = $('#task-button').position().left;
			var top = $('#task-button').position().top;

			$('#task-list').toggle();						// hide(), show() 복합기능
			$(".popup-menu").css("top", top+35);
			$(".popup-menu").css("left", left);
			
		}
		
		// 화면에 데이터 보여주기
		function f_clickTaskList (taskId, taskName, description, menuName, directory, categoryId) {
			$("#task-button").val(taskName + " ▼");
			$("#taskId").val(taskId);
			$("#taskName").val(taskName);
			$("#description").val(description);
			$("#menuName").val(menuName);
			$("#directory").val(directory);
			$("#categoryId").val(categoryId);
			
			$('#task-list').toggle();
		}
		
		// category 정보 수정
		function f_modifyTask (url) {
			var formData = form_getFormDataToJson ("task-modify");
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
			
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
										onClick="javascript:f_clickCategoryList('<%=contextPath%>/task/modify/taskList', this, '${list.categoryId}')">${list.categoryName}</li>
									</c:forEach>
									</ul>
								</div>
							</div>
							
							<!-- TASK 상세 내용 -->
							<div id="task-info" class="width-60percent float-right">
								<form id="task-modify" class="group-box width-60percent" name="task-modify">
									<div class="heading" id="category-head">registration items</div>
									<div class="group-box-inner">
									
										<!-- category -->
										<input type="button" value="task ▼" id="task-button" class="button" onClick="javascript:f_taskList()">
										<input type="text" id="taskId" name="taskId" hidden>
										
										<br><br>
										
										<div id="task-list" class="popup-menu-box" hidden>
											<div class="popup-menu" id="taks-list-menu">

											</div>
										</div>
	
										<table cellpadding="5">
											<tr>
												<td>task Name</td>
												<td><input type="text" id="taskName" class="input-box" name="taskName" value="${taskName}"></td>
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
												<td>
													<input type="text" id="directory" class="input-box" name="directory" value="${directory}">
													<input type="text" id="categoryId" class="input-box" name="categoryId" value="${categoryId}" hidden>
												</td>
											</tr>
										</table>
									</div>
									<br>
									<div><input type="button" value="modify" class="button" onClick="f_modifyTask('<%=contextPath%>/task/modify/upd')"></div>
								</form>
							</div>
							
						</div>


