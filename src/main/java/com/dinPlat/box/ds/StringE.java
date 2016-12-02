package com.dinPlat.box.ds;

import java.util.Random;

public class StringE {

	/**
	 * String 배열로 전달된 데이터중 random으로 String을 반환하는 Method.
	 * @param stringArray
	 * @return
	 */
	public static String randomString (String[] stringArray) {

		Random random = new Random();
		
		return stringArray[random.nextInt(stringArray.length)];
	}
	
	
	/**
	 * 지정한 문자로 지정한 길이만큼 왼쪽을 Padding하여 반환하는 Method.
	 * @param str
	 * @param padString
	 * @param len
	 * @return
	 */
	public static String lPadString (String str, String padString, int len) {
		StringBuffer stb = new StringBuffer();

		if (str.length() < len) {
			for (int i=0; i < (len - str.length()); i++) {
				stb.append(padString);
			}
			return stb.toString()+str;
		} else {
			return str;
		}
	}
	
	
	/**
	 * 지정한 문자로 지정한 길이만큼 오르쪽을 Padding하여 반환하는 Method.
	 * @param str
	 * @param padString
	 * @param len
	 * @return
	 */
	public static String rPadString (String str, String padString, int len) {
		StringBuffer stb = new StringBuffer();

		if (str.length() < len) {
			for (int i=0; i < (len - str.length()); i++) {
				stb.append(padString);
			}
			return str + stb.toString();
		} else {
			return str;
		}
	}

	
	/**
	 * String Array에 지정한 String이 있으면 true, 없으면 false를 반환하는 Method.
	 * @param array
	 * @param s
	 * @return
	 */
	public static boolean compareStringArray (String[] array, String s) {
		int cnt=0;
		
		for (String a: array) {
			if (a.equals(s)) cnt++;
		}
		
		if (cnt == 0) return false;
		
		return true;
	}
	
	
	/**
	 * 뒤쪽부터 지정한 길이만큼 잘라서 String을 반환하는 Mthod.
	 * @param s
	 * @param len
	 * @return
	 */
	public static String substrBackToForward (String s, int len) {
		return s.substring(s.length() - len, s.length());
	}
}
