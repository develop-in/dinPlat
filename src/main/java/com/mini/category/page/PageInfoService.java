package com.mini.category.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.StringE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.code.M;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;

@Service ("PageInfoService")
public class PageInfoService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public ModelAndView doService (Object parameter, Object extendParameter) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/category/page/page_info");
		
		Map pageInfo = (Map) dao.getSingleData("page.pageInfo", parameter);
		
		mav.addObject("taskId", pageInfo.get("taskId"));
		mav.addObject("pageId", pageInfo.get("pageId"));
		mav.addObject("pageName", pageInfo.get("pageName"));
		mav.addObject("description", pageInfo.get("description"));
		mav.addObject("linkUrl", pageInfo.get("linkUrl"));
		mav.addObject("createTime", pageInfo.get("createTime"));
		mav.addObject("updateTime", pageInfo.get("updateTime"));

		return mav;
	}
	
}
