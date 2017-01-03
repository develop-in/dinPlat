<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	

	<script>
	
		function f_editPage (url, taskId, pageId) {
			var jsonData = {};
			jsonData["taskId"] = taskId;
			jsonData["pageId"] = pageId;
			var param = dinplat_getRequestData (jsonData);
			ajax_post_json (url, param, "view", success_drawHtml);
		}
	</script>	

						<div>
							<div class="group-box">
								<div class="heading">result to created</div>
								<div class="group-box-inner">
									<table>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td align="bottom"><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">page name</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${pageName}</td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">description</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td><pre class="div-text">${description}</pre></td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">URL</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${linkUrl}</td>
										</tr>
									</table>
								</div>
								
								<br>
								<div><input type="button" value="edit" class="button" onClick="f_editPage('<%=contextPath%>/page/newPage/edit', '${taskId}', '${pageId}')"></div>
							</div>
							
						</div>


