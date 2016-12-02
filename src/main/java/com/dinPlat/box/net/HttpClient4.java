package com.dinPlat.box.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;  

/**
 * HttpClient 4.x (apache commons에서 별도 project로 나와  httpcomponents로 생성 됨) 
 * - Apache Gruop에서 제공하는 HTTP클라이언트
 * - 브라우져을 만들만큼 방대하고 유연한 API 제공 (안정적, 별다른 버그 없음)
 * - 너무 방대해서 유지보수와 성능 개량에 어려움이 있음. 기존 Application과 호환성을 유지하기 위해 너무 많은 사항을 고려해야 함. (Android에서는 더이상 지원하지 않음)
 * @author gangboss
 *
 */
public class HttpClient4 {

	public static final String REQ_JSON = "JSON";
	public static final String REQ_PARAM = "PARAMETER";
	public static final String HTTP = "HTTP";
	public static final String HTTPS = "HTTPS";
	
	
	/**
	 * Post 방식 호출 후 String 결과를 리턴받는 Method.
	 * @param url
	 * @param header
	 * @param param
	 * @param encoding
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static String executePost (String protocol, String url, Map header, Map param, String encoding, int connectTimeout, int readTimeout, String reqType) throws Exception {	
		String result = "";
		
		/** 1. create HttpClient */
		HttpClient httpClient = null;
		if (protocol.equals(HTTP)) {
			httpClient = HttpClientBuilder.create().build();
		} else {
			httpClient = getHttpsClient();
		}
		HttpPost post = getHttpPost (url, connectTimeout, readTimeout);

		
		/** 2. set headet */
		setPostHeader(post, header);
		
		
		/** 3. set parameter */
		if (reqType.equals(REQ_PARAM)) {
			setPostRequestParameter(post, param, encoding);			// parameter 전달 방식
		} else if (reqType.equals(REQ_JSON)) {
			setPostRequestBody (post, param, encoding);				// Body에 JSON 데이터를 set하는 방식
		}
		
		
		/** 4. executePost */
		try{
			result = execute (httpClient, post, encoding);
		} catch (IOException e) {
			throw e;
		} catch(Exception e){	
			throw e;
		} finally{
			if (post != null) post.releaseConnection();
		}
		
		return result; 
	}
	
	
	/**
	 * URL과 환경설정이 된 PostMehtod를 반환하는 Method.
	 * @param url
	 * @param header
	 * @param param
	 * @param encoding
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws Exception
	 */
	public static HttpPost getHttpPost (String url, int connectTimeout, int readTimeout) throws Exception {
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).build();
		post.setConfig(requestConfig);
		
		// parameter set

		return post;
	}
	
	
	/**
	 * 
	 * @param post
	 * @param header
	 * @throws Exception
	 */
	public static void setPostHeader (HttpPost post, Map header) throws Exception {
		
		Iterator itHeader = (Iterator)header.entrySet().iterator();
		while (itHeader.hasNext()) {
			Map.Entry entry = (Map.Entry)itHeader.next();
		    post.setHeader((String)entry.getKey(), (String)entry.getValue());
		}
	}
	
	
	/**
	 * 
	 * @param post
	 * @param param
	 * @param encoding
	 * @throws Exception
	 */
	public static void setPostRequestParameter (HttpPost post, Map param, String encoding) throws Exception {
		
	    List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
	    Iterator itParam = (Iterator)param.entrySet().iterator();
	    while (itParam.hasNext()) {
	    	Map.Entry entry = (Map.Entry)itParam.next();
	    	paramValues.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
	    }
	    if (encoding == null) {
	    	post.setEntity(new UrlEncodedFormEntity(paramValues));
	    } else {
	    	post.setEntity(new UrlEncodedFormEntity(paramValues, encoding));
	    }
	}
	
	
	/**
	 * 
	 * @param post
	 * @param param
	 * @param encoding
	 * @throws Exception
	 */
	public static void setPostRequestBody (HttpPost post, Map param, String encoding) throws Exception {
		
		StringEntity reqEntity = null;	
		if (encoding == null) {
			reqEntity = new StringEntity(JSONObject.fromObject(param).toString());
		} else {
			reqEntity = new StringEntity(JSONObject.fromObject(param).toString(), encoding);
		}
		post.setEntity(reqEntity);
		
	}
	
	
	/**
	 * executePost 실행 후 String을 반환하는 Method.
	 * @param httpClient
	 * @param post
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String execute (HttpClient httpClient, HttpPost post, String encoding) throws Exception {
		String result = "";
		
		HttpResponse response = httpClient.execute(post);
		
		int status = response.getStatusLine().getStatusCode();
		if(status == HttpStatus.SC_OK){
			HttpEntity entity = response.getEntity();
			if (encoding == null) {
				result = EntityUtils.toString(entity);
			} else {
				result = EntityUtils.toString(entity, encoding);
			}
		}else {
			throw new Exception(HttpClient4.class + " : Fail to httpClient.executeMethod. [HttpStatus : " + status + "]");
		}
		
		return result;
	}
	
	

	/**
	 * SSL 통신시 인증서를 회피하는 설정을 담은 HttpClient를 리턴하는 Method.
	 * @return
	 * @throws Exception
	 */
	private static HttpClient getHttpsClient () throws Exception {
		
		/** 1. create HttpClient */
		SSLContext sslContext = SSLContext.getInstance("SSL");

		// set up a TrustManager that trusts everything
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
		            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                    return null;
		            }

					public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws java.security.cert.CertificateException {
						// TODO Auto-generated method stub
						
					}

					public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
							throws java.security.cert.CertificateException {
						// TODO Auto-generated method stub
						
					}
		} }, new SecureRandom());

		// 공통화를 하려고 HttpClient로 casting함.
//		CloseableHttpClient httpClient = 
//				HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();
		HttpClient httpClient = 
				(HttpClient) HttpClients.custom().setSSLSocketFactory(new SSLSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)).build();
		
		return httpClient;
	}
	

	/**
	 * HTTP 통신 (GET 방식 지원)
	 * @param url
	 * @param header
	 * @param param
	 * @param encoding
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Object executeGet (String url, Map header, Map param, String encoding, int connectTimeout, int readTimeout) throws Exception {
		String result = "";
		
		/** 1. create HttpClient */
		HttpClient httpClient = HttpClientBuilder.create().build();
	    List<NameValuePair> paramValues = new ArrayList<NameValuePair>();
	    Iterator itParam = (Iterator)param.entrySet().iterator();
	    while (itParam.hasNext()) {
	    	Map.Entry entry = (Map.Entry)itParam.next();
	    	paramValues.add(new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue()));
	    }
	    HttpGet get;
	    if (param.isEmpty()) {
	    	get = new HttpGet(url);
	    } else {
	    	get = new HttpGet(url+"?"+URLEncodedUtils.format(paramValues, encoding));
	    }

		
		/** 2. connection timeout set */
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(readTimeout).setConnectTimeout(connectTimeout).build();
		get.setConfig(requestConfig);
		
	    
	    /** 3. header set */
	    Iterator itHeader = (Iterator)header.entrySet().iterator();
	    while (itHeader.hasNext()) {
	    	Map.Entry entry = (Map.Entry)itHeader.next();
	    	get.setHeader((String)entry.getKey(), (String)entry.getValue());
	    }

	    
		/** 4. execute */
		try{
			HttpResponse response = httpClient.execute(get);
			
			int status = response.getStatusLine().getStatusCode();
			if(status == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				result = EntityUtils.toString(entity);
			}else {
				throw new Exception(HttpClient4.class + " : Fail to httpClient.executeMethod. [HttpStatus : " + status + "]");
			}
		}catch (IOException e) {
			throw e;
		}catch(Exception e){	
			throw e;
		}finally{
			if (get != null) get.releaseConnection();
		}
       
		return result;
	}
	
}
