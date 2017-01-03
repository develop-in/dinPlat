package com.mini.main;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("MainService")
public class MainService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr == null) {
			mav.setViewName("/main/main");
		} else if (tr.equals("header-box")){
			mav.setViewName("/main/header-box");
		}
		
		List categoryList = (List) dao.getListData("category.categoryList", null);
		DropLog.log(JsonE.beautifier(categoryList));
		
		mav.addObject("categoryList", categoryList);
		return mav;
	}

}
