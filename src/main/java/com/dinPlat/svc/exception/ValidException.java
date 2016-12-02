package com.dinPlat.svc.exception;

public class ValidException extends Exception {
	String code = "";
	String msg = "";
	
	public ValidException (String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ValidException (String msg) {
		this.msg = msg;
	}
	
	public String getCode () {
		return this.code;
	}
	
	public String getMessage () {
		return this.msg;
	}
}
