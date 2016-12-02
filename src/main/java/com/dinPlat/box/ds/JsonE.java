package com.dinPlat.box.ds;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import net.sf.json.JSONObject;

public class JsonE {

	/**
	 * Object(List, Map)를 JSON String으로 변환하여 리턴하는 Method. (로그에 보기좋게 찍을수 있도록 변환한다.)
	 * @param objData
	 * @return
	 */
	public static String beautifier (Object objData) {
		String org = null;
		String result = null;
		
		ObjectMapper objectMapper = null;
		JsonNode tree = null;
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if (objData instanceof List) {
			hm.put("data", objData);
		} else {
			hm = (HashMap<String, Object>) objData;
		}
		
		try {
			objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
			org = JSONObject.fromObject(hm).toString();
			tree = objectMapper.readTree(org);
			result = objectMapper.writeValueAsString(tree);
		} catch (Exception e){
			result = hm.toString();
		}
		
		return result;
	}
	
	
	/**
	 * Object(List, Map)를 JSON String으로 변환하는 Method
	 * @param objData
	 * @return
	 */
	public static String changeJsonString (Object objData) {
		String result = null;
		
//		ObjectMapper objectMapper = null;
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		if (objData instanceof List) {
			hm.put("data", objData);
		} else {
			hm = (HashMap<String, Object>) objData;
		}
		
		try {
//			objectMapper = new ObjectMapper();
//			objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
			result = JSONObject.fromObject(hm).toString();
		} catch (Exception e){
			result = hm.toString();
		}
		
		return result;
	}
	
	
	/**
	 * Json포맷의 String을 Map Object르 변환하여 리턴하는 API.
	 * @param jsonString
	 * @return
	 */
	public static Map changeStringToMap (String jsonString) throws Exception {
		
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			hm = new ObjectMapper().readValue(jsonString, HashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hm;
	}
	
}
