package com.mini.common.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.log.DropLog;
import com.dinPlat.svc.validation.Validator;
import com.mini.common.inout.ReqHeader;
import com.mini.common.service.ServiceParent;


//@Controller
public abstract class ControllerParent {


	/**
	 * parameter 전달 방식을 처리하는 request
	 * 
	 * @param command
	 * @param parameter
	 * @param request
	 * @param tr			: 추가 parameter (ex) 화면ID, 추적번호 등..
	 * @return
	 * @throws Exception
	 */
	public ModelAndView executeHttpServletRequest (String pageId, HttpServletRequest request, String tr) throws Exception {

		ServiceParent sp;
		ModelAndView mav=null;
		
		// Service
		try {
			
			Enumeration paramList = request.getParameterNames();
			Map parameter = new HashMap();
			while(paramList.hasMoreElements()){
				String paramName = (String) paramList.nextElement();
				String paramValue = request.getParameter(paramName);
				parameter.put(paramName, paramValue);
			}
			
			// Request log
			DropLog.log(DropLog.LOC_REQUEST, request.getRequestURL().toString(), parameter);
			
			// Service
			sp = getService(pageId);
			Map extendParameter = new HashMap();
			extendParameter.put("tr", tr);
			
			mav = (ModelAndView) sp.doService(parameter, extendParameter);
			
			// Response log
			DropLog.log(DropLog.LOC_RESPONSE, request.getRequestURL().toString(), mav.getViewName());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return mav;
	}
	
	
	/**
	 * JSON Data를 처리 Request
	 * @param command
	 * @param parameter
	 * @param request
	 * @param tr			: 추가 parameter (ex) 화면ID, 추적번호 등..
	 * @return
	 * @throws Exception
	 */
	public ModelAndView executeRequestBody (String pageId, HashMap parameter, HttpServletRequest request, String tr) throws Exception {

		ServiceParent sp;
		ModelAndView mav=null;
		
		// Service
		try {
			
			// Request log
			DropLog.log(DropLog.LOC_REQUEST, request.getRequestURL().toString(), parameter);
			
			// 공통 헤더 Validation
			Validator.execute((Map) parameter.get("Header"), ReqHeader.class);
			
			// Service
			sp = getService(pageId);
			Map extendParameter = new HashMap();
			extendParameter.put("tr", tr);
			mav = (ModelAndView) sp.doService((Map) parameter.get("Body"), extendParameter);
			
			// Response log
			DropLog.log(DropLog.LOC_RESPONSE, request.getRequestURL().toString(),  mav.getViewName());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return mav;
	}
	
	
	/**
	 * multipart/form-data 전용 request
	 * @param pageId
	 * @param mpRequest
	 * @param request
	 * @param tr
	 * @return
	 * @throws Exception
	 */
	public ModelAndView executeHttpServletRequest (String pageId, MultipartHttpServletRequest mpRequest, HttpServletRequest request, String tr) throws Exception {

		ServiceParent sp;
		ModelAndView mav=null;
		
		// Service
		try {
			
			Enumeration paramList = request.getParameterNames();
			Map parameter = new HashMap();
			while(paramList.hasMoreElements()){
				String paramName = (String) paramList.nextElement();
				String paramValue = request.getParameter(paramName);
				parameter.put(paramName, paramValue);
			}
			
			// Request log
			DropLog.log(DropLog.LOC_REQUEST, request.getRequestURL().toString(), parameter);
			
			// Service
			sp = getService(pageId);
			Map extendParameter = new HashMap();
			extendParameter.put("tr", tr);
			extendParameter.put("file", mpRequest);
			
			mav = (ModelAndView) sp.doService(parameter, extendParameter);
			
			// Response log
			DropLog.log(DropLog.LOC_RESPONSE, request.getRequestURL().toString(), mav.getViewName());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		return mav;
	}
	
	
	// Service 선택
	public abstract ServiceParent getService (String pageId);	

}
