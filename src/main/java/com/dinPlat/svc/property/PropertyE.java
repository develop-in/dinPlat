package com.dinPlat.svc.property;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import com.dinPlat.svc.code.Config.P;
import com.dinPlat.svc.log.DropLog;

public class PropertyE {

/*
 * 독립적으로 구성하기 위해 ENUM을 별도 java로 분리하여 svc 패키지 아래에 둠.
 */
//	public static Properties serviceProp = null;
//	public static Properties messageProp = null;
//	
//	public enum P {
//		SERVICE		(serviceProp, "static/service.properties"),
//		MESSAGE		(messageProp, "static/message.properties");
//		
//		private Properties properties;
//		private String path;
//		
//		P (Properties properties, String path) {
//			this.properties = properties;
//			this.path = path;
//		}
//		
//		public Properties getProperties() {
//			return properties;
//		}
//		public void setProperties(Properties properties) {
//			this.properties = properties;
//		}
//		public String getPath() {
//			return path;
//		}
//		public void setPath(String path) {
//			this.path = path;
//		}
//	}
	
	/**
	 * Contructor
	 * @param filePath
	 * @throws Exception
	 */
	private static Properties createProperties (P p) {
		
		Properties properties = new Properties();
		p.setProperties(properties);
		
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			if (classLoader == null)
				classLoader = ClassLoader.getSystemClassLoader();
			URL url = classLoader.getResource("");
			FileInputStream fis = new FileInputStream(url.getPath() + p.getPath());
			properties.load(new java.io.BufferedInputStream(fis));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return properties;
	}
	
	/**
	 * PProperty File에서 key로 값 찾기
	 * @param filePath
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getStringValue (P p, String key) {
		Properties properties = getProperty(p);
		
		String value = getValue(properties, key);
		
		return value;
	}
	
	public static boolean getbooleanValue (P p, String key) {
		Properties properties = getProperty(p);
		
		String value = getValue(properties, key);
		
		if (value.equals("true")) return true;
		
		return false;
	}
	
	public static int getintValue (P p, String key) {
		Properties properties = getProperty(p);
		
		String value = getValue(properties, key);
		
		return Integer.parseInt(value);
	}
	
	
	public static long getlongValue (P p, String key) {
		Properties properties = getProperty(p);
		
		String value = getValue(properties, key);
		
		return Integer.parseInt(value);
	}
	
	
	private static Properties getProperty (P p) {
		Properties properties = null;
		
		if (p.getProperties() == null) {
			properties = createProperties(p);

			DropLog.log("classLoader : " + p.getPath());
		} else {
			properties = p.getProperties();
		}
	
		return properties;

	}
	
	private static String getValue(Properties properties, String key) {
		return properties.getProperty(key);
		
	}
}
