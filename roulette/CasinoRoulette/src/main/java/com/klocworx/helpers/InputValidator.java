package com.klocworx.helpers;

import java.util.Collection;

import com.klocworx.PlayerBet;
import com.klocworx.Exceptions.InvalidInputException;

public interface InputValidator {

	/*
	 * This method will validate user input from console and throw an InvalidInputException along with corresponding
	 * error message if input is invalid.  If input is valid, a corresponding PlayerBet is returned.	 * 
	 */
	public PlayerBet validate(String userInput, Collection<String> validUsers) throws InvalidInputException;
	
}
