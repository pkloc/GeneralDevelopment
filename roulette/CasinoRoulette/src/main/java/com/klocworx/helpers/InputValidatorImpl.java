package com.klocworx.helpers;

import java.util.Collection;
import java.util.StringTokenizer;

import com.klocworx.PlayerBet;
import com.klocworx.Exceptions.InvalidInputException;

public class InputValidatorImpl implements InputValidator{

	public static String INVALID_INPUT_MESSAGE = "Invalid input.  Please enter player name, followed by a bet (1-36, EVEN, ODD), followed by bet amount.";
	public static String INVALID_PLAYER_NAME_MESSAGE = "Invalid Player name given.";
	public static String INVALID_BET_MESSAGE = "Invalid bet given.  Valid bets are ODD, EVEN or any number between 1 and 36.";
	public static String INVALID_BET_AMOUNT_MESSAGE = "Invalid bet amount given.  Must be a positive decimal number.";
	
	@Override
	public PlayerBet validate(String userInput, Collection<String> validUsers) throws InvalidInputException {
		
		StringTokenizer tokens = new StringTokenizer(userInput);
		String playerName = "";
		String playerBet = "";
		String playerBetAmount = "";
		
		if(tokens.countTokens() == 3){
			playerName = tokens.nextToken();
			playerBet = tokens.nextToken();
			playerBetAmount = tokens.nextToken();			
		}
		else{
			throw new InvalidInputException(INVALID_INPUT_MESSAGE);
		}
		
		if(!validUsers.contains(playerName)){
			throw new InvalidInputException(INVALID_PLAYER_NAME_MESSAGE);
		}
		
		if(!IsValidBet(playerBet)){
			throw new InvalidInputException(INVALID_BET_MESSAGE);
		}
		
		try{
			double betAmountNumber = Double.parseDouble(playerBetAmount);
			if(betAmountNumber <= 0){
				throw new InvalidInputException(INVALID_BET_AMOUNT_MESSAGE);
			}
			return new PlayerBet(playerName, playerBet, betAmountNumber);
		}
		catch(NumberFormatException e){
			throw new InvalidInputException(INVALID_BET_AMOUNT_MESSAGE);
		}		
	}
	
	private boolean IsValidBet(String bet){
		
		try{
			int betNumber = Integer.parseInt(bet);
			if(betNumber < 0 || betNumber > 36){
				return false;
			}
		}catch(NumberFormatException e){
			if( !bet.equalsIgnoreCase("even") && !bet.equalsIgnoreCase("odd") ){
				return false;
			}
		}
		
		return true;
	}
}
