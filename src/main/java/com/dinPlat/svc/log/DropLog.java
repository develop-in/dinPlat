package com.dinPlat.svc.log;

import java.util.HashMap;
import java.util.List;

//import org.slf4j.Logger;
import org.apache.log4j.Logger;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;

public final class DropLog {
	
	public final static String LID = "[UserLog] ";
	
	

	/**
	 * 각 Class 내에서 log를 남길 경우 사용하는 Method
	 * @param logger
	 * @param clazz
	 * @param logData
	 */
	public final static void log (Logger logger, Object clazz, Object logData) {
		
		dropLog(logger, C.INFO_MODE, LID);
		dropLog(logger, C.INFO_MODE, "class : " + clazz);
		dropLog(logger, C.INFO_MODE, parsingMessage(logData));

	}
	

	/**
	 * HTTP Request/Response에서 log를 남길 경우 사용하는 Method
	 * 
	 * @param logger
	 * @param location
	 * @param uri
	 * @param traceNo
	 * @param logData
	 */
	public final static void log (Logger logger, String location, String uri, String traceNo, Object logData) {
		
		dropLog(logger, C.INFO_MODE, LID);
		dropLog(logger, C.INFO_MODE, location + " : " + uri);
		dropLog(logger, C.INFO_MODE, "traceNo : " + traceNo);
		dropLog(logger, C.INFO_MODE, parsingMessage(logData));

	}
	
	private static String parsingMessage(Object logData) {
		String logMessage = null;
		
		if (logData instanceof Exception)	logMessage = "Exception : " + ((Exception)logData).getMessage();
		if (logData instanceof InvalidException)	
			logMessage = "InvalidException : [" + ((InvalidException)logData).getCode() +"] " + ((InvalidException)logData).getMessage();
		if (logData instanceof ValidException)	
			logMessage = "ValidException : [" + ((ValidException)logData).getCode() +"] " + ((ValidException)logData).getMessage();
		if (logData instanceof String)		logMessage = "Message : " + logData;
		if (logData instanceof HashMap || logData instanceof List)	
											logMessage = "jsonData : \r\n" + JsonE.beautifier(logData);
		
		return logMessage;
	}
	
	
	private static void dropLog(Logger logger, String mode, String logMessage) {
		if (mode.equals(C.INFO_MODE)) {
			logger.info(logMessage);
		} else if (mode.equals(C.DEBUG_MODE)) {
			logger.debug(logMessage);
		}
	}
	
}
