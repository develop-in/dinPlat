/**
 * Ajax callback function
 * 
 * 
 */

/*
 * json Data, post 방식 호출
 */
function ajax_json_post (url, param, key, callback) {
	$.ajax({
		url : 			url,
		type:			"POST",
		contentType: 	"application/json; charset=utf-8",
		data: 			JSON.stringify(param),			
		success:function(data){
			callback(key, data);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			error_alert(jqXHR, textStatus, errorThrown);
		}
	});
}


/*
 * recursive ajax call
 */
function ajax_callback(url, param, key, callback, recursiveUrl, recursiveCallback) {
	$.ajax({
		url : 			url,
		type:			"POST",
		contentType: 	"application/json; charset=utf-8",
		data: 			JSON.stringify(param),			
		success:function(data){
			callback(key, data);
			recursiveCallback(recursiveUrl, param, key, callback);
		},
		error:function(jqXHR, textStatus, errorThrown) {
			error_alert(jqXHR, textStatus, errorThrown);
		}
	});
}

/*
 * 성공 후 결과를 팝업으로 보여준다.
 */
function success_alert (resCode, resultData) {
	var jsonData = JSON.parse(resultData);
	alert (jsonData.resMsg);
}


/*
 * 성공 후 결과를 팝업으로 보여준다.
 */
function success_alert_jsonMsg (code, resultData) {
	var jsonData = JSON.parse(resultData);
	alert (jsonData.resMsg);
}


/*
 * 성공 후 결과를 팝업으로 보여준 후 입력필드 Clear
 */
function success_alert_clear (code, resultData) {
	var jsonData = JSON.parse(resultData);
	alert (jsonData.resMsg);
	if (jsonData.resCode == code) {
		f_clearInputData();
	}
}

/*
 * 성공 후 결과를 화면에 보여준다
 */
function success_drawHtml (jqueryId, resultData) {
	$("#"+jqueryId).empty();
	$("#"+jqueryId).append(resultData);
}

/*
 * 실패시 오류 팝업
 */
function error_alert (jqXHR, textStatus, errorThrown) {

	var errorMsg = "status(code) : " + jqXHR.status + "\n";
	errorMsg += "statusText : " + jqXHR.statusText + "\n";
	errorMsg += "responseText : " + jqXHR.responseText + "\n";
	errorMsg += "textStatus : " + textStatus + "\n";
	errorMsg += "errorThrown : " + errorThrown;
	alert (errorMsg);
}


