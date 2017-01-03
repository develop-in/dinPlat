package com.Xnote;

import java.util.HashMap;
import java.util.Map;

public class Pnote {

	public static void main(String[] args) {

		Map tMap = new HashMap();
		tMap.put("code", "1");
		tMap.put("name", "name");
		
		String transaction = (String) tMap.get("transaction");
	}
}
