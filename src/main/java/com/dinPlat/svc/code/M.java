package com.dinPlat.svc.code;

public enum M {
	
	// 정상 처리
	SUCCESS 					("0000", "정상 처리 되었습니다."),

	// Login
	LOGIN_NONE_EXIST			("1001", "등록되지 않은 회원ID 입니다."),
	
	// 회원 가입
	MEMBER_EXIST				("1101", "이미 등록되어 있는 회원ID 입니다."),
	
	// 종목발굴
	ENTRY_PORTFOLIO				("1201", "포트폴리오에 편입 되었습니다."),
	REMOVE_PORTFOLIO			("1202", "포트폴리오에서 삭제 하였습니다."),
	
	// DB 공통
	DB_DUPLICATE				("9001", "중복된 Key가 입력이 되었습니다. 확인 바랍니다."),
	DB_NOT_NULL					("9002", "필수갑이 입력되지 않았습니다. 확인 바랍니다."),
	DB_SAVE_FAIL				("9003", "DB에 데이터 저장시 오류가 발생하였습니다. 확인 바랍니다."),
	

	// 에러메시지를 미리 지정하지 않고 특수한 상황에 따른 메세지를 지정할 경우 사용 (확장메세지에 실제 보여주고하는 메세지를 전달함.) 
	// ex) throw new InvalidException(M.FLEXIBLE_MESSAGE, "name field cannot be null.");
	FLEXIBLE_MESSAGE			("9998", ""),
	
	UNDEFINED_ERROR 			("9999", "알수 없는 오류입니다.");
	
	private String code;
	private String msg;

	M (String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
