<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
    String contextPath = request.getContextPath();
%>

	<script src="<%=contextPath%>/static/js/jquery.form.js"></script>
	<script>
		var buttonObject = new Object(); 
		
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
		function f_saveTask(url, taskId) {
			var formData = form_getFormDataToJson ("task-modify");
			formData["taskId"] = taskId;
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
		}
		
		/** 레퍼런스 타입 보여주기 */
		function f_selectButton (obj) {
			var left = $(obj).position().left;
			var top = $(obj).position().top;

			$('#referenceTypeList').toggle();						// hide(), show() 복합기능
			$(".popup-menu").css("top", top+35);
			$(".popup-menu").css("left", left);
			
			buttonObject = obj;
		}
		
		/** 레퍼런스 타입 선택 */
		function f_clickReferenceList (referenceType, referenceName) {
			
			$(buttonObject).val(referenceName);
			
			$('#referenceTypeList').toggle();
			
			if (referenceType == '2') {
				$(buttonObject).parent().parent().find('#uploadFile').show();
				$(buttonObject).parent().parent().find('#uploadButton').addClass('button');		// button class를 최초에 적용하면 hidden이 먹지 않아 여기서 class를 추가함.
				$(buttonObject).parent().parent().find('#uploadButton').show();
			} else {
				$(buttonObject).parent().parent().find('#uploadFile').hide();
				$(buttonObject).parent().parent().find('#uploadButton').hide();
			}
			
			$(buttonObject).parent().find("#referenceType").val(referenceType);
		}
		
		/** feature 저장 */
		function f_saveFeature (url, taskId) {
			var formData = form_getFormDataToJson("feature-modify");
			formData["taskId"] = taskId;
			var param = dinplat_getRequestData (formData);
			
			ajax_post_json (url, param, "view", success_drawHtml);
		}
		
		/** 레퍼너스 추가하기 */
		function f_addReference () {
			var html = $("#hiddenReference").html();
			
			$("#referenceList").append(html);
		}
		
		/** 레퍼런스 삭제하기 */
		function f_removeReference (obj) {
			$(obj).parent().parent().remove();
		}
		
		/** File Upload */
		function f_fileUpload(obj, taskId) {
			var parentObj = $(obj).parent();
			var grandObj = $(obj).parent().parent();
			var fileName = $(obj).parent().find("#uploadFile").val();
			
			if (fileName == '') {
				alert ("Upload 파일을 먼저 선택하시기 바랍니다.");
				return;
			}
			
			// upload중 메세지
			$(grandObj).find("#contents").val("upload중...")
			
			// File object를 fileUploadForm으로 move
			var ext = new Object();
			$.extend(ext, $(obj).parent().find("#uploadFile"));
			$("#fileUploadForm").empty();
			$("#fileUploadForm").append(ext);
			
			// Key값 fileUplaodForm으로 copy
			var taskId = $(grandObj).find("#taskId").val();
			var referenceId = $(grandObj).find("#referenceId").val();
			var taskObj = "<input type='text' id='taskId' name='taskId' value='" + taskId + "'>";
			var referenceObj = "<input type='text' id='referenceId' name='referenceId' value='" + referenceId + "'>";
			$("#fileUploadForm").append(taskObj);
			$("#fileUploadForm").append(referenceObj);
			
			
			// 화면 정리
			$(grandObj).find("#divider").remove();
			var html = $("#hiddenFileButton").html();
			$(grandObj).append(html);
			html = "<div class='popup-menu-divider' id='divider'></div>";
			$(grandObj).append(html);
			$(parentObj).remove();

			
			// File Upload
			var url = "<%=contextPath%>/task/file/upload";

			$("#fileUploadForm").ajaxForm({
				url : url,
				enctype : "multipart/form-data",
				success:function(data){
					$(grandObj).find("#contents").val(data);
				},
				error:function(jqXHR, textStatus, errorThrown) {
					error_alert(jqXHR, textStatus, errorThrown);
				}
			});
			
			$("#fileUploadForm").submit();
		}

	</script>	
	


						<div>
							<form id="feature-modify" class="group-box" name="feature-modify">
								
								<!-- feature -->
								<div class="heading">scenario</div>
								<div class="group-box-inner">
									<textarea rows="10" cols="90" class="input-box" id="scenario" name="scenario" style="overflow: hidden;">${scenario}</textarea>
								</div>
								
								<br>
								<div class="heading">algorithm</div>
								<div class="group-box-inner">
									<textarea rows="10" cols="90" class="input-box" id="algorithm" name="algorithm">${algorithm}</textarea>
								</div>
								
								<br>
								
		
								<!-- references -->
								<div class="heading">references 
									<img class="float-right" src="<%=contextPath%>/static/images/plus.png" style="width:20px;height:20px" onClick="javascript:f_addReference()">
								</div>
								<div class="group-box-inner" id="referenceList">
									
										
										<!-- referenceList의 Array size 구하기 -->
										<c:set var="len" value="${fn:length(referenceList)}"/>
										<c:set var="i" value="0"/>
										
										
										<c:forEach var="list" items="${referenceList}">
										<ul>
										
											<c:set var="i" value="${i+1}"/>
											<!-- 
											<li class="popup-menu-item"> 
												<input type="button" class="button" 
													<c:if test="${list.referenceTypeName != null}">value="${list.referenceTypeName} ▼"</c:if>
													<c:if test="${list.referenceTypeName == null}">value="select ▼"</c:if> 
														onClick="javascript:f_selectButton(this)">
												<img class="float-right" src="<%=contextPath%>/static/images/minus.png" style="width:20px;height:20px" onClick="javascript:f_removeReference(this)">
												<input type="text" id="taskId" class="input-box" value="${taskId}" name="taskId" hidden>
												<input type="text" id="referenceType" class="input-box" value="${list.referenceType}" name="referenceType" hidden>
												<input type="text" id="referenceId" class="input-box" value="${list.referenceId}" name="referenceId" hidden>
											</li>
											-->
											<li class="popup-menu-item">
												<input type="text" id="description" class="input-box width-650px" value="${list.description}" name="description" placeholder="description">
											</li>
											<li class="popup-menu-item">
												<input type="text" id="contents" class="input-box width-650px" value="${list.contents}" name="contents" placeholder="contents">
											</li>
											<li class="popup-menu-item">
												<input type="file" class="input-box width-500px" id="uploadFile" name="uploadFile" hidden>
												<input type="button" id="uploadButton" value="upload" onClick="javascript:f_fileUpload(this)" hidden>
											</li>
											
											<!-- 마지막에는 line을 찍어주지 않는다. -->
											<c:if test="${i < len}">
											<div class="popup-menu-divider" id="divider"></div>
											</c:if>
											
										</ul>
										</c:forEach>
																				
									
								</div>
								<br>
								
								<div><input type="button" value="save" class="button" onClick="javascript:f_saveFeature('<%=contextPath%>/task/feature/save', '${taskId}' )"></div>
								
								

								
								
								<!-- Hidden popup menu -->
								<div id="referenceTypeList" class="popup-menu-box" hidden>
									<div class="popup-menu">
										<ul>
											<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
												onClick="javascript:f_clickReferenceList('1', 'Link ▼')">Link</li>
											<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
												onClick="javascript:f_clickReferenceList('2', '문서 ▼')">문서</li>
											<li class="popup-menu-item" onmouseover="this.className='mouse over'" onmouseout="this.className='mouse out'"
												onClick="javascript:f_clickReferenceList('3', '이미지 ▼')">이미지</li>
										</ul>
									</div>
								</div>
								
							</form>
							
							<!-- Hidden fileUpload Form -->
							<div hidden>
								<form id="fileUploadForm" name="fileUploadForm" method="post" enctype="multipart/form-data">
									<input type="text" id="taskId" class="input-box" name="taskId">
									<input type="text" id="referenceId" class="input-box" name="referenceId">
								</form>
							</div>
							
							<!-- Hidden file button -->
							<div id="hiddenFileButton" hidden>
								<li class="popup-menu-item">
									<input type="file" class="input-box width-500px" id="uploadFile" name="uploadFile">
									<input type="button" id="uploadButton" class="button" value="upload" onClick="javascript:f_fileUpload(this,'${taskId}', '${list.referenceId}')">
								</li>
							</div>
								
								
							<!-- Hidden reference -->
							<div id="hiddenReference" hidden>
								<ul>
								<div class="popup-menu-divider"></div>
								<!-- 
								<li class="popup-menu-item"> 
									<input type="button" class="button" value="select ▼" onClick="javascript:f_selectButton(this)">
									<img class="float-right" src="<%=contextPath%>/static/images/minus.png" style="width:20px;height:20px" onClick="javascript:f_removeReference(this)">
									<input type="text" id="referenceType" class="input-box" name="referenceType" hidden>
								</li>
								-->
								<li class="popup-menu-item">
									<input type="text" id="description" class="input-box width-650px" name="description" placeholder="description">
								</li>
								<li class="popup-menu-item">
									<input type="text" id="contents" class="input-box width-650px" name="contents" placeholder="contents">
								</li>
								</ul>
							</div>
						</div>




