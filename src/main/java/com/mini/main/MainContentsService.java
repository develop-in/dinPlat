package com.mini.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("MainContentsService")
public class MainContentsService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/main/contents");
		
		List pageList = (List) dao.getListData("category.pageList", (Map) parameter);
		
		mav.addObject("pageList", pageList);
		mav.addObject("taskId", ((Map)parameter).get("taskId"));
		
		DropLog.log(JsonE.beautifier(pageList));
		
		return mav;
	}

}
