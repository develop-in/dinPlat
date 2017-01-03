package com.mini.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;
import com.mini.main.MainNavigationService;
import com.mini.main.MainService;
import com.mini.main.ErrorService;
import com.mini.main.MainContentsService;

@Controller
@RequestMapping("/")
public class MainController extends ControllerParent {

	@Resource (name="MainService")
	MainService mainService;
	
	@Resource (name="MainNavigationService")
	MainNavigationService mainNavigationService;

	@Resource (name="MainContentsService")
	MainContentsService mainContentsService;
	
	@Resource (name="ErrorService")
	ErrorService errorService;
	
	@RequestMapping 
	(
			value = "/{pageId}", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute (@PathVariable("pageId") String pageId, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, null);
	}
	
	@RequestMapping 
	(
			value = "/{pageId}/{tr}", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute (@PathVariable("pageId") String pageId, @PathVariable("tr") String tr, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, tr);
	}
	
	@Override
	public ServiceParent getService(String pageId) {
		if (pageId.equals("main")) return mainService;
		if (pageId.equals("navigation")) return mainNavigationService;
		if (pageId.equals("contents")) return mainContentsService;
		if (pageId.equals("error")) return errorService;
		
		return null;
	}

}
