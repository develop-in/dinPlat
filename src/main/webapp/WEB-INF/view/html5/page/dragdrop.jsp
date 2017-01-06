<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
	#touchSlider { width:100%; height:150px; margin:0 auto; background:#ccc; position:relative; overflow:hidden; }
	#touchSlider ul { width:99999px; height:150px; position:absolute; top:0; left:0; overflow:hidden; }
	#touchSlider ul li { float:left; width:100%; height:150px; background:#9C9; font-size:14px; color:#fff; }
	
	
	#autoSlider { width:100%; height:150px; margin:0 auto; background:#ccc; position:relative; overflow:hidden; }
	#autoSlider ul { width:99999px; height:150px; position:absolute; top:0; left:0; overflow:hidden; }
	#autoSlider ul li { float:left; width:100%; height:150px; background:#9C9; font-size:14px; color:#fff; }
	
	
	.paging { background:#ffffff; text-align:center; overflow:hidden; }
	.paging .btn_page { display:inline-block; width:10px; height:10px; margin:3px; font-size:0px; line-height:0; text-indent:-9999px; background:#3399CC; }
	.paging .btn_page.on { background:#ff0000; }
</style>


<%
    String contextPath = request.getContextPath();
%>

<style>
	#div1, #div2 {
		position: relative;
	    width: 130px;
	    height: 120px;
	    margin: 10px;
	    padding: 10px;
	    border: 1px solid black;
	}
</style>


<!-- Drag & Drop -->
<script>
	
	function drag(event) {
		// drag 시작시 이동해야할 id를 set 한다.
		event.dataTransfer.setData("id", event.target.id);
	}
	
	function drop(event) {
		// drag를 허용 하고
		event.preventDefault();
		
		// 이동 대상이 되는 객체를 가져와서 drop 함.
		var ambulance = event.dataTransfer.getData("id");
		event.target.appendChild(document.getElementById(ambulance));
		
		// 이미지가 최종 이동한 위치를 찾는다.
		console.log("location : " + $("#"+ambulance).parent().attr("id"));
	}
	
	function allowDrop(event) {
		// drag를 허용하기 위해 설정 .. 이동시 매번 event 발생
		event.preventDefault();
	}
	
</script>


<!-- Mouse Move -->
<script>
	// mouse 좌표
	var oldX;
	var oldY;
	var currentX;
	var currentY;

	// 이미지 left/top 좌표
	var oldLeft;
	var oldTop;
	var currentLeft;
	var currentTop;
	
	function mouseDrag(event) {
		
		// mouse 좌표 초기값 저장
		currentX = event.clientX;
		currentY = event.clientY;
		
		// 이미지 left/top 최기값 저장
		currentLeft = $("#car").offset().left;
		currentTop =  $("#car").offset().top;
	}
	
	function mouseAllowDrop(event) {
		
		oldX = currentX;
		oldY = currentY;
		
		oldLeft = currentLeft;
		oldTop = currentTop;
		
		// mouse 좌표 이동 한만큼 계산
		var diffX = event.clientX - oldX;
		var diffY = event.clientY - oldY;
		
		var car = document.getElementById("car");
		
		// 이미지를 mouse 이동한 만큼 더하기
		$("#car").offset({left:oldLeft+diffX, top:oldTop+diffY});
		
		currentX = event.clientX;
		currentY = event.clientY;
		
		currentLeft = $("#car").offset().left;
		currentTop = $("#car").offset().top;
	}
	
</script>

								<div class="heading">Drag&Drop</div>

								<div class="group-box-inner">

									<!-- ondrop 이벤트를 받을수 있는 설정(ondrop)과 이동을 할수 있는 설정(ondragover)을 함. -->
									<div id="div1" ondrop="drop(event)" ondragover="allowDrop(event)">
									
										<!-- dragable=true -->
  										<img src="<%=contextPath%>/static/images/ambulance.png" draggable="true" ondragstart="drag(event)" id="ambulance">
									</div>
			
									<div id="div2" ondrop="drop(event)" ondragover="allowDrop(event)"></div>

								</div>
								
								
								<br>
								<div class="heading">Move</div>

								<div class="group-box-inner">
					
									<!-- drag할수 있는 설정(ondragover)만 함. -->
									<div ondragover="mouseAllowDrop(event)">
										<img src="<%=contextPath%>/static/images/mini_car.png" draggable="true" ondragstart="mouseDrag(event)" id="car">
									</div>
	
								</div>
								



