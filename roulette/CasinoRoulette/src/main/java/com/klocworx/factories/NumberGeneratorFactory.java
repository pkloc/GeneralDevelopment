package com.klocworx.factories;

import com.klocworx.helpers.NumberGenerator;
import com.klocworx.helpers.NumberGeneratorImpl;

public class NumberGeneratorFactory {

	public static NumberGenerator getNumberGenerator(){
		return new NumberGeneratorImpl();
	}
}
