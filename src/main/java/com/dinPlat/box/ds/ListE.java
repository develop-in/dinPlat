package com.dinPlat.box.ds;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListE {

	/**
	 * Map 데이터로 구성된 List에서 지정한 Key를 가진 Map 데이터만 추출하여 List로 반환하는 Method.
	 * 
	 * ex)	[ 
	 * 	   		{
	 * 				"name" : "홍길동",
	 * 			 	"age"  : "20"
	 * 			}, 
	 * 			{
	 * 				"name" : "강길동",
	 * 				"age"  : "30"
	 * 			}
	 * 		]
	 * 		에서 name Map만 List로 추출
	 * 
	 * @param data : List Data
	 * @param key  : 찾고자 하는 Map의 Key
	 * @return
	 */
	public static List extractMapListInListMap (List data, String key) {

		List list = new ArrayList();
		
		for (Object obj : data) {
			Map m = new HashMap();
			m.put(key, ((Map)obj).get(key));
			list.add(m);
		}
		
		return list;
	}
	
	
	/**
	 * Map 데이터로 구성된 List에서 지정한 Key를 가진 Value만 추출하여 List로 반환하는 Method.
	 * 
	 * ex)	[ 
	 * 	   		{
	 * 				"name" : "홍길동",
	 * 			 	"age"  : "20"
	 * 			}, 
	 * 			{
	 * 				"name" : "강길동",
	 * 				"age"  : "30"
	 * 			}
	 * 		]
	 * 		에서 name key를 가진 value만 List로 추출
	 * 
	 * @param data : List Data
	 * @param key  : 찾고자 하는 Map의 Key
	 * @return
	 */
	public static List extractStringListInListMap (List data, String key) {

		List list = new ArrayList();
		
		for (Object obj : data) {
			Map m = new HashMap();
			m.put(key, ((Map)obj).get(key));
			list.add(m.get(key));
		}
		
		return list;
	}
	

	/**
	 * Map 데이터로 구성된 List에서 지정한 Key를 가진 Value만 추출하여 String Array로 반환하는 Method.
	 * @param data : List Data
	 * @param key  : 찾고자 하는 Map의 Key
	 * @return
	 */
	public static String[] extractStringArrayInListMap(List data, String key) {
		
		String[] strArray = null;
		
		int i=0;
		for (Object obj : data) {
			String value = (String) ((Map)obj).get(key);
			strArray[i] = value;
			i++;
		}
		
		return strArray;
		
	}
	
	

	/**
	 * List에서 matchKey에 해당하는 matchValue를 가진 map을 찾아 List로 반환하는 Method.
	 * @param data			: List Data
	 * @param matchKey		: Map에서 찾고자 하는 Key
	 * @param matchValue	: Map에서 찾고자 하는 Value
	 * @return
	 */
	public static List findMapListInListMap (List data, String matchKey, String matchValue) {
		
		List matchList = new ArrayList();
		
		for (Object obj : data) {
			String value = (String) ((Map)obj).get(matchKey);
			if (value != null)
			if (value.equals(matchValue)) {
				matchList.add((Map)obj);
			}
		}
		
		return matchList;
	}
	
	
	/**
	 * List에서 matchKey에 해당하는 matchValue를 가진 첫번째 map을 찾아 반환하는 Method.
	 * @param data			: List Data
	 * @param matchKey		: Map에서 찾고자 하는 Key
	 * @param matchValue	: Map에서 찾고자 하는 Value
	 * @return
	 */
	public static Map findMapInListMap (List data, String matchKey, String matchValue) {
		
		for (Object obj : data) {
			String value = (String) ((Map)obj).get(matchKey);
			if (value != null)
			if (value.equals(matchValue)) {
				return (Map)obj;
			}
		}
		
		return null;
	}
	
	
	/**
	 * List의 특정데이터(Map:matchkey, matchvalue)를 포함한 row를 delete 또는 replace하는  Method.
	 * @param list			: List Data
	 * @param replaceMap	: 바꾸고하는 Map Data
	 * @param matchKey		: 바꾸는 대상이 되는 Map 의 Key
	 * @param matchValue	: 바꾸는 대상이 되는 Map 의 Value
	 * @param replace		: true (replace), false (delete)
	 * @return
	 */
	public static List replaceListMap (List list, Map replaceMap, String matchKey, String matchValue, boolean replace) {
		
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Map data = (Map) iter.next();
			String value = (String) data.get(matchKey);

			if (value != null)
			if (value.equals(matchValue)) {
				iter.remove();
			}
		}
		
		if (replace) {
			list.add(replaceMap);
		}
		

		
		return list;
	}
	
}
