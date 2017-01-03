package com.dinPlat.svc.log;

import java.util.HashMap;
import java.util.List;

//import org.slf4j.Logger;
import org.apache.log4j.Logger;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;

public final class DropLog {
	private final static Logger logger = Logger.getLogger(ServiceParent.class);
	
	public final static String TAG 					= "[UserLog] ";
	
	// loc
	public final static String LOC_REQUEST 			= "request";
	public final static String LOC_RESPONSE			= "response";
	public final static String LOC_PROCESS			= "process";
	

	/**
	 * 
	 * @param loc			: log를 찍으려는 위치 (request, response)
	 * @param uri
	 * @param traceNo
	 * @param logData
	 */
	public final static void log (String loc, String uri, Object logData) {
		String logMessage = "";
		if (logData != null) {
			logMessage = parsingMessage(logData);
		}
		logger.info(TAG + loc + " : " + uri + logMessage);

	}
	public final static void log (String loc, String uri) {
		log (loc, uri, null);
	}
	
	// 단순 메세지 로그
	public final static void log (String message) {
		logger.info(TAG + "message : " + message);
	}
	
	
	
	/** 메세지 */
	private static String parsingMessage(Object logData) {
		String logMessage = null;
		
		if (logData instanceof Exception) {
			logMessage = ", Exception : " + ((Exception)logData).getMessage();
		}
		if (logData instanceof InvalidException) {	
			logMessage = ", InvalidException : [" + ((InvalidException)logData).getCode() +"] " + ((InvalidException)logData).getMessage();
		}
		if (logData instanceof ValidException) {	
			logMessage = ", ValidException : [" + ((ValidException)logData).getCode() +"] " + ((ValidException)logData).getMessage();
		}
		if (logData instanceof String) {
			logMessage = ", log data : " + (String)logData;
		}
		if (logData instanceof HashMap || logData instanceof List) {
			logMessage = ", log data : " + JsonE.beautifier(logData);
//			logMessage = ", log data : " + logData;
		}
		
		return logMessage;
	}
	
}
