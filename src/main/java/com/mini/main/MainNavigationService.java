package com.mini.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("MainNavigationService")
public class MainNavigationService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/main/navigation");
			
		List taskList = (List) dao.getListData("category.taskList", (Map)parameter);
			
		mav.addObject("menuName", ((Map)parameter).get("menuName"));
		mav.addObject("taskList", taskList);
			
		DropLog.log(JsonE.beautifier(taskList));
		
		return mav;
	}

}
