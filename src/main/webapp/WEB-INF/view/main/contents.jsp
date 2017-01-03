
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>


	<script>
	
		// pageheade 선택시 처리
		function f_clickPagehead(obj, taskId, pageId, linkUrl) {
			$(".pagehead-item").removeClass('selected');
			$(obj).addClass('selected');
			
			var url = "<%=contextPath%>/page/pageInfo";
			var param = "taskId="+taskId+"&pageId="+pageId;
			
			var recursiveUrl = "<%=contextPath%>"+linkUrl;
			
			ajax_post_recursive (url, param, "view", success_drawHtml, recursiveUrl, param, "pageContents", ajax_post, success_drawHtml);
			
			// ajax_post (url, param, "view", success_drawHtml);
		}

		// new page
		function f_newPage(obj, taskId) {
			$(".pagehead-item").removeClass('selected');
			$(obj).addClass('selected');
			
			var url = "<%=contextPath%>/page/newPage";
			var param = "taskId="+taskId;
			ajax_post (url, param, "view", success_drawHtml);
		}
	</script>	

							<!-- feature Page -->
							<div class="pagehead-item float-left selected" onClick="javascript:f_clickNavigation(this, '<%=contextPath%>/contents', '${taskId}')">feature</div>
							
							<!-- contents Page -->
							<c:forEach var="list" items="${pageList}">
							<div class="pagehead-item float-left" onClick="javascript:f_clickPagehead(this, '${list.taskId}', '${list.pageId}', '${list.linkUrl}')">${list.pageName}</div>
							</c:forEach>
							
							<div class="pagehead-item float-right" onClick="javascript:f_newPage(this, '${taskId}')">
								<img class="float-right" src="<%=contextPath%>/static/images/newpage.png" style="width:30px;height:30px">
							</div>
			


