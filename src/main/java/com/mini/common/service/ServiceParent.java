package com.mini.common.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.dinPlat.box.dao.CommonDAO;
import com.dinPlat.svc.code.M;
import com.dinPlat.svc.log.DropLog;


public abstract class ServiceParent {
	HashMap<String, String> resCodeMap = new HashMap<String, String>();
	
	@Autowired
	protected CommonDAO dao;
	
	
	protected ServiceParent () {
		setMessage(M.SUCCESS);
	}
	
	// 서비스 로직 구현
	public abstract Object doService (Object parameter, Object extendParameter) throws Exception;
	

	// 메세지 설정
	public void setMessage(M m) {
		this.resCodeMap.put("resCode", m.getCode());
		this.resCodeMap.put("resMsg", m.getMsg());
	}

	public HashMap<String, String> getResCodeMap() {
		return resCodeMap;
	}

	public void setResCodeMap(HashMap<String, String> resCodeMap) {
		this.resCodeMap = resCodeMap;
	}

}
