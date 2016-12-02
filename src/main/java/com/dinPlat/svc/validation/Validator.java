package com.dinPlat.svc.validation;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class Validator {

	/**
	 * 
	 * @param parameter
	 * @param clazz
	 * @throws Exception
	 */
	public static <T> void execute (Map parameter, Object clazz) throws Exception {
		
		// Input Data Validation
		ObjectMapper mapper = new ObjectMapper();
		clazz =  mapper.convertValue(parameter, (Class<T>) clazz);
		
		try {
			CheckDefinition.checkInputField(clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
}
