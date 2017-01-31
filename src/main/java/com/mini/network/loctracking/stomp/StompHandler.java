package com.mini.network.loctracking.stomp;

import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.svc.log.DropLog;
import com.mini.common.service.ServiceParent;

@Controller
public class StompHandler {

	/*
	 * spring의 @Controller를 이용해 처리 하기에 Spring의 MVC 모델과 흡사하지만 실제적으로는 stomp의 connect로 client와 server간 세션이 
	 * 맺어지게 되면 (/stomp/server/info에 의해) 이후 진행은 @MessageMapping에 의해 Spring의 Dispatcher를 타지 않기에 
	 * HandlerInterceptorAdapter에도 잡히지 않고 session내에서만 통신이 이루어 진다.
	 */
	@Autowired
	private SimpMessagingTemplate template;
	
	/**
	 * 전체 대상 Noti
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/noti")				// Application Destination ( "/send" 이후의 name - clinet에서 지정됨 )
	@SendTo({"/noti/message"})				// client subscribe의 destination
	public String sendToAllMessage (String message) throws Exception {
		DropLog.log(">> STOMP : notification");
		return message;
	}

	
	/**
	 * group, private 대상
	 * @param messageBroker
	 * @param message
	 * @throws Exception
	 */
	@MessageMapping("/{messageBroker}/{id}")		// Application Destination을 message broker name과 동일하게 지정하면 편리 함.
	public void sendToPrivateMessage (@DestinationVariable String messageBroker, @DestinationVariable String id, String message) throws Exception {
		DropLog.log(">> STOMP messageBroker : " + messageBroker + ", id : " + id);
		
		// 특정 group, 특정 사용자에게만 메세지를 보내기 위해 convertAndSend 사용
		template.convertAndSend("/" + messageBroker + "/" + id, message);
	}
	
	
	/*
	 * Spring MVC로 Direct로 호출하여 처리도 가능하다. (Spring Dispatcher로 타게 됨)
	 */
	@RequestMapping("/stomp/servetToClient")
	public ModelAndView sendServerToClientMessage(String message) {
		DropLog.log(">> STOMP change message : " + message);
		
		template.convertAndSend("/change/message", message);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/common/result");
		mav.addObject("result", message);
		
		return mav;
	}
}
