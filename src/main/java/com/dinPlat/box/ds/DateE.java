package com.dinPlat.box.ds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateE {
	
	/**
	 * Date 포맷
	 * yyyy	: 년도
	 * MM	: 월
	 * dd	: 일
	 * HH	: 시간 (24시간으 표현)
	 * mm	: 분
	 * ss	: 초
	 * SSS	: millisecond
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentTime(String format) {
		  SimpleDateFormat sidf = new SimpleDateFormat(format, Locale.KOREA);
		  Date currentTime = new Date();
		  String cTime = sidf.format(currentTime);
		  return cTime;
	}
}
