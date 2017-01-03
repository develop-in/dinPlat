document.write('<script src="static/js/user/date.js"></script>');
document.write('<script src="static/js/user/form.js"></script>');
document.write('<script src="static/js/user/string.js"></script>');
document.write('<script src="static/js/user/ds.js"></script>');
document.write('<script src="static/js/user/ajax.js"></script>');


/**
 * Request / Response
 * 
 * 
 */


/*
 * Request 데이터중 Body 부분의 json 데이터를 받아 dinPlat의 표준 JSON 호출 규격을 만드는 Method.
 * input - JSON Data (body 영역)
 * output - Header, Body가 포함된 JSON Data
 */
function dinplat_getRequestData (jsonData) {

	var headerData = {};
	var traceNo = f_getDate("YYYYMMDDHHMISS") + f_random(1000);
	headerData["traceNo"] = traceNo;
	
	var reqData = {};
	reqData["Header"] = headerData;
	reqData["Body"] = jsonData;
	
	return reqData;
}


/*
	Header Data
*/
function f_getHeaderData () {

	var header = {};
	var headerData = {};
	
	var traceNo = f_getDate("YYYYMMDDHHMISS") + f_random(1000);
	
	headerData["traceNo"] = traceNo;
	header["Header"] = headerData;
	
	return header;
}



/*
	Request Data (key, value를 인자로 받아 처리 하는 Method)
*/
function f_getRequestData (key, value) {

	var headerData = {};
	var traceNo = f_getDate("YYYYMMDDHHMISS") + f_random(1000);
	headerData["traceNo"] = traceNo;
	
	var bodyData = {};
	for (var i=0; i<key.length; i++) {
		bodyData[key[i]] = value[i];
	}
	
	var reqData = {};
	reqData["Header"] = headerData;
	reqData["Body"] = bodyData;
	
	return reqData;
}


/**
* Sesssion
*
*
*/
function f_checklo() {
	var lo = $.cookie("lo");
	
	if (lo == "Y") return true;
	
	return false;
}