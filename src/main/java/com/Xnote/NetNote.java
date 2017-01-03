package com.Xnote;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dinPlat.box.ds.ListE;
import com.dinPlat.box.file.FileE;
import com.dinPlat.box.net.HttpClient3;
import com.dinPlat.box.net.HttpClient4;
import com.dinPlat.box.net.HttpConnectionPool;
import com.dinPlat.box.net.HttpUrl;
import com.dinPlat.box.net.SpringRestTemplate;

import net.sf.json.JSONObject;

public class NetNote {

	/**
	 * dinPlat을 검증하기 위한 목적의 실행가능한 클래스.
	 * @param args
	 */
	public static void main(String[] args) {


	}
	
	
	
	
	// HTTP 연동 테스트
	private static void runHttpConnect () {
		httpConnectionPool();
		springRestTemplate();
		httpClient4();
		httpClientSSL4();
		httpURLConnectionJSON ();
		httpsURLConnectionJSONSSL ();
		httpClient3();
		httpClientSSL3();	
	}
	
	
	private static void httpClientSSL3 () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		parameter.put("hostUrl", "http://172.22.119.155:8080/swcs/ux2/coupon/list/cloRecommendCouponList.do");
		
		bodyParam.put("memberId", "41896992");
		bodyParam.put("partnerSiteId", "C277");
		
		parameter.put("rData", bodyParam);
		
		String url = "https://stg-syrup-appif.smartwallet.co.kr:27000/sw5/proto/rx78";
		
		try {
			Object result = HttpClient3.executePostMethodJSON(url, header, parameter, null, 10000, 10000, 443);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* commons HttpClient */
	private static void httpClient3 () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		
		bodyParam.put("mdn", "01032780597");
		bodyParam.put("userId", "3388352");
		bodyParam.put("saleType", "0");
		bodyParam.put("pageCount", 30);
		
		parameter.put("rData", bodyParam);
		
		String url = "http://pri-icpsw-stg.syrup.co.kr/syrupApi/ux5/coupon/issue/issueList.do";
		
		try {
			Object result = HttpClient3.executePostMethodJSON(url, header, parameter, null, 10000, 10000, 0);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* HttpsUrlConnection */
	private static void httpsURLConnectionJSONSSL () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		parameter.put("hostUrl", "http://172.22.119.155:8080/swcs/ux2/coupon/list/cloRecommendCouponList.do");
		
		bodyParam.put("memberId", "41896992");
		bodyParam.put("partnerSiteId", "C277");
		
		parameter.put("rData", bodyParam);
		
		String url = "https://stg-syrup-appif.smartwallet.co.kr:27000/sw5/proto/rx78";
		
		try {
			Object result = HttpUrl.connectJSONSSL(url, header, parameter, null, 10000, 10000);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* HttpUrlConnection */
	private static void httpURLConnectionJSON () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		
		bodyParam.put("mdn", "01032780597");
		bodyParam.put("userId", "3388352");
		bodyParam.put("saleType", "0");
		bodyParam.put("pageCount", 30);
		
		parameter.put("rData", bodyParam);
		
		String url = "https://pri-icpsw-stg.syrup.co.kr/syrupApi/ux5/coupon/issue/issueList.do";
		
		try {
			Object result = HttpUrl.connectJSON(url, header, parameter, null, 10000, 10000);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* httpcomponents HttpClient */
	private static void httpClient4 () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		
		bodyParam.put("mdn", "01032780597");
		bodyParam.put("userId", "3388352");
		bodyParam.put("saleType", "0");
		bodyParam.put("pageCount", 30);
		
		parameter.put("rData", bodyParam);
		
		String url = "http://pri-icpsw-stg.syrup.co.kr/syrupApi/ux5/coupon/issue/issueList.do";
		
		try {
			Object result = HttpClient4.executePost("HTTP", url, header, parameter, "UTF-8", 10000, 10000, "JSON");
//			Object result = HttpClient4.executeJSON (url, header, parameter, "UTF-8", 1000, 1000);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* httpcomponents HttpClient */
	private static void httpClientSSL4 () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		parameter.put("hostUrl", "http://172.22.119.155:8080/swcs/ux2/coupon/list/cloRecommendCouponList.do");
		
		bodyParam.put("memberId", "41896992");
		bodyParam.put("partnerSiteId", "C277");
		
		parameter.put("rData", bodyParam);
		
		String url = "https://stg-syrup-appif.smartwallet.co.kr:27000/sw5/proto/rx78";
		
		try {
			Object result = HttpClient4.executePost("HTTPS", url, header, parameter, "UTF-8", 10000, 10000, "JSON");
//			Object result = HttpClient4.executeSSLJSON(url, header, parameter, "UTF-8", 10000, 1000); 
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* RestTemplate */
	private static void springRestTemplate () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		
		bodyParam.put("mdn", "01032780597");
		bodyParam.put("userId", "3388352");
		bodyParam.put("saleType", "0");
		bodyParam.put("orderType", "0");
		bodyParam.put("pageCount", 30);
		bodyParam.put("rowCount", "0");
		
		parameter.put("rData", bodyParam);
		
		String url = "http://pri-icpsw-stg.syrup.co.kr/syrupApi/ux5/coupon/issue/issueList.do";
		
		try {
			String result = (String) SpringRestTemplate.exchangePostString(url, header, parameter, 1000, 1000);
			System.out.println(JSONObject.fromObject(result.trim()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	private static void httpConnectionPool () {
		Map header = new HashMap();
		header.put("Accept", "application/json");
		header.put("Content-Type", "application/json");
		header.put("Cache-Control", "no-cache");
		header.put("Host", "pri-icpcdsch.skpcoupon.com");
		
		Map parameter = new HashMap();
		Map bodyParam = new HashMap();
		
		parameter.put("poc", "0002");
		parameter.put("term", "01");
		parameter.put("ver", "0001");
		parameter.put("traceNo", "20161128123412XoP");
		
		bodyParam.put("mdn", "01032780597");
		bodyParam.put("userId", "3388352");
		bodyParam.put("saleType", "0");
		bodyParam.put("pageCount", 30);
		
		parameter.put("rData", bodyParam);
		
		String url = "http://pri-icpsw-stg.syrup.co.kr/syrupApi/ux5/coupon/issue/issueList.do";
		
		try {
			String result = HttpConnectionPool.postString (url, header, parameter, HttpConnectionPool.REQUEST_TYPE_JSON, 1000, 1000);
			System.out.println(JSONObject.fromObject(result.trim()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

