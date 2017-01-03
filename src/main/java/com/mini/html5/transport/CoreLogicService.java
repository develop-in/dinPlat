package com.mini.html5.transport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.code.C;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Service ("CoreLogicService")
public class CoreLogicService extends ServiceParent {
		
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("/html5/transport/corelogic");
		
		return mav;
	}

}
