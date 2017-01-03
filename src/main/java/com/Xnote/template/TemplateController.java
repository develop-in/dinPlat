package com.Xnote.template;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;
import com.mini.main.MainNavigationService;
import com.mini.main.MainService;
import com.mini.main.MainContentsService;

@Controller
@RequestMapping("/template")
public class TemplateController extends ControllerParent {

	@Resource (name="XNoteService")
	XNoteService xNoteService;
	
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
	public ModelAndView execute (@PathVariable("pageId") String pageId, @PathVariable("tr") String tr, MultipartHttpServletRequest mpRequest, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, mpRequest, request, tr);
	}
	
	@Override
	public ServiceParent getService(String pageId) {
		if (pageId.equals("xNote")) return xNoteService;
		if (pageId.equals("file")) return xNoteService;
		
		return null;
	}

}
