package com.mini.html5;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import java.util.HashMap;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;
import com.mini.html5.transport.CoreLogicService;

@Controller
@RequestMapping("/html5")
public class HTML5Controller extends ControllerParent {

	@Resource (name="CoreLogicService")
	CoreLogicService coreLogicService;
	
	@RequestMapping  
	(
			value = "/transport/{pageId}", 
			method = {RequestMethod.GET, RequestMethod.POST}

	)
	public ModelAndView execute (@PathVariable("pageId") String pageId, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, null);
	}
	
	
	@Override
	public ServiceParent getService(String pageId) {
		if (pageId.equals("corelogic")) return coreLogicService;
		
		return null;
	}

}
