
<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%
    String contextPath = request.getContextPath();
%>

	<script>
	
		function f_currentXY() {
			if (!navigator.geolocation) {
				alert ("Not available geolocation.");
				return;
			}
			
			function geolocation_success (position) {
				var latitude = position.coords.latitude;
				var longitude = position.coords.longitude;
				$("#currentXY").val("X : " + latitude + ", Y : " + longitude);
				
				f_draw_daum_map (latitude, longitude);
			}
			
			function geolocation_error () {
				alert ("Not found user location!");
			}
			
			navigator.geolocation.getCurrentPosition(geolocation_success, geolocation_error);
		}
		
		
		function f_draw_daum_map (latitude, longitude) {

			var container = document.getElementById("map");
			var options = {
				center: new daum.maps.LatLng(latitude, longitude),
				level: 3
			};
			
			// 지도 보여주기
			var map = new daum.maps.Map(container, options);
			
			// 지도위에 교통정보 보여주기 (지하철, 버스 정류장)
			map.addOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);
			
			// Marker 만들기
			var marker = new daum.maps.Marker({
				position: map.getCenter()
			});
			marker.setMap(map);
			
			// 마우스 클릭시 좌표 읽어와서 marking 하기
			daum.maps.event.addListener (map, 'click', function (mouseEvent) {
				var latLng = mouseEvent.latLng;
				
				marker.setPosition(latLng);
				$("#clickXY").val("X : " + latLng.getLat() + ", Y : " + latLng.getLng());
			});
		}

		
		
	</script>	

							<div class="heading">좌표 구하기</div>
								<div class="group-box-inner" id="xy">
									<table>
										<tr>
											<td>
												<input type="button" class="button" value="현재좌표" onClick="javascript:f_currentXY()">
												<input type="text" class="input-box" id="currentXY" size="50">
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" class="button" value="클릭좌표">
												<input type="text" class="input-box" id="clickXY" size="50">
											</td>
										</tr>
									</table>
								</div>
							</div>
							
							<br>
							<div class="heading">카카오맵</div>
								<div class="group-box-inner" id="map" style="widht:500px; height:400px">

								</div>
							</div>