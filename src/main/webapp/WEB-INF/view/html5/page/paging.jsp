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

<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.event.drag-1.5.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/static/js/jquery.touchSlider.js"></script>


<script>
	
	$(document).ready(function () {
		
		/* Touch (Button or Mouse)로 Flicking 처리 */
		$("#touchSlider").touchSlider({
			btn_prev : $("#touchSlider").next().find(".btn_prev"),
			btn_next : $("#touchSlider").next().find(".btn_next"),
			initComplete : function () {
				var _this = this;								// this : touchSlider를 의미
				
				$(".touch-dot").bind("click", function () {
					var index = $(this).attr("value");			// this : touchSlider내의 contents (li)를 의미
					_this.go_page(index - 1);
					
				});
			},
			counter : function (e) {
				$(".touch-dot").attr("src", "<%=contextPath%>/static/images/blank_dot.png");
				$(".touch-dot").eq(e.current-1).attr("src", "<%=contextPath%>/static/images/black_dot.png");
			}
			
		});
	
		
		/* 자동 Flicking 처리 */
		$("#autoSlider").touchSlider({
			
			autoplay : {
				enable : true,
				pauseHover : true,
				addHoverTarget : "",
				interval : 3000
			},
			// 현재 페이지에 해당하는 선택Box(button) 하단에 표시 하기
			initComplete : function (e) {
				var _this = this;
				var $this = $(this);
				var paging = $this.next(".paging");
				var len = Math.ceil(this._len / this._view);
				
				paging.html("");
				for(var i = 1; i <= len; i++) {
					paging.append('<button type="button" class="btn_page">' + i + '</button>');
				}

				paging.find(".btn_page").bind("click", function (e) {
					_this.go_page($(this).index());
				});
			},
			counter : function (e) {
				$(this).next(".paging").find(".btn_page").removeClass("on").eq(e.current-1).addClass("on");
			}
			
		});
		
	});
	
	

</script>

								<div class="heading">Touch Slider</div>
								<div class="group-box-inner" id="xy">

									<div id="touchSlider">
										<ul>
											<li>
												content 1
											</li>
											<li style="background:#396">
												content 2
											</li>
											<li style="background:#39C">
												content 3
											</li>
											<li style="background:#33C">
												content 4
											</li>
											<li style="background:#ccc">
												content 5
											</li>
										</ul>
									</div>
									<div class="btn_area">
										<button type="button" class="btn_prev button">prev</button>
										<button type="button" class="btn_next button">next</button>
										<div>
											<span><img class="touch-dot" src="<%=contextPath%>/static/images/black_dot.png" value="1" style="width:30px;height:30px"></span>
											<span><img class="touch-dot" src="<%=contextPath%>/static/images/blank_dot.png" value="2" style="width:30px;height:30px"></span>
											<span><img class="touch-dot" src="<%=contextPath%>/static/images/blank_dot.png" value="3" style="width:30px;height:30px"></span>
											<span><img class="touch-dot" src="<%=contextPath%>/static/images/blank_dot.png" value="4" style="width:30px;height:30px"></span>
											<span><img class="touch-dot" src="<%=contextPath%>/static/images/blank_dot.png" value="5" style="width:30px;height:30px"></span>
										</div>
									</div>
									
								</div>
								
								
								<br>
								<div class="heading">Auto Slider (3초)</div>
								<div class="group-box-inner" id="xy">

									<div id="autoSlider">
										<ul>
											<li>
												content 1
											</li>
											<li style="background:#396">
												content 2
											</li>
											<li style="background:#39C">
												content 3
											</li>
											<li style="background:#33C">
												content 4
											</li>
											<li style="background:#ccc">
												content 5
											</li>
										</ul>
									</div>
									<div class="paging"></div>
								</div>


