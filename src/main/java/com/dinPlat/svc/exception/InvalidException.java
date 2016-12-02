package com.dinPlat.svc.exception;

import com.dinPlat.svc.code.M;

public class InvalidException extends Exception {
	String code = "";
	String message = "";
	
	public InvalidException () {
		
	}
	
	public InvalidException (M codemsg) {
		setCode(codemsg.getCode());
		setMessage(codemsg.getMsg());
	}
	
	public InvalidException (M codemsg, String extendMsg) {
		setCode(codemsg.getCode());
		setMessage(codemsg.getMsg() + extendMsg);
	}
	
	public InvalidException(String message) {
		this.message = message;
	}
	
	public InvalidException(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	


	
}
