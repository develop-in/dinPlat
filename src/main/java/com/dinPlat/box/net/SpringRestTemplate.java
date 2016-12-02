package com.dinPlat.box.net;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class SpringRestTemplate {

	/**
	 * Spring Framework에서 제공하는 RestTemplate을 이용한 HTTP 통시 Method. (String 결과값 리턴) 
	 * @param url
	 * @param header
	 * @param parameter
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static String exchangePostString (String url, 
										 Map header, 
										 Map parameter,
										 int connectTimeout,
										 int readTimeout) throws Exception {
		
	    /** 1. HTTP header set */
		HttpHeaders headers = new HttpHeaders();
		Iterator itHeader = (Iterator)header.entrySet().iterator();
	    while (itHeader.hasNext()) {
	    	Map.Entry entry = (Map.Entry)itHeader.next();
	    	headers.set((String)entry.getKey(), (String)entry.getValue());
	    }
	    headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	    
	    /** 2. request Body set */
	    HttpEntity entity = new HttpEntity(parameter, headers);
	   
	    ResponseEntity responseEntity;
	    
	    String responseData;
	    
	    try {
	    	RestTemplate restTemplate = getRestTemplate(connectTimeout, readTimeout);
	    	
	    	responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	    	String buff = (String) responseEntity.getBody();
    		responseData = new String(buff.getBytes("iso-8859-1"), "UTF-8");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	throw e;
	    } 
		
		return responseData;
	}
	
	
	/**
	 * restTemplate 객체를 받아오는 Method.
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
			
	public static RestTemplate getRestTemplate(int connectTimeout, int readTimeout) throws Exception {
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    	requestFactory.setConnectTimeout(connectTimeout);
    	requestFactory.setReadTimeout(readTimeout);
    	RestTemplate restTemplate = new RestTemplate(requestFactory);
    	
    	return restTemplate;
	}
	
}

