package com.klocworx.factories;

import com.klocworx.helpers.InputValidator;
import com.klocworx.helpers.InputValidatorImpl;

public class InputValidatorFactory {

	public static InputValidator getInputValidator(){
		return new InputValidatorImpl();
	}
}
