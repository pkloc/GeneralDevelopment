package com.klocworx.helpers;

import java.util.Random;

public class NumberGeneratorImpl implements NumberGenerator {

	@Override
	public int getNewNumber() {
		
		Random random = new Random();
		
		return random.nextInt(37);
	}

}
