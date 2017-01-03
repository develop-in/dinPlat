
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
    String contextPath = request.getContextPath();

	//치환 변수 선언
	pageContext.setAttribute("cr", "\r"); //Space
	pageContext.setAttribute("cn", "\n"); //Enter
	pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
	pageContext.setAttribute("br", "<br/>"); //br 태그
%>

	<script>
	
		// feature page 수정 화면 호출
		function f_featureEdit (url, taskId) {
			var jsonData = {};
			jsonData["taskId"] = taskId;
			var param =  dinplat_getRequestData (jsonData);
			ajax_post_json (url, param, "view-inner", success_drawHtml);
		}

	</script>	

							<div id="view-inner" class="group-box">
								
								<!-- Task 기본정보 -->
								<div class="heading">scenario</div>
								<div class="textarea-box" id="scenario"><pre class="div-text">${scenario}</pre></div>
								<br>
								<div class="heading">algorithm</div>
								<div class="textarea-box" id="algorithm"><pre class="div-text">${algorithm}</pre></div>
								<br>
								
								<!-- Task reference -->
								<div class="heading">references</div>
								<div class="group-box-inner">
									<ul>
										
										<!-- referenceList의 Array size 구하기 -->
										<c:set var="len" value="${fn:length(referenceList)}"/>
										<c:set var="i" value="0"/>
										
										<c:forEach var="list" items="${referenceList}">
											
											<c:set var="i" value="${i+1}"/>
											<!-- 
											<li class="popup-menu-item">${list.referenceTypeName}</li>
											-->							
											<li class="popup-menu-item">${list.description}</li>
											<li class="popup-menu-item">
												<a href="${list.contents}" target="_blank" class="a_underline">${list.contents}</a>
											</li>
											
											<!-- 마지막에는 line을 찍어주지 않는다. -->
											<c:if test="${i < len}">
												<div class="popup-menu-divider"></div>
											</c:if>
										</c:forEach>
										
									</ul>
								</div>
								
								<br>
								<div><input type="button" class="button" value="edit" onClick="javascript:f_featureEdit('<%=contextPath%>/task/feature/edit', '${taskId}')"></div>
							</div>
			


