package com.mini.network.loctracking;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;
import com.mini.network.loctracking.stomp.StompHandler;

@Service ("WebsocketService")
public class WebsocketService extends ServiceParent {
	
	@Autowired
	private StompHandler stomp;

	
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String tr = (String) ((Map) extendParameter).get("tr");
		
		if (tr == null) {
			mav.setViewName("/network/loctracking/websocket");
	
			List clanList = (List) dao.getListData("mini.clanList", null);
			
			mav.addObject("clanList", clanList);
			
			return mav;
		} else {
			String message = (String) ((Map) parameter).get("message");
			/*
			 *  Application 어느 지점에서든 STOMP를 통해 메세지를 전달 할수 있다.
			 *  StompHandler를 @Autowired하여 StompHandler의 mehtod를 호출하는 형태로 처리가 가능하다.
			 */
			stomp.sendServerToClientMessage(message);
			
			mav.setViewName("/common/result");
			mav.addObject("result", message);
			
			return mav;
		}
	}

}
