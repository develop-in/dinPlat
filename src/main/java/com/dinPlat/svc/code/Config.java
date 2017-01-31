package com.dinPlat.svc.code;

import java.util.Properties;

public class Config {

	public static Properties serviceProp = null;
	public static Properties messageProp = null;
	public static Properties memcachedProp = null;
	
	public enum P {
		SERVICE		(serviceProp,	"properties/service.properties"),
		MESSAGE		(messageProp,	"properties/message.properties"),
		MEMCASHED	(memcachedProp,	"properties/memcached.properties");
		
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
}