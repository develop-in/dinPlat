package com.dinPlat.svc.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * InputFiledValidation Annotation 생성
 * 
 * @interface로 생성 함
 * [엘리먼트 타입] [엘리먼트명()] {default[값]};
 * 예) String testElement() default "TEST";
 * 
 */
@Target( ElementType.FIELD )
@Retention( RetentionPolicy.RUNTIME )
public @interface Definition {


	/**
	 * 필수여부
	 * true : 필수
	 * false : 필수아님
	 */
	boolean required() default false;
	
	/**
	 * 문자열 최소 길이 체크
	 * 미 지정시 0
	 */
	int minLength( ) default 0;
	
	/**
	 * 문자열 최대 길이 체크
	 * 미 지정시 65536
	 */
	int maxLength( ) default 65536;
	
	/**
	 * 문자열 필요 길이
	 * 미 지정시 체크 안함
	 */
	int equalLength( ) default -1;
	
	/**
	 * 문자열 날짜 포맷 체크
	 * 미 지정시 체크 안함
	 */
	String dateFormat( ) default "";
	
	/**
	 * 문자열 체크 리스트
	 * 미 지정시 체크 안함
	 */
	String[] checkedData( ) default {};
}
