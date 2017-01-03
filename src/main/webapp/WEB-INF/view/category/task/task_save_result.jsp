<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	

	<script>
	
		function f_editTask (url, taskId) {
			var jsonData = {};
			jsonData["taskId"] = taskId;
			var param = dinplat_getRequestData (jsonData);
			ajax_post_json (url, param, "view", success_drawHtml);
			
			$(".pagehead-item").removeClass('selected');
			$("#modify").addClass('selected');
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
											<td class="data-item-title">task name</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${taskName}</td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td align="bottom"><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">category name</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${categoryName}</td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">description</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${description}</td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">menu name</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${menuName}</td>
										</tr>
										<tr><td colspan="2">&nbsp;</td></tr>
										<tr>
											<td><img class="data-item-image" src="<%=contextPath%>/static/images/data-item.png"></td>
											<td class="data-item-title">directory</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>${directory}</td>
										</tr>
									</table>
								</div>
								
								<br>
								<div><input type="button" value="edit" class="button" onClick="f_editTask('<%=contextPath%>/task/create/edit', '${taskId}')"></div>
							</div>
							
						</div>


