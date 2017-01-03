/**
 * Form Function
 * 
 * 
 */


/*
	Form안의 Object의 value를 추출하여 String Data로 반환하는 function
	- Form과 Form안의 Object에 대해 "name" 지정이 되어 있어야 한다.
	- GET, POST parameter 전달시 사용 
	예) address=seoul&zipcode=123456
*/
function form_getFormData (form) {
	var formString = $("form[name='" + form + "']").serialize();
	return formString;
}


/*
 *  Form안의 Object의 value를 추출하여 JSON Object Data로 반환하는 function
 *  - 예) [{"name" : "address"}, {"value" : "seoul"}]를 Json Object로 반환 한다.
 */
function form_getFormDataToJson (form) {
	var jsonData = {};
	var formData = $("form[name='" + form + "']").serializeArray();
	
	$.each(formData, function() {
        if (jsonData[this.name] !== undefined) {
            if (!jsonData[this.name].push) {
            	jsonData[this.name] = [jsonData[this.name]];
            }
            jsonData[this.name].push(this.value || '');
        } else {
        	jsonData[this.name] = this.value || '';
        }
    });
	
    return jsonData;
}





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

