package com.mini.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import java.util.HashMap;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mini.category.CategoryNavigationService;
import com.mini.category.category.CategoryCreateService;
import com.mini.category.category.CategoryModifyService;
import com.mini.category.task.TaskCreateService;
import com.mini.category.task.TaskFeatureService;
import com.mini.category.task.TaskFileUploadService;
import com.mini.category.task.TaskModifyService;
import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;

@Controller
@RequestMapping("/task")
public class TaskController extends ControllerParent {

	@Resource (name="TaskCreateService")
	TaskCreateService taskCreateService;
	
	@Resource (name="TaskModifyService")
	TaskModifyService taskModifyService;
	
	@Resource (name="TaskFeatureService")
	TaskFeatureService taskFeatureService;
	
	@Resource (name="TaskFileUploadService")
	TaskFileUploadService taskFileUploadService;
	
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
			value = "/{pageId}/upload", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute (@PathVariable("pageId") String pageId, MultipartHttpServletRequest mpRequest, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, mpRequest, request, null);
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
		if (pageId.equals("create")) return taskCreateService;
		if (pageId.equals("modify")) return taskModifyService;
		if (pageId.equals("feature")) return taskFeatureService;
		if (pageId.equals("file")) return taskFileUploadService;
		
		return null;
	}

}
