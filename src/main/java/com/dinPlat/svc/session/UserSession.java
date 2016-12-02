package com.dinPlat.svc.session;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UserSession {

	/**
	 * 세션 속성 설정
	 * @param request
	 */
	public static void setAttribute(HttpServletRequest request, Map attr) {
		Iterator iter = attr.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = (String)iter.next();
			request.getSession().setAttribute(key, attr.get(key));
		}
	}
	
	/**
	 * 세션의 저장된 값 얻어오기
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getAttribute(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}
	
	/**
	 * 세션에 설정된값 제거  (key이용)
	 * @param request
	 * @param key
	 */
	public static void removeAttribute(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}
	
	/**
	 * 세션의 유지 시간(초) 설정
	 * @param request
	 * @param second
	 */
	public static void setSessionTime(HttpServletRequest request, int second) {
		request.getSession().setMaxInactiveInterval(second);
	}
	
	/**
	 * Session ID 조회 (JSESSIONID)
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request) {
		return request.getSession().getId();
	}
	
	/**
	 * 세션 drop (로그아웃)
	 * @param request
	 */
	public static void removeSession(HttpServletRequest request) {
		request.getSession().invalidate();
	}
}
