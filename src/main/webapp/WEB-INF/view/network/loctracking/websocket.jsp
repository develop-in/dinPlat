<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String contextPath = request.getContextPath();
%>

<!-- 현재 좌표를 객체에 담는다. -->
<script>

	var currentXY = (function (latitude, longitude) {
		return {
			get_X : function () {
				return latitude;
			},
			get_Y : function () {
				return longitude;
			},
			set_X : function (_latitude) {
				latitude = _latitude;
			},
			set_Y : function (_longitude) {
				longitude = _longitude;
			}
		}
	})();
	
	if (!navigator.geolocation) {
		console.log ("Not available geolocation.");
	}
	
	function geolocation_success (position) {
		var latitude = position.coords.latitude;
		var longitude = position.coords.longitude;
		
		currentXY.set_X(latitude);
		currentXY.set_Y(longitude);
	}
	
	function geolocation_error () {
		console.log ("Not found user location!");
	}
	
	navigator.geolocation.getCurrentPosition(geolocation_success, geolocation_error);
	

	// 좌표 지도에 표시하기.
	function f_messenger_map (latitude, longitude, image) {
		var container = document.getElementById("map");
		var options = {
			center: new daum.maps.LatLng(latitude, longitude),
			level: 3
		};
		
		// 지도 보여주기
		var map = new daum.maps.Map(container, options);
		
		// 지도위에 교통정보 보여주기 (지하철, 버스 정류장)
		map.addOverlayMapTypeId(daum.maps.MapTypeId.TRAFFIC);
		
		// 마커 Image 바꾸기
		var imageSrc = image;
		//var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";
		var imageSize = new daum.maps.Size(64, 69);
		var imageOption = {offset: new daum.maps.Point(27, 69)};
		
		var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption);
		var markerPosition = new daum.maps.LatLng(latitude, longitude);
		
		var marker = new daum.maps.Marker(
				{
					position : markerPosition,
					image : markerImage
				});
		
		marker.setMap(map);
		
	}
	
</script>

<script>
	
	/*	
		stomp 통신을 위한 서버측 URL 
		- Spring Dispacterh의 requestMapping에 정의 되지 않은 URI를 지정해야 함. (dinplat의 requestMapping에 걸리지 않기 위해 /stomp로 지정 함.)
		- StompConfigurer.java의 registerStompEndpoints() 지정한 name과 동일.
	*/
	var url = "<%=contextPath%>/stomp/server";
	var socket = new SockJS(url);
	var stompClient = Stomp.over(socket);
	
	/*
		대화 그룹 Name을 담아 두는 객체 선언 (Closure 이용)
	*/
	function message_group (groupName) {
		return {
			get_groupName : function () {
				return groupName;
			},
			set_groupName : function (_groupName) {
				groupName = _groupName;
			}
		}
	}
	var groupName = message_group("${groupName}");			// 초기값 설정.
	
	/*
		대화 ID를 담아 두는 객체
	*/
	function message_user (privateId) {
		return {
			get_privateId : function () {
				return privateId;
			},
			set_privateId : function (_privateId) {
				privateId = _privateId;
			}
		}
	}
	var privateId = message_user ("${privateId}");			// 초기값 설정
	
	/*
		위의 Stomp.over(socket) 이후  지정된 URL로 connect를 수행하게 된다.
	*/
	stompClient.connect({}, function(frame) {
		
		/*
			connect 이후 clien화 server간에 network session이 연결되어 있는 상태로 아래의 subscribe()에 의해 client는 server의 message를 대기하는 상태가 된다.
		*/
	    stompClient.subscribe('/noti/message', function(message) {					// noti message
	        console.log("noti : " + message.body);
	    });
		
	    stompClient.subscribe(getGroupSubscribe(), function(message) {				// group message
	        console.log("group : " + message.body);
	    });
	    
	    stompClient.subscribe(getPrivateSubscribe(), function(message) {			// private message
	        console.log("private : " + message.body);
	    });
	    
	    stompClient.subscribe('/change/message', function(message) {				// 일정시간이 경과된 후 변경된 group Name, private Id를 변경하여 객체에 담아 둔다.
	    	console.log("change : " + message.body);
	    	var key = message.body["key"];
	    	if (key === "group") {
	    		groupName.set_groupName(message.body("value"));
	    	} else if (key === "private") {
	    		privateId.set_privateId(message.body("value"));	    
	    	}
	    });
	    
	});

	
	// group subscribe
	function getGroupSubscribe () {
		console.log("group : " + groupName.get_groupName());
		return "/group/"+groupName.get_groupName();
	}
	
	// private subscribe
	function getPrivateSubscribe () {
		console.log("private : " + privateId.get_privateId());
		return "/private/"+privateId.get_privateId();
	}
	
	
	function disconnect() {
	    if (stompClient != null) {
	        stompClient.disconnect();
	    }
	    $('#console').append('Disconnected<br>');
	}
	
	
	// 메세지 Send
	function messageSend(messageBroker) {
		var subscribeName;
		if (messageBroker === 'group') {
			subscribeName = "/" + messageBroker + "/" +  groupName.get_groupName();
		} else if (messageBroker === 'private') {
			subscribeName = "/" + messageBroker + "/" +  privateId.get_privateId();
		}
		
		/*
			서버로 message를 보낼때 서버측 destination.
			- "/send"는 StompConfigurer.java의 setApplicationDestinationPrefixes() 정의된 name과 동일
			- "/message"는 StompHandler.java 의 @MessageMapping에 정의된 name과 같아야 함.
		*/
		var xy = {};
		xy.x = currentXY.get_X();
		xy.y = currentXY.get_Y();
		xy.image = $("input:radio[name='me_radio']:checked").val();
		console.log("send : " + JSON.stringify(xy));
	    stompClient.send("/send"+subscribeName, {}, JSON.stringify(xy));
	}
	
	
	// groupName, privateId 객체에 Setting
	function f_clickPrivateId (gName, pId) {
		groupName.set_groupName (gName);
		privateId.set_privateId (pId);
		
		// subscribe 동적 생성
		var subscribeName = "/group/" +  gName;
		stompClient.subscribe(subscribeName, function(message) {
	        var json = JSON.parse(message.body);
	        var x = json.x;
	        var y = json.y;
	        var image = json.image;
	        console.log("receive : " + message.body);
	        f_messenger_map(x, y, image);
	    });
	}
	
	
	function f_onOff (obj, cupId) {
		var v = $(obj).val();
		if (v === "On") {
			$(obj).val("Off");
		} else {
			$(obj).val("On");
		}
	}


</script>
								<div class="float-left">
									<div class="heading" style="width:200px">CLAN</div>
	
									<div class="group-box-inner" id="clan" style="width:200px; height:450px">
										<table class="float-left">
											<c:forEach var="list" items="${clanList}">
											<tr>
												<td><input type="radio" name="me_radio" value="<%=contextPath%>${list.cupImage}" onClick="javascript:f_clickPrivateId('${list.groupName}', '${list.cupId}')"></td>
												<td>${list.nickName}<br>
													${list.groupName}</td>
												<td><img src="<%=contextPath%>${list.cupImage}"></td>
												<td><input type="button" class="button" value="On" id="${list.cupId}" onClick="javascript:f_onOff(this, '${list.cupId}')"></td>
											</tr>
											</c:forEach>
											<tr>
												<td colspan="4">
													<input type="text" id="cupId" hidden>
												</td>
											</tr>
											<!-- 
											<tr>
												<td colspan="4">
													groupName : <input type="text" id="groupName"> <br>
													user : <input type="text" id="privateId">
												</td>
											</tr>
											-->
											<tr>
												<td colspan="4">
													<input type="button" value="위치전송" onclick="javascript:messageSend('group')"> <br>
												</td>
											</tr>
										</table>
									</div>
								</div>
								
								<div class="float-left" style="width:10px">
								&nbsp;
								</div>
								
								<div class="float-left">

									<div class="heading" style="width:450px">Map</div>
									<div class="group-box-inner" id="map" style="widht:450px; height:450px">
	
									</div>

								</div>
								
								
								



