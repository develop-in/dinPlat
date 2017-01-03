package com.mini.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import java.util.HashMap;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mini.category.CategoryNavigationService;
import com.mini.category.category.CategoryCreateService;
import com.mini.category.category.CategoryModifyService;
import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;

@Controller
@RequestMapping("/category")
public class CategoryController extends ControllerParent {

	@Resource (name="CategoryNavigationService")
	CategoryNavigationService categoryNavigationService;
	
	@Resource (name="CategoryCreateService")
	CategoryCreateService categoryCreateService;

	@Resource (name="CategoryModifyService")
	CategoryModifyService categoryModifyService;
	

	
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
			method = {RequestMethod.GET, RequestMethod.POST},
			produces= "application/json; charset=UTF-8"

	)
	public ModelAndView execute (@PathVariable("pageId") String pageId, @PathVariable("tr") String tr, 
								 @RequestBody HashMap parameter, HttpServletRequest request) throws Exception {
		return super.executeRequestBody(pageId, parameter, request, tr);
	}
	
	
	@Override
	public ServiceParent getService(String pageId) {
		if (pageId.equals("navigation")) return categoryNavigationService;
		if (pageId.equals("create")) return categoryCreateService;
		if (pageId.equals("modify")) return categoryModifyService;
		
		return null;
	}

}
