package com.klocworx;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.klocworx.Exceptions.InvalidInputException;
import com.klocworx.helpers.InputValidator;
import com.klocworx.helpers.InputValidatorImpl;

public class InputValidatorImplTest {
	
	InputValidator validator;
	
	@Rule public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void Initialize(){
		validator = new InputValidatorImpl();
	}
	
	@Test
	public void validate_BadInput_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange/Assert		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_INPUT_MESSAGE);
		
		// Act		
		validator.validate("Gobble Gobble", new ArrayList<String>());
	}
		
	@Test
	public void validate_InvalidPlayerName_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "InvalidPlayer";
		String testBet = "EVEN";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add("Player1");
		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_PLAYER_NAME_MESSAGE);
		
		// Act
		validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
	}
	
	@Test
	public void validate_validEvenBet_ReturnsValidPlayerBet() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "EVEN";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add("Player1");
		
		// Act
		PlayerBet playerBet = validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
		
		// Assert
		Assert.assertEquals(testPlayer, playerBet.getPlayerName());
		Assert.assertEquals(testBet, playerBet.getBet());
		Assert.assertEquals(testBetAmount, playerBet.getBetAmount(), 0.01);		
	}
	
	@Test
	public void validate_validOddBet_ReturnsValidPlayerBet() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "EVEN";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add("Player1");
		
		// Act
		PlayerBet playerBet = validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
		
		// Assert
		Assert.assertEquals(testPlayer, playerBet.getPlayerName());
		Assert.assertEquals(testBet, playerBet.getBet());
		Assert.assertEquals(testBetAmount, playerBet.getBetAmount(), 0.01);		
	}
	
	@Test
	public void validate_validBetNumber0_ReturnsValidPlayerBet() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "0";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		// Act
		PlayerBet playerBet = validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
		
		// Assert
		Assert.assertEquals(testPlayer, playerBet.getPlayerName());
		Assert.assertEquals(testBet, playerBet.getBet());
		Assert.assertEquals(testBetAmount, playerBet.getBetAmount(), 0.01);		
	}
	
	@Test
	public void validate_validBetNumber36_ReturnsValidPlayerBet() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "36";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		// Act
		PlayerBet playerBet = validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
		
		// Assert
		Assert.assertEquals(testPlayer, playerBet.getPlayerName());
		Assert.assertEquals(testBet, playerBet.getBet());
		Assert.assertEquals(testBetAmount, playerBet.getBetAmount(), 0.01);		
	}
	
	@Test
	public void validate_InvalidBet_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "InvalidBet";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_BET_MESSAGE);
		
		// Act
		validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
	}
	
	@Test
	public void validate_InvalidBetNumber37_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "37";
		double testBetAmount = 2.0;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_BET_MESSAGE);
		
		// Act
		validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
	}
	
	@Test
	public void validate_InvalidNonDecimalBetAmount_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "30";
		String testBetAmount = "InvalidBetAmount";
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_BET_AMOUNT_MESSAGE);
		
		// Act
		validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
	}
	
	@Test
	public void validate_InvalidNegativeDecimalBetAmount_throwsInvalidInputException() throws InvalidInputException
	{
		// Arrange
		String testPlayer = "Player1";
		String testBet = "30";
		double testBetAmount = -7.5;
		
		List<String> validPlayerNames = new ArrayList<String>();
		validPlayerNames.add(testPlayer);
		
		thrown.expect( InvalidInputException.class );
		thrown.expectMessage(InputValidatorImpl.INVALID_BET_AMOUNT_MESSAGE);
		
		// Act
		validator.validate(testPlayer + " " + testBet + " " + testBetAmount, validPlayerNames);
	}

}
