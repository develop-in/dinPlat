package com.template.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.exception.ExceptionE;
import com.dinPlat.svc.log.DropLog;
import com.dinPlat.svc.validation.Validator;
import com.template.common.inout.ReqHeader;
import com.template.common.service.ServiceParent;


//@Controller
public abstract class ControllerParent {
	private Logger logger = Logger.getLogger(ControllerParent.class);
	
	public ModelAndView
		doController (@PathVariable("command") String command, HashMap parameter, HttpServletRequest request) throws Exception {

		ServiceParent sp;
		ModelAndView mav=null;
		
		// Service
		try {
			
			// Request log
			DropLog.log(logger, C.HTTP_REQUEST, request.getRequestURL().toString(), (String)((HashMap) parameter.get("Header")).get("traceNo"), parameter);
			
			// 공통 헤더 Validation
			Validator.execute((Map) parameter.get("Header"), ReqHeader.class);
			
			// Service
			sp = getService(command);
			mav = (ModelAndView) sp.doService((Map) parameter.get("Body"), request);
			
			// Response log
			DropLog.log(logger, C.HTTP_RESPONSE, request.getRequestURL().toString(), (String)((HashMap) parameter.get("Header")).get("traceNo"), JsonE.beautifier(sp.getResCodeMap()));
			
		} catch (Exception e) {
			ExceptionE.doException(e);
			throw e;
		}
		
		// Return Data
		return mav;
	}
	
	// Service 선택
	public abstract ServiceParent getService (String command);
	
}
