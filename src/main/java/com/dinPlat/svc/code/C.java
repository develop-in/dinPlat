package com.dinPlat.svc.code;

public class C {

	// Page
	public final static int LINE_COUNT = 10;											// 페이지당 몇 라인을 보여줄지 설정
	public final static int PAGE_COUNT = 10;											// List하단에 표현될 페이지 갯수
	
	// UTI transaction pattern
	public final static String TR_INS = "ins";
	public final static String TR_EDT = "edit";
	public final static String TR_UPD = "upd";
	public final static String TR_DEL = "del";
	public final static String TR_SAV = "save";
	
	
	// Message
	public final static String MSG_DATA_NOT_FOUND = "조건에 해당하는 데이터가 없습니다.";
	
	// Task 문서 Upload
	public final static String TSK_FILE_PATH = "D:\\Workspace\\delvelopin\\dinPlat\\src\\main\\webapp\\static\\taskfile\\";
	public final static String TSK_CONTEXT = "http://127.0.0.1:8080/dinplat/static/taskfile/";
	
}
