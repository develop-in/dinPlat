/**
 * Form Function
 * 
 * 
 */

/*
	checkbox에 check된 값을 Value Array 형태로 리턴하는 Funtion
	예) ['MT001', 'Mt002']
*/
function f_getCheckboxChechkedValues(checkboxName) {
	
	var values = new Array();
	
	checkbox = document.getElementsByName(checkboxName);

	for (var i=0;i<checkbox.length;i++) {
		if (checkbox[i].checked) {
			values.push(checkbox[i].value);
		}
	}

	return values;
}


/*
	checkbox에 check된 값을 Object Array로 리턴하는 function (JSON 형식)
	예) [ 
	     {
        	"termsCode" : "MT001"
      	 }, {
        	"termsCode" : "MT002"
         } 
       ]
*/
function f_getCheckboxCheckedArray(checkboxName, obj) {
	
	var values = new Array();
	checkbox = document.getElementsByName(checkboxName);

	for (var i=0;i<checkbox.length;i++) {
		if (checkbox[i].checked) {
			values.push(new obj(checkbox[i].value));
		}
	}

	return values;
}
	

/*
	Form안의 Object의 value를 추출하여 Form Data로 반환하는 function 
*/
function f_getFormData (form) {
	var formData = $("form[name='" + form + "']").serialize();
	return formData;
}


/*
	HTML문서상에 있는 Input값을 모두 clear 시킨다.
*/
function f_clearInputData () {

	$('input[type=text]').each(function() {
		this.value = "";	
	});
	$('input[type=password]').each(function() {
		this.value = "";
	});
	$('input[type=email]').each(function() {
		this.value = "";
	});
	$('input[type=radio]').each(function() {
		this.checked = false;
	});
	$('select').each(function() {
		this.value = "";
	});
	$('textarea').each(function() {
		this.value = "";
	});

}

