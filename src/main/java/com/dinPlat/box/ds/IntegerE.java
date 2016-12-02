package com.dinPlat.box.ds;

import java.util.Random;

public class IntegerE {

	/**
	 * maxValue내에서 random으로 하나의 값을 전달해주는 Method.
	 * @param maxValue
	 * @return
	 */
	public static int randomInt (int maxValue) {
		Random random = new Random();
		
		return random.nextInt(maxValue);
	}
}
