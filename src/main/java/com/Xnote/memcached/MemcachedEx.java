package com.Xnote.memcached;

import com.dinPlat.svc.cache.MemcachedE;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedEx {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doProcess();
	}
	
	private static void doProcess() {
		
		MemcachedE memcachedUtil = new MemcachedE();
		memcachedUtil.init();
		
		String key = "3388352";
		String keyString = "2033266199";

		String cachedData = (String) memcachedUtil.get(key);
		
		if (cachedData == null) {
			System.out.println("cachedData is null");
			memcachedUtil.set(key, keyString, 10);				// key, value, 캐싱시간(초)
		    System.out.println("set Data is : " + memcachedUtil.get(key));
		} else {
			System.out.println("cachedData : " + cachedData);	
		}
	}

}
