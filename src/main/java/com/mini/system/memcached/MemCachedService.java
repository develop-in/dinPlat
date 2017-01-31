package com.mini.system.memcached;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.dinPlat.box.ds.JsonE;
import com.dinPlat.svc.cache.MemcachedE;
import com.dinPlat.svc.exception.InvalidException;
import com.dinPlat.svc.exception.ValidException;
import com.mini.common.service.ServiceParent;
import com.mini.network.loctracking.stomp.StompHandler;

import org.springframework.stereotype.Controller;

@Service ("MemCachedService")
public class MemCachedService extends ServiceParent {
	
	@Autowired
	private MemcachedE cache;
	
	@Override
	@Transactional(rollbackFor={Exception.class, InvalidException.class}, noRollbackFor={ValidException.class})		// @Transactional Annotation은 doService method에 있어야지 정확하게 작동함.
	public Object doService (Object parameter, Object extendParameter) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		if (parameter != null) {
			String key = (String) ((Map)parameter).get("key");
			String name = (String) ((Map)parameter).get("name");
			String age = (String) ((Map)parameter).get("age");
			
			if (key != null) {
				Object o = getCacheData(key, parameter);
				mav.setViewName("/common/result");
				mav.addObject("result", o);
			} else {
				mav.setViewName("/system/memcached/simple");
			}
		}
			
		return mav;
	}

	private Object getCacheData (String key, Object parameter) {
		Object cachedData = cache.get(key);
		
		if (cachedData == null) {
			System.out.println("cachedData is null");
			cache.set(key, parameter, 100);				// key, value, 캐싱시간(초)
		    System.out.println("set Data is : " + cache.get(key));
		    cachedData = "init >>>> " + cache.get(key);
		} else {
			System.out.println("cachedData : " + cachedData);	
		}
		
		return cachedData;
	}
}
