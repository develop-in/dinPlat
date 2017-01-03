package com.dinPlat.svc.property;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dinPlat.svc.log.DropLog;

public class PropertyE {

	public static Properties serviceProp = null;
	public static Properties messageProp = null;
	
	public enum P {
		SERVICE		(serviceProp, "static/service.properties"),
		MESSAGE		(messageProp, "static/message.properties");
		
		private Properties properties;
		private String path;
		
		P (Properties properties, String path) {
			this.properties = properties;
			this.path = path;
		}
		
		public Properties getProperties() {
			return properties;
		}
		public void setProperties(Properties properties) {
			this.properties = properties;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}
	
	/**
	 * Contructor
	 * @param filePath
	 * @throws Exception
	 */
	private static Properties createProperties (P p) throws Exception {
		
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
	public static String getValue (P p, String key) throws Exception {
		Properties properties = null;
		
		if (p.getProperties() == null) {
			properties = createProperties(p);

			DropLog.log("classLoader : " + p.getPath());
		} else {
			properties = p.getProperties();
		}
	
		return properties.getProperty(key);

	}
}
