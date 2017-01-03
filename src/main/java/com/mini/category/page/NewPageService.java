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

@Service ("NewPageService")
public class NewPageService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public ModelAndView doService (Object parameter, Object extendParameter) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr == null) {
			mav.setViewName("/category/page/page_create");
			mav.addObject("taskId", ((Map)parameter).get("taskId"));
		} else {
			if (tr.equals(C.TR_INS)) {
				int pageId = (Integer)	dao.getSingleData("page.maxPage", parameter);
				pageId++;
				((Map)parameter).put("pageId", pageId);
				
				dao.insert("page.insert_dp_task_page", parameter);
				mav.setViewName("/category/page/page_create_result");
				setAddObject (mav, (Map)parameter);
			} else if (tr.equals(C.TR_EDT)) {
				Map pageInfo = (Map) dao.getSingleData("page.pageInfo", parameter);
				mav.setViewName("/category/page/page_edit");
				setAddObject (mav, pageInfo);
			} else if (tr.equals(C.TR_UPD)) {
				dao.update("page.update_dp_task_page", parameter);
				mav.setViewName("/category/page/page_create_result");
				setAddObject (mav, (Map)parameter);
			}
		}

		return mav;
	}
	
	
	private void setAddObject (ModelAndView mav, Map pageInfo) throws Exception {
		mav.addObject("taskId", pageInfo.get("taskId"));
		mav.addObject("pageId", pageInfo.get("pageId"));
		mav.addObject("pageName", pageInfo.get("pageName"));
		mav.addObject("description", pageInfo.get("description"));
		mav.addObject("linkUrl", pageInfo.get("linkUrl"));
	}
	
	
}
