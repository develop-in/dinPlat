package com.mini.category.category;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("CategoryModifyService")
public class CategoryModifyService extends ServiceParent {
		
	@Override
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		mav.setViewName("/category/category/category_modify");

		if (tr != null) dao.update("category.update_dp_category", parameter);
		
		List categoryList = (List) dao.getListData("category.categoryList", null);
		
		mav.addObject("categoryList", categoryList);
		
		DropLog.log(JsonE.beautifier(categoryList));
		return mav;
	}

}
