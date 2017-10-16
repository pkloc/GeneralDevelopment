package com.klocworx;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.klocworx.helpers.SpinOutcome;
import com.klocworx.helpers.SpinOutcomeCalculator;
import com.klocworx.helpers.SpinOutcomeCalculatorImpl;

public class SpinOutcomeCalculatorImplTest {

	public SpinOutcomeCalculator outcomeCalculator;
	
	@Before
	public void Initialize(){
		outcomeCalculator = new SpinOutcomeCalculatorImpl();
	}
	
	@Test
	public void calculateOutcomes_NoBets_ReturnEmptyList(){
		
		// Arrange
				
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(new ArrayList<PlayerBet>(), 23);
		
		// Assert
		Assert.assertEquals(0, outcomes.size());
	}
	
	@Test
	public void calculateOutcomes_BetLose_ReturnsOutcomesWithOneLoss(){
		
		// Arrange
		List<PlayerBet> playerBets = new ArrayList<PlayerBet>();
		String testPlayerName = "SlimShadey";
		String testBet = "2";
		playerBets.add(new PlayerBet(testPlayerName, testBet, 2.0));
		
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(playerBets, 23);
		
		// Assert
		Assert.assertEquals(1, outcomes.size());
		SpinOutcome outcome = outcomes.get(0);
		Assert.assertEquals(testPlayerName, outcome.getPlayerName());
		Assert.assertEquals(testBet, outcome.getBet());
		Assert.assertEquals("LOSE", outcome.getOutcome());
		Assert.assertEquals(0.0, outcome.getWinnings(), 0.01);
	}
	
	@Test
	public void calculateOutcomes_BetWinningNumber_ReturnsOutcomesWithOneWin(){
		
		// Arrange
		List<PlayerBet> playerBets = new ArrayList<PlayerBet>();
		String testPlayerName = "SlimShadey";
		String testBet = "23";
		double testBetAmount = 2.0;
		playerBets.add(new PlayerBet(testPlayerName, testBet, testBetAmount));
		
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(playerBets, 23);
		
		// Assert
		Assert.assertEquals(1, outcomes.size());
		SpinOutcome outcome = outcomes.get(0);
		Assert.assertEquals(testPlayerName, outcome.getPlayerName());
		Assert.assertEquals(testBet, outcome.getBet());
		Assert.assertEquals("WIN", outcome.getOutcome());
		Assert.assertEquals(testBetAmount*36, outcome.getWinnings(), 0.01);
	}

	@Test
	public void calculateOutcomes_BetEvenWin_ReturnsOutcomesWithOneWin(){
		
		// Arrange
		List<PlayerBet> playerBets = new ArrayList<PlayerBet>();
		String testPlayerName = "SlimShadey";
		String testBet = "EVEN";
		double testBetAmount = 2.0;
		playerBets.add(new PlayerBet(testPlayerName, testBet, testBetAmount));
		
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(playerBets, 32);
		
		// Assert
		Assert.assertEquals(1, outcomes.size());
		SpinOutcome outcome = outcomes.get(0);
		Assert.assertEquals(testPlayerName, outcome.getPlayerName());
		Assert.assertEquals(testBet, outcome.getBet());
		Assert.assertEquals("WIN", outcome.getOutcome());
		Assert.assertEquals(testBetAmount*2, outcome.getWinnings(), 0.01);
	}
	
	@Test
	public void calculateOutcomes_BetOddWin_ReturnsOutcomesWithOneWin(){
		
		// Arrange
		List<PlayerBet> playerBets = new ArrayList<PlayerBet>();
		String testPlayerName = "SlimShadey";
		String testBet = "ODD";
		double testBetAmount = 2.0;
		playerBets.add(new PlayerBet(testPlayerName, testBet, testBetAmount));
		
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(playerBets, 1);
		
		// Assert
		Assert.assertEquals(1, outcomes.size());
		SpinOutcome outcome = outcomes.get(0);
		Assert.assertEquals(testPlayerName, outcome.getPlayerName());
		Assert.assertEquals(testBet, outcome.getBet());
		Assert.assertEquals("WIN", outcome.getOutcome());
		Assert.assertEquals(testBetAmount*2, outcome.getWinnings(), 0.01);
	}
	
	@Test
	public void calculateOutcomes_TwoBetsOneLoseOneWin_ReturnsOutcomesWithOneLoseAndOneWin(){
		
		// Arrange
		List<PlayerBet> playerBets = new ArrayList<PlayerBet>();
		String testPlayerName1 = "SlimShadey";
		String testBet1 = "EVEN";
		double testBetAmount1 = 4.0;
		playerBets.add(new PlayerBet(testPlayerName1, testBet1, testBetAmount1));
		
		String testPlayerName2 = "SlimShadey";
		String testBet2 = "13";
		double testBetAmount2 = 6.0;
		playerBets.add(new PlayerBet(testPlayerName2, testBet2, testBetAmount2));
		
		// Act
		List<SpinOutcome> outcomes = outcomeCalculator.calculateOutcomes(playerBets, 13);
		
		// Assert
		Assert.assertEquals(2, outcomes.size());
		
		SpinOutcome outcome = outcomes.get(0);
		Assert.assertEquals(testPlayerName1, outcome.getPlayerName());
		Assert.assertEquals(testBet1, outcome.getBet());
		Assert.assertEquals("LOSE", outcome.getOutcome());
		Assert.assertEquals(0.0, outcome.getWinnings(), 0.01);
		
		outcome = outcomes.get(1);
		Assert.assertEquals(testPlayerName2, outcome.getPlayerName());
		Assert.assertEquals(testBet2, outcome.getBet());
		Assert.assertEquals("WIN", outcome.getOutcome());
		Assert.assertEquals(testBetAmount2 * 36, outcome.getWinnings(), 0.01);
	}
	
}
