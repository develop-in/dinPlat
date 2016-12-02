package com.dinPlat.box.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class NetworkEnv {

	/**
	 * HttpServletRequest 객체를 통해 Remote로 접속된 IP를 얻어 온다. (실제 IP를 얻지는 못한다.)
	 * @param request
	 * @return
	 */
	public static String getHttpServletRequestIp (HttpServletRequest request) {
		
		// Web Server, WAS, L4, Proxy 종류에 상관없이 IP를 구하는 방법
		String ip = request.getHeader("X-Forwarded-For");							// Apache Road Balancer를 거칠 경우 
//		System.out.println("X-Forwarded-For ip : " + ip);
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 	// 
		    ip = request.getHeader("Proxy-Client-IP"); 
//		    System.out.println("Proxy-Client-IP ip : " + ip);
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 	// Web Logic WAS를 거칠 경우
		    ip = request.getHeader("WL-Proxy-Client-IP"); 
//		    System.out.println("WL-Proxy-Client-IP ip : " + ip);
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 	// 
		    ip = request.getHeader("HTTP_CLIENT_IP"); 
//		    System.out.println("HTTP_CLIENT_IP ip : " + ip);
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 	// Proxy를 거칠 경우
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//		    System.out.println("HTTP_X_FORWARDED_FOR ip : " + ip);
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getRemoteAddr(); 
//		    System.out.println("getRemoteAddr ip : " + ip);
		}

		return ip;
	}
	
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public static int getHttpServletRequestPort (HttpServletRequest request) {
		return request.getRemotePort();
	}

	
	/**
	 * Network에 접속된 IP 구하기 (유/무선 모두 있을경우 실제 접속된 IP 구하기)
	 * 
	 * @return
	 */
	public static String getRemoteLocalIp () {
	
		String ip = null;
		
		Enumeration<NetworkInterface> nienum = null;
		try {
			nienum = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		while (nienum.hasMoreElements()) {
			NetworkInterface ni = nienum.nextElement();
			Enumeration<InetAddress> kk = ni.getInetAddresses();
            
			while (kk.hasMoreElements()) {
				InetAddress inetAddress = (InetAddress) kk.nextElement();
        		String address = inetAddress.getHostAddress();
        		int dot = address.compareTo(".");
        		if (dot == 3)	ip = address;
			}
		}
    
		return ip;
	}

	
	/**
	 * HttpServletRequest에서 원하는 값을 읽어오는 method
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getRequestHeaderValue(HttpServletRequest request, String key) {
		Enumeration<?> headerNames = request.getHeaderNames();
		StringBuilder sb = new StringBuilder();
		
		while(headerNames.hasMoreElements()){
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			
			if (headerName.equals(key)) return headerValue;
			
//			sb.append("\n"+headerName+"="+headerValue);
		}
		
//		logger.info("CLIENT HEADER INFO:"+sb.toString());
		
		return null;
	}
	
	
	
	/**
	 * HttpServletRequest Header값을 모두 읽어 들인다.
	 * 
	 * @param request
	 * @return
	 */
	public static StringBuilder getRequestHeaderValues(HttpServletRequest request) {
		Enumeration<?> headerNames = request.getHeaderNames();
		StringBuilder sb = new StringBuilder();
		
		while(headerNames.hasMoreElements()){
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			sb.append("\n"+headerName+"="+headerValue);
		}
		
		return sb;
	}
}
