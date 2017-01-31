package com.mini.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.svc.cache.MemcachedE;
import com.mini.common.controller.ControllerParent;
import com.mini.common.service.ServiceParent;
import com.mini.html5.page.PagingService;
import com.mini.html5.transport.CoreLogicService;
import com.mini.network.loctracking.WebsocketService;
import com.mini.system.memcached.MemCachedService;

@Controller
@RequestMapping("/system")
public class SystemController extends ControllerParent {


	@Resource (name="MemCachedService")
	MemCachedService memCachedService;
	
	@RequestMapping  
	(
			value = "/memcached/{pageId}", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute_transport (@PathVariable("pageId") String pageId, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, null);
	}

	
	@Override
	public ServiceParent getService(String pageId) {
		
		if (pageId.equals("simple")) return memCachedService;
		
		return null;
	}

}
