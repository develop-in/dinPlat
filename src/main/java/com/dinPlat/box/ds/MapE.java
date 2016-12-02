package com.dinPlat.box.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.NameValuePair;

public class MapE {

	/**
	 * Map안에 String 타입의 Key, Value만 존재할 경우 NameValuePair[]을 리턴하는 Method.
	 * @param data
	 * @return
	 */
	public static NameValuePair[] convertNameValuePairFromMap (Map data) {
		
		Set set = data.entrySet();
		
	    Iterator iter = (Iterator)set.iterator();
	    NameValuePair[] pair = new NameValuePair[set.size()];
	    int i=0;
	    while (iter.hasNext()) {
	    	Map.Entry entry = (Map.Entry)iter.next();
	    	pair[i] = new NameValuePair((String)entry.getKey(), (String)entry.getValue());
	    	i++;
	    }
	    
		return pair;
	}
}
