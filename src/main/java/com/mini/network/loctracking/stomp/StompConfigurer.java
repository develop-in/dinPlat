package com.mini.network.loctracking.stomp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan (basePackages = 	{"com.mini.network.loctracking.stomp"})
public class StompConfigurer extends AbstractWebSocketMessageBrokerConfigurer {

	// End point 설정
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/stomp/server").withSockJS();				// Stomp 통신을 하기 위한 URI. Spring RequestMapping을 타지 않기 위해 /stomp라고 지칭 하였다. 
	}
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		/**
		 * Messsage Broker 정의. (noti : 전체 대상, group : 대화그룹 대상, private : 귓속말, error : websocket 시스템 오류 전용  
		 * - Message Broker 하나로 모두 처리할 수 있으나 시스템측면에서 기능별로 나누는게 좋음. (메모리 사용 및 독립적 구성 등.)
		 */
		registry.enableSimpleBroker("/noti", "/group", "/private", "/error");	  
		
		/**
		 * clinet --> server 메세지 전달시의 prefix
		 */
		registry.setApplicationDestinationPrefixes("/send");
	}

}
