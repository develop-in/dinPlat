package com.mini.category;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.mini.common.service.ServiceParent;

@Service ("CategoryNavigationService")
public class CategoryNavigationService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/category/category_navigation");
		
		mav.addObject("menuId", ((Map)parameter).get("menuId"));
		
		return mav;
	}

}
