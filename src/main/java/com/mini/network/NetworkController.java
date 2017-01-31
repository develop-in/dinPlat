package com.mini.network;

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
import com.mini.html5.page.PagingService;
import com.mini.html5.transport.CoreLogicService;
import com.mini.network.loctracking.WebsocketService;

@Controller
@RequestMapping("/network")
public class NetworkController extends ControllerParent {


	@Resource (name="WebsocketService")
	WebsocketService websocketService;
	
	@RequestMapping  
	(
			value = "/loctracking/{pageId}", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute_transport (@PathVariable("pageId") String pageId, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, null);
	}

	@RequestMapping  
	(
			value = "/loctracking/{pageId}/{tr}", 
			method = {RequestMethod.GET, RequestMethod.POST}
	)
	public ModelAndView execute_transport (@PathVariable("pageId") String pageId, @PathVariable("tr") String tr, HttpServletRequest request) throws Exception {
		return super.executeHttpServletRequest(pageId, request, tr);
	}
	
	@Override
	public ServiceParent getService(String pageId) {
		
		if (pageId.equals("websocket")) return websocketService;
		
		return null;
	}

}
