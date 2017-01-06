package com.mini.html5.page;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;

@Service ("PagingService")
public class PagingService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map)extendParameter).get("tr");
		
		if (tr.equals("paging")) {
			mav.setViewName("/html5/page/paging");
		} else if (tr.equals("dragdrop")) {
			mav.setViewName("/html5/page/dragdrop");
		}
		
		return mav;
	}

}
