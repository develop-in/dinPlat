/**
 * Date Function
 * 
 * 
 */

/*
 * 현재 시간을 구하는 Method
 */
function f_getDate(format) {
		
	var date;
		
	if (format == "YYYYMMDDHHMISS") {
		date = f_getYMDHIS();
	} else if (format == "YYYYMMDD") {
		date = f_getYMD();
	}
		
	return date;
}


/*
 * 
 */
function f_getYMDHIS () {
		
	var y = f_getYMD ();
	var h = f_getHIS ();

	return y+h;
}
	

/*
 *
 */
function f_getYMD () {
	var date = new Date();
		
	var y = date.getFullYear();
	var m = f_lpad(date.getMonth() + 1, "0" , 2);
	var d = f_lpad(date.getDate(), "0", 2);
		
	return ""+ y+m+d;
}
	

/*
 * 
 */
function f_getHIS () {
		
	var date = new Date();

	var h = f_lpad(date.getHours(), "0", 2);
	var i = f_lpad(date.getMinutes(), "0", 2);
	var s = f_lpad(date.getSeconds(), "0", 2);

	return ""+ h+i+s;
}
