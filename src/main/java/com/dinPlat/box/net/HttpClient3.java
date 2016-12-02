package com.dinPlat.box.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;

import com.dinPlat.box.ds.JsonE;

import net.sf.json.JSONObject;  

/**
 * HttpClient 3.x (apache commons 프로젝트 하위 모듈) 
 *
 * @author gangboss
 *
 */
public class HttpClient3 {

	
	/**
	 * PostMethod로 통신하여 JSON 데리터를 반환하는 Method.
	 * @param url
	 * @param header
	 * @param param
	 * @param encoding
	 * @param connectTimeout
	 * @param readTimeout
	 * @param SSLPort			: SSL통신이 아닐경우 0.
	 * @return
	 * @throws Exception
	 */
	public static JSONObject executePostMethodJSON (String url, Map header, Map param, String encoding, int connectTimeout, int readTimeout, int SSLPort) throws Exception {
		
		/** 1. create HttpClient */
		HttpClient httpClient = new HttpClient();
		PostMethod post = getPostMethod(httpClient, url, connectTimeout, readTimeout);
		
		/** 2. Http Request Header & body Set */
		setPostHeaderAndBodyJSON (post, header, param, encoding);
		
		/** 3. execute */
		String result=null;
		try {
			if (SSLPort > 0) {
				Protocol.registerProtocol("https", new Protocol("https", new EasySSLProtocolSocketFactory(), SSLPort));	
			}
			result = execute (httpClient, post, encoding);
		} catch (Exception e) {
			if (post != null) post.releaseConnection();
		}
		
		return JSONObject.fromObject(result.trim());
	}

	
	/**
	 * URL과 환경설정이 된 PostMehtod를 반환하는 Method.
	 * @param url
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static PostMethod getPostMethod (HttpClient httpClient, String url, int connectTimeout, int readTimeout) throws Exception {
		
		PostMethod post = new PostMethod(url);
		post.getParams().setSoTimeout(readTimeout);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectTimeout);

		return post;
	}
	
	
	/**
	 * Post 통신일경우 Http Request Header와 Body에 값을 설정한는 Method.
	 * @param post
	 * @param header
	 * @param param
	 * @param encoding
	 * @throws Exception
	 */
	public static void setPostHeaderAndBodyJSON (PostMethod post, Map header, Map param, String encoding) throws Exception {
	    
		// Request Header
		Iterator itHeader = (Iterator)header.entrySet().iterator();
	    while (itHeader.hasNext()) {
	    	Map.Entry entry = (Map.Entry)itHeader.next();
	    	post.setRequestHeader((String)entry.getKey(), (String)entry.getValue());
	    }
	    
	    // Request Body
	    post.setRequestBody(JsonE.changeJsonString(param));
	}
	
	
	/**
	 * 
	 * @param httpClient
	 * @param post
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String execute (HttpClient httpClient, PostMethod post, String encoding) throws Exception {
		StringBuilder sb = new StringBuilder();
		
		int status = httpClient.executeMethod(post);
		
		if(status == HttpStatus.SC_OK){
			BufferedReader br;
			
			if (encoding == null) {
				br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream(), encoding));
			}
			
			String readLine;
			while ((readLine = br.readLine()) != null){
				sb.append(readLine);
			}
		}else {
			throw new Exception(HttpClient3.class + " : Fail to httpClient.executeMethod. [HttpStatus : " + status + "]");
		}
		
		return sb.toString();
	}
	
}
