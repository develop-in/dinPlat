
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%
    String contextPath = request.getContextPath();
%>


	<script>
	
		function f_pageEdit (url, taskId, pageId) {
			var jsonData = {};
			jsonData["taskId"] = taskId;
			jsonData["pageId"] = pageId;
			var param = dinplat_getRequestData (jsonData);
			ajax_post_json (url, param, "view", success_drawHtml);
		}
	
	</script>	

							<div id="view-inner" class="group-box">
								
								<!-- Page 기본정보 -->
								<div class="heading">page Info</div>
								<div class="group-box-inner">
									<table>
										<tr>
											<td><pre class="div-text">${description}</pre></td>
										</tr>
										<tr>
											<td>
												<div class="div-30px-right-padding float-left">create : ${createTime} </div> 
												<div class="div-30px-right-padding float-left">update : ${updateTime} </div> 
											</td>
											<td align="right">
												<input type="button" class="button" value="edit" onClick="javascript:f_pageEdit('<%=contextPath%>/page/newPage/edit', '${taskId}', '${pageId}')">
											</td>
										</tr>
									</table>
								</div>

								<br>
								
								<!-- Page Contents -->
								<div class="heading">page contents</div>
								<div class="group-box-inner" id="pageContents">
									
								</div>
							</div>
			


