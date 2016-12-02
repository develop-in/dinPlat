package com.dinPlat.box.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;

import net.sf.json.JSONObject;

/** 
 * HttpURLConnection (stream 전송 방식)
 * - Java의 기본적인 HTTP 연동 방식
 * - 일반적인 목적으로 사용되는 가벼운 HTTP 클라이언트
 * - 핵심적인 API만 갖고 있어서 사용과 유지보수에 편리 함.
 * [버그]
 * - HttpURLConnection에서 커넥션풀을 사용할 떄 inputStream을 다 읽지 않고 close()를 하게 되면
 *   커넥션이 재활용이 될때 읽지 못한 데이터가 남아 있게 된다.
 * @author gangboss
 *
 */
public class HttpUrl {

	public static final String HC_POST_METHOD = "POST";
	public static final String HC_GET_METHOD = "GET";
	public static final String HC_JSON_HEADER = "application/json; charset=UTF-8";
	
	/**
	 * HTTP Post Method
	 * @param uri
	 * @param header
	 * @param param
	 * @param encoding
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Object connectPost (String uri, Map header, Map param, String encoding, int connectTimeout, int readTimeout) throws Exception {
		String result = "";
		int responseCode = 0;
		
		HttpURLConnection conn = null;
		
		PrintWriter send = null;
		BufferedReader receive = null;
		
		try {
			/** 1. connection create */
			URL url = new URL (uri);
			conn = (HttpURLConnection)url.openConnection();
			
			/** 2. set to property */
			conn.setRequestMethod(HC_POST_METHOD);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setDoInput(true);							// 서버로부터 메세지를 받을수 있도록 설정. Default=true
			conn.setDoOutput(true);							// 서버로부터 메세지를 보낼수 있돌고 설정. Default=false, true로 설정하면 POST로 정의 됨. GET은 써주지 않음.
			conn.setUseCaches(false);						// true일 경우 서버에 저장된 문서가 변경되지 않으면 다운로드 하지 않고 로컬캐쉬에 저장된 문서를 보여준다. 

		    /** 3. header set */
		    Iterator itHeader = (Iterator)header.entrySet().iterator();
		    while (itHeader.hasNext()) {
		    	Map.Entry entry = (Map.Entry)itHeader.next();
		    	conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
		    }
		    
		    /** 4. Paramter Set 
		     * 		Post Method일 경우 Key, Value 형태의 String 구문을 만들어 Output Stream으로 서버에 전송한다.
		     * */
		    String paramString = "";
		    int i=0;
		    Iterator itParam = (Iterator)param.entrySet().iterator();
		    while (itParam.hasNext()) {
		    	Map.Entry entry = (Map.Entry)itParam.next();
		    	if (i>0) paramString += "&";
		    	paramString += (String)entry.getKey() + "=" + (String)entry.getValue();
		    	i++;
		    }
		    if (encoding == null) {
		    	paramString = URLEncoder.encode(paramString);
		    } else {
		    	paramString = URLEncoder.encode(paramString, encoding);
		    }
		    
		    
			/** 5. send */
		    if (encoding == null) {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
		    } else {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), encoding));
		    }
            send.write(paramString);
            send.flush();
            send.close();
            responseCode = conn.getResponseCode();				// execute를 처리하는 역할
			
            /** 6. receive */
			if (responseCode == HttpURLConnection.HTTP_OK) {
				if (encoding == null) {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				}

	           	String inStr = "";
	           	StringBuffer sb = new StringBuffer();
	           	while ((inStr = receive.readLine()) != null) {
	           		sb.append(inStr);
	           	}
	           	
	           	result = sb.toString();
			}
            
		} catch (Exception e){
			throw new Exception(HttpUrl.class + " : Fail into connectPost Method. [HttpStatus : " + responseCode + "]");
		} finally {
			if (send != null) send.close();
			if (receive != null) receive.close();
			/**
			 * 명시적으로 disconnect 처리를 하는게 좋음.
			 * 아파치 웹 서버가 KeepAlive 가 Off로 되어 있으면, 클라이언트에서 FIN을 보내서 종료하게 되고, KeepAlive가 On이면 대기하게 된다.
			 * 웹 서버가 KeepAlive가 On 상태인대, 계속 요청이 들어오게 되면, 연결이 많아져 점점 처리를 못하게 되어 ACK 통신이 어려워져 timeout 되어 FIN_WAIT로 이동하게 된다.  
			 */
			if (conn != null) conn.disconnect();		
		}
		
		return result;
	}
	
	
	/**
	 * HTTP Get Method
	 * @param uri
	 * @param header
	 * @param param
	 * @param encoding
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Object connectGet (String uri, Map header, Map param, String encoding, int connectTimeout, int readTimeout) throws Exception {

		String result = "";
		int responseCode = 0;
				
		HttpURLConnection conn = null;
		
		BufferedReader in = null;
		
		try {
			/** 1. connection create */
			URL url = null;
			
			Set keySet = param.keySet();
			Object keyStr[] = keySet.toArray();

			// Parameter 설정
			String parameter = "";
			for(int i=0;i<keyStr.length;i++) {
				String key = (String)keyStr[i];
				String value = (String)param.get(key);
				if (i>0) parameter = parameter + "&";
				parameter = parameter + key + "=" + value;
			}
			
			url = new URL (uri+parameter);
			conn = (HttpURLConnection)url.openConnection();

			
			/** 2. set to property */
			conn.setRequestMethod(HC_GET_METHOD);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setUseCaches(false);						  

			
		    /** 3. header set */
		    Iterator itHeader = (Iterator)header.entrySet().iterator();
		    while (itHeader.hasNext()) {
		    	Map.Entry entry = (Map.Entry)itHeader.next();
		    	conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
		    }
		    
		    
		    /** 4. send */
		    responseCode = conn.getResponseCode();			// execute를 처리하는 역할
			
            /** 5. receive */
			if (responseCode == HttpURLConnection.HTTP_OK) {
				if (encoding == null) {
					in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					in = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				}

	           	String inStr = "";
	           	StringBuffer sb = new StringBuffer();
	           	while ((inStr = in.readLine()) != null) {
	           		sb.append(inStr);
	           	}
	           	
	           	result = sb.toString();
			}
		} catch (Exception e){
			throw new Exception(HttpUrl.class + " : Fail into connectGet Method. [HttpStatus : " + responseCode + "]");
		} finally {
			if (in != null) in.close();
			if (conn != null) conn.disconnect();
		}
		return result;
	}
	
	
	
	/**
	 * HTTPS POST JSON
	 * @param uri
	 * @param param
	 * @param encoding
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Object connectJSON (String uri, Map header, Map param, String encoding, int connectTimeout, int readTimeout) throws Exception {
		String result = "";
		
		int responseCode = 0;
		
		HttpURLConnection conn = null;

		PrintWriter send = null;
		BufferedReader receive = null;
		
		try {
			
			/** 1. connection create */
			URL url = new URL (uri);
			conn = (HttpURLConnection)url.openConnection();
			
			/** 2. set to property */
			conn.setRequestMethod(HC_POST_METHOD);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setDoInput(true);							// 서버로부터 메세지를 받을수 있도록 설정. Default=true
			conn.setDoOutput(true);							// 서버로부터 메세지를 보낼수 있돌고 설정. Default=false, true로 설정하면 POST로 정의 됨. GET은 써주지 않음.
			conn.setUseCaches(false);						// true일 경우 서버에 저장된 문서가 변경되지 않으면 다운로드 하지 않고 로컬캐쉬에 저장된 문서를 보여준다.
			
			
			/** 3. header set */
		    Iterator itHeader = (Iterator)header.entrySet().iterator();
		    while (itHeader.hasNext()) {
		    	Map.Entry entry = (Map.Entry)itHeader.next();
		    	conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
		    }
	    
		    
			/** 4. send */
		    if (encoding == null) {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
		    } else {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), encoding));
		    }
            send.write(JSONObject.fromObject(param).toString());
            send.flush();
            send.close();
            
            responseCode = conn.getResponseCode();				// execute플 처리하는 역할.

            
            /** 5. receive */
			if (responseCode == HttpURLConnection.HTTP_OK) {
				if (encoding == null) {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				}

	           	String inStr = "";
	           	StringBuffer sb = new StringBuffer();
	           	while ((inStr = receive.readLine()) != null) {
	           		sb.append(inStr);
	           	}
	           	
	           	result = sb.toString();
			}
            
		} catch (Exception e){
			e.printStackTrace();
			throw new Exception(HttpUrl.class + " : Fail into connectSSLJSON Method. [HttpStatus : " + responseCode + "]");
		} finally {
			if (send != null) send.close();
			if (receive != null) receive.close();
			if (conn != null) conn.disconnect();
		}

		return result;
	}
    
	
	/**
	 * HTTPS POST JSON
	 * @param uri
	 * @param param
	 * @param encoding
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static Object connectJSONSSL (String uri, Map header, Map param, String encoding, int connectTimeout, int readTimeout) throws Exception {
		String result = "";
		
		int responseCode = 0;
		
		HttpsURLConnection conn = null;

		PrintWriter send = null;
		BufferedReader receive = null;
		
		try {
			
			/** 1. connection create */
			URL url = new URL (uri);
			
			SSLContext sslContext = SSLContext.getInstance("TLS");

			// set up a TrustManager that trusts everything
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
			            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			                    return new java.security.cert.X509Certificate[] 
			                    		{
			                    		};
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

			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			
			conn = (HttpsURLConnection)url.openConnection();
			
			conn.setHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String arg0, SSLSession arg1) {
					// TODO Auto-generated method stub
					return true;
				}
				
			});
			
			/** 2. set to property */
			conn.setRequestMethod(HC_POST_METHOD);
			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);
			conn.setDoInput(true);							// 서버로부터 메세지를 받을수 있도록 설정. Default=true
			conn.setDoOutput(true);							// 서버로부터 메세지를 보낼수 있돌고 설정. Default=false, true로 설정하면 POST로 정의 됨. GET은 써주지 않음.
			conn.setUseCaches(false);						// true일 경우 서버에 저장된 문서가 변경되지 않으면 다운로드 하지 않고 로컬캐쉬에 저장된 문서를 보여준다.
			
			
			/** 3. header set */
		    Iterator itHeader = (Iterator)header.entrySet().iterator();
		    while (itHeader.hasNext()) {
		    	Map.Entry entry = (Map.Entry)itHeader.next();
		    	conn.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
		    }
	    
		    
			/** 4. send */
		    if (encoding == null) {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream()));
		    } else {
		    	send = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), encoding));
		    }
            send.write(JSONObject.fromObject(param).toString());
            send.flush();
            send.close();
            
            responseCode = conn.getResponseCode();				// execute플 처리하는 역할.

            /** 5. receive */
			if (responseCode == HttpURLConnection.HTTP_OK) {
				if (encoding == null) {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					receive = new BufferedReader(new InputStreamReader(conn.getInputStream(), encoding));
				}

	           	String inStr = "";
	           	StringBuffer sb = new StringBuffer();
	           	while ((inStr = receive.readLine()) != null) {
	           		sb.append(inStr);
	           	}
	           	
	           	result = sb.toString();
			}
            
		} catch (Exception e){
			e.printStackTrace();
			throw new Exception(HttpUrl.class + " : Fail into connectSSLJSON Method. [HttpStatus : " + responseCode + "]");
		} finally {
			if (send != null) send.close();
			if (receive != null) receive.close();
			if (conn != null) conn.disconnect();
		}

		return result;
	}
}

