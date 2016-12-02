package com.template.common.inout;

import com.dinPlat.svc.validation.Definition;

/**
 * 
 * 공통 헤더
 *
 */
public final class ReqHeader {
	
	@Definition (required=true)
	private String traceNo;

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

}
