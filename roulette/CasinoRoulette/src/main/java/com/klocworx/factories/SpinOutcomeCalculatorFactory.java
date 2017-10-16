package com.klocworx.factories;

import com.klocworx.helpers.SpinOutcomeCalculator;
import com.klocworx.helpers.SpinOutcomeCalculatorImpl;

public class SpinOutcomeCalculatorFactory {

	public static SpinOutcomeCalculator getSpinOutcomeCalculator(){
		return new SpinOutcomeCalculatorImpl();
	}
}
