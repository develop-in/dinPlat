/**
 * JSON 관련 Javascript
 * 
 * 
 */

/*
 * Map 정의
 */
Map = function(){
		this.map = new Object();
	};
	Map.prototype = {   
		put : function(key, value){   
			/*if ( value.constructor == Array ){
				for ( var i =0 ; i < value.size(); i++ ) {
					  this.map[key] = value[i];
					  alert(i + key + " : " + value[i]);
				}
				
				alert('This is an array!' + value.legnth);
			}else{*/
				this.map[key] = value;
			/*}*/
			
		},   
		get : function(key){   
			return this.map[key];
		},
		containsKey : function(key){    
			return key in this.map;
		},
		containsValue : function(value){    
			for(var prop in this.map){
				if(this.map[prop] == value) return true;
			}
			return false;
		},
		isEmpty : function(key){    
			return (this.size() == 0);
		},
		clear : function(){   
			for(var prop in this.map){
				delete this.map[prop];
			}
		},
		remove : function(key){    
			delete this.map[key];
		},
		keys : function(){   
			/*var keys = new Array();   
			for(var prop in this.map){   
				keys.push(prop);
			}   
			return keys;*/
			var keys = new Array();
			var resVal = "";
			var count = 0;
			for(var prop in this.map){   
				keys.push(prop);
				if(count > 0){
					resVal += "_";
				}
				resVal += prop;
				count++;
			}   
			return resVal;
			
		},
		values : function(){   
			/*var values = new Array();   
			for(var prop in this.map){   
				values.push(this.map[prop]);
			}   
			return values;*/
			
			
			var values = new Array();
			var resVal = "";
			var count = 0;
			for(var prop in this.map){   
				values.push(prop);
				if(count > 0){
					resVal += "_";
				}
				resVal += this.map[prop];
				count++;
			}   
			return resVal;
		},
		size : function(){
			var count = 0;
			for (var prop in this.map) {
				count++;
			}
			return count;
		}
	};

/*
	HTML문서상에 있는 사용자 입력 객체의 value를 추출하여 JSON Object로 변환하는 Method.
	( Form과 무관하게 HTML 문서상에 있는 모든 input값을 읽어 들인다. )
*/
function f_getJSONFromDocument () {
	
	var jsonObject = {};
	
	$('input[type=text]').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('input[type=password]').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('input[type=email]').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('input[type=radio]:checked').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('input[type=checkbox]').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('select').each(function() {
		jsonObject[this.id] = this.value;
	});
	$('textarea').each(function() {
		jsonObject[this.id] = this.value;
	});
	
	return jsonObject;
}

/*
	HTML문서상에 있는 사용자 입력 객체의 value를 추출하여 JSON Object로 변환하는 Method.
	( ID가 여러 Object에 부여 되는 경우에 Class Name을 부여해서 ArrayData로 뽑아낸다. )
*/

function f_getJSONArrayDataFromDocument () {
	var jsonObject = {};
	
	var textItems = {};
	$('input[type=text]').each(function() {
		textItems[this.id] = this.value;
	});
	f_setJsonData(textItems, jsonObject, "text");
	//console.log("textItem : " + JSON.stringify(textItems));
	
	var passwordItems = {};
	$('input[type=password]').each(function() {
		passwordItems[this.id] = this.value;
	});
	f_setJsonData(passwordItems, jsonObject, "password");
	//console.log("passwordItem : " + JSON.stringify(passwordItems));
	
	var emailItems = {};
	$('input[type=email]').each(function() {
		emailItems[this.id] = this.value;
	});
	f_setJsonData(emailItems, jsonObject, "email");
	//console.log("emailItem : " + JSON.stringify(emailItems));
	
	var radioItems = {};
	$('input[type=radio]:checked').each(function() {
		radioItems[this.id] = this.value;
	});
	f_setJsonData(radioItems, jsonObject, "radio");
	//console.log("radioItem : " + JSON.stringify(radioItems));
	
	var checkBoxItems = {};
	$('input[type=checkbox]').each(function() {
		checkBoxItems[this.id] = this.value;
	});
	f_setJsonData(checkBoxItems, jsonObject, "checkbox");
	//console.log("checkboxItem : " + JSON.stringify(checkBoxItems));
	
	var selectItems = {};
	$('select').each(function() {
		selectItems[this.id] = this.value;
	});
	f_setJsonData(selectItems, jsonObject, "select");
	//console.log("selectItem : " + JSON.stringify(selectItems));
	
	var textareaItems = {};
	$('textarea').each(function() {
		textareaItems[this.id] = this.value;
	});
	f_setJsonData(textareaItems, jsonObject, "textarea");
	//console.log("textareaItem : " + JSON.stringify(textareaItems));
	
	return jsonObject;
}


function f_setJsonData (items, param, type) {
	
	for (var itemName in items) {
		var objCnt = $('.'+itemName).length;
		
		if (objCnt > 0) {
			var arr = new Array();
			
			for (var i=0; i<objCnt; i++) {
				var value = "";
				
				if (type == "radio") {
					value = $("input:radio[name="+itemName+"]:checked").eq(i).val(); 		// raido가 이름이 같으면 의미 없음.
				} else {
					value = $("."+itemName).eq(i).val();									// Array 데이터는 className으로 찾음 
				}
			
				if (value != "") {
					arr.push(value);
				}
			}
			
			param[itemName] = arr;
		} else {
			if (type == "radio") {
				param[itemName] = $("input:radio[name="+itemName+"]:checked").val();		// 단건 데이터는 ID로 찾음.
			} else {
				param[itemName] = $("#"+itemName).val();
			}
		}
	}
	
	// return param;
}




/*
	Array로 전달된 Key, Value로 jsonObject로 변환하는 Method.
*/
function f_getJsonByArray (key, value) {

	var jsonData = {};
	for (var i=0; i<key.length; i++) {
		jsonData[key[i]] = value[i];
	}
	
	return jsonData;
}





