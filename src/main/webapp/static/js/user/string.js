/**
 * String Function
 * 
 * 
 * 
 */

/*
 * LPAD
 */
function f_lpad(orgData, paddingData, length) {

	var str = new String(orgData);
	
	if (str.length < length) {
		for (var i=0;i<length-str.length;i++) {
			str = paddingData + str;
		}
	}

	return str;
}


/*
 * muliply 숫자내에서 random 번호 생성 시키는 Method
 */
function f_random(multiply) {
	
	var r = parseInt(Math.random()*multiply);
	var mr = new String(multiply);
	
	return f_lpad(r, "0", mr.length-1);
}


