package com.Xnote;

import java.util.HashMap;
import java.util.Map;

import com.dinPlat.svc.code.Config.P;
import com.dinPlat.svc.property.PropertyE;

public class Pnote {

	public static void main(String[] args) {
		try {
			String str = PropertyE.getStringValue(P.MEMCASHED, "MEMCACHED_SERVERLIST");
			String arr[] = PropertyE.getStringValue(P.MEMCASHED, "MEMCACHED_SERVERLIST").split(";");
			System.out.println("str : " + str);
			System.out.println("arr[0] : " + arr[0] + ", arr[1] : " + arr[1]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

