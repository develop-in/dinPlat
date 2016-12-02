package com.dinPlat.svc.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
//import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AnnotationInterceptor extends HandlerInterceptorAdapter {

		
		private Logger logger = LoggerFactory.getLogger(AnnotationInterceptor.class.toString());
		public int CONTENT_LENGTH_MAX = 1024 * 1024 * 10;
		
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception	{
			String remoteAddr 	= request.getRemoteAddr();
			String protocol 	= request.getProtocol();
			boolean secure 		= request.isSecure();
			int contentLength 	= request.getContentLength();

			String message = "Request URL : " + request.getRequestURL().toString() + ", REMOTE ADDRESS : "+remoteAddr+", PROTOCOL : "+protocol
					+", SECURE : "+secure+", CONTENT LENGTH : "+contentLength;
			logger.info(message);
			
			
			// 로그인이 필요한 request에 대해서 Session 확인
			if (checkCertURI(request)) {
				HttpSession session = request.getSession();
				if (session.getAttribute("cup") == null) {
					logger.info("session null.....................");
					response.sendRedirect(request.getContextPath() + "/login");
					return false;
				}
			}
			
			
			
//			DropLog.dropLog(logger, Constant.INFO_MODE, request.getRequestURL().toString(), HandlerInterceptorAdapter.class, message);
			
		
			if ( contentLength > CONTENT_LENGTH_MAX )		{
				logger.info( "CONTENT LENGTH OVER!!!" );
				return false;
			}
			
			Enumeration<?> headerNames = request.getHeaderNames();
			StringBuilder sb = new StringBuilder();
			
			while(headerNames.hasMoreElements()){
				String headerName = (String) headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				sb.append("\n"+headerName+"="+headerValue);
			}
			
			logger.debug("CLIENT HEADER INFO:"+sb.toString());
			
			return true;
		}
		
		
		/*
		 * Session Check가 필요한 API Session 확인
		 */
		private boolean checkCertURI (HttpServletRequest request) {
			String uri = request.getRequestURI();
			String contextPath = request.getContextPath() + "/";
			
			int beginIndex = uri.indexOf(contextPath) + contextPath.length();
			int endIndex = uri.indexOf("/", beginIndex);
			
			if (endIndex < 0) {
				return false;
			}

			String directory = uri.substring(beginIndex, endIndex);
			
			String certList[] = {"friend", "messenger"};
			
			for (int i=0; i<certList.length; i++) {
				String u = certList[i];
				if (directory.equals(u)) return true;
			}
			
			return false;
		}
		
}
