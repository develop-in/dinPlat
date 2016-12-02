package com.dinPlat.box.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


/**
 * 소스참고 : http://somnusnote.tistory.com/entry/HttpClient-Connection-Pooling
 * 클라이언트에서 서버로 HTTP 통신을 할 경우 일반적인 방법으로 연결을 맺고 닫게 되면 Application에서 리소스를 해제 하여도 실제로는 어느정도 TIME-WAIT이 발행하기에
 * Transaction이 몰릴 경우 File Description이 Full로 차게 되어 더이상 연결을 맺지 못하게 된다.
 * 이런 현상을 방지하기 위해 Pool에 connection 갯수를 제한하도록 httpClient에서 기능 제공을 한다.
 * Pool을 사용할 때마다 항상 주의할 것은 역시 반환을 꼭 해 줘야 한다는 것!!!
 * Exception이 발생하면 abort를 호출해서 정리해 주고, finally에선 항상 EntityUtils.consumeQuietly를 호출해서 리소스를 반납하게 해 준다.
 * @author scott
 *
 */
public class HttpConnectionPool {

	private static PoolingHttpClientConnectionManager connectionManager = null;
	
	public static final int MAX_TOTAL = 100;
	public static final int MAX_ROUTE = 20;
	public static final String REQUEST_TYPE_JSON = "JSON";
	public static final String REQUEST_TYPE_XML = "XML";

	/**
	 * HttpConnectionPool에서 http세션을 가져와서 POST 방식으로 연동하는 Mehtod로 String으로 응답 한다.
	 * @param url
	 * @param header
	 * @param parameter
	 * @param requsetParameterType	: JSON, XML
	 * @param readTimeout
	 * @param connectionTimeout
	 * @return
	 * @throws Exception
	 */
	public static String postString (String url, Map header, Map parameter, String requsetDataType, int readTimeout, int connectionTimeout) throws Exception {

		HttpClient client = null;
		HttpResponse response = null;
		HttpPost post = null;
		
		String result = "";
		
		try {

			/** 0. pool 가져오기 */
			client = getHttpClient();
	
			/** 1. TIMEOUT 설정 */
			RequestConfig.Builder config = RequestConfig.custom();
			config.setConnectTimeout(connectionTimeout);
			config.setSocketTimeout(connectionTimeout);
			config.setConnectionRequestTimeout(readTimeout);
	
			/** 2. Header 설정 */
			post = new HttpPost(url);
			if (header != null) {
				Set set = header.keySet();
				Iterator<String> iter = set.iterator();
				while(iter.hasNext()) {
					String headerName = iter.next();
					String headerValue = (String) header.get(headerName);
					post.setHeader(headerName, headerValue);
				}
			}
			post.setConfig(config.build());
	
			/** 3. 요청 param 셋팅 */
			String reqParam=null;
			if (requsetDataType != null) {
				if (requsetDataType.equals(REQUEST_TYPE_JSON)) {
					reqParam = JSONObject.fromObject(parameter).toString();
				}
			}
	
			HttpEntity entity = new StringEntity(reqParam, "UTF-8");
			post.setEntity(entity);
	
			/** 4. 연동 */
			response = client.execute(post);
			
			/** 5. 응답값 읽기 */
			if(response.getStatusLine().getStatusCode() == 200){
				InputStream is = null;
				BufferedReader brd = null;

				/** 5. 응답값 처리 */
				try{
					Charset ch = Charset.forName("UTF-8");
					is = response.getEntity().getContent();
					brd = new BufferedReader(new InputStreamReader(is, ch));

			    	String line = "";
			    	StringBuffer deliverySb = new StringBuffer();
			    	while((line = brd.readLine()) != null){
			    		deliverySb.append(line);
			    	}
			    	result = deliverySb.toString();
				}catch(Exception e){
					e.printStackTrace();
					throw e;
				}finally{
					if(brd != null){
						try{brd.close();}catch(Exception e){};
					}

					if(is != null){
						try{is.close();}catch(Exception e){};
					}
				}
			} 
		}catch(IOException ie){
			ie.printStackTrace();
			abort(post);
			throw ie;
		}catch(Exception e){
			e.printStackTrace();
			abort(post);
			throw e;
		} finally {
			release(response);
		}

		return result;
	}
	
	
	public static synchronized HttpClient getHttpClient() {
		if (connectionManager == null) {
			connectionManager = new PoolingHttpClientConnectionManager();
			connectionManager.setMaxTotal(MAX_TOTAL);
			connectionManager.setDefaultMaxPerRoute(MAX_ROUTE);
		}
		
		HttpClient client = HttpClients.custom().setConnectionManager(connectionManager).build();
		
		PoolStats cntStates= connectionManager.getTotalStats();
//		System.out.println("[New] Available  = " +  cntStates.getAvailable() + " , Leased = " + cntStates.getLeased() + " , Pending = " + cntStates.getPending());
		
		return client;
	}
	
	// Request 리소스 제거
	public static void abort(HttpRequestBase httpRequest) {
		if (httpRequest != null) {
			try {
				httpRequest.abort();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	// Response 리소스 제거
	public static void release(HttpResponse httpResponse) {
		if (httpResponse != null && httpResponse.getEntity() != null) {
			EntityUtils.consumeQuietly(httpResponse.getEntity());
		}
	}
	



}
