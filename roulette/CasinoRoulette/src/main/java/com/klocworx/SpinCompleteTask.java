package com.klocworx;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.klocworx.factories.NumberGeneratorFactory;
import com.klocworx.factories.SpinOutcomeCalculatorFactory;
import com.klocworx.helpers.NumberGenerator;
import com.klocworx.helpers.SpinOutcome;
import com.klocworx.helpers.SpinOutcomeCalculator;

public class SpinCompleteTask extends TimerTask{
	
	private List<PlayerBet> playerBets;
	
	private NumberGenerator numberGenerator;
	private SpinOutcomeCalculator spinOutcomeCalculator;
	
	public SpinCompleteTask(){
		this.numberGenerator = NumberGeneratorFactory.getNumberGenerator();
		this.spinOutcomeCalculator = SpinOutcomeCalculatorFactory.getSpinOutcomeCalculator();
		
		playerBets = new ArrayList<PlayerBet>();
	}
	
	/*
	 * This method is used to add a player bet to be processed.
	 */
	public void addBet(PlayerBet playerBet){
		playerBets.add(playerBet);
	}
	
	/*
	 * This method is used to get a list of player bets that will be processed in the current round.
	 */
	public List<PlayerBet> getCurrentBets(){
		return playerBets;
	}

	@Override
	public void run() {
		
		int ballNumber = numberGenerator.getNewNumber();
		
		System.out.println("Number: " + ballNumber);
		System.out.println("Player                Bet    Outcome  Winnings");
		System.out.println("---");
		
		List<SpinOutcome> rouletteOutcomes = spinOutcomeCalculator.calculateOutcomes(playerBets, ballNumber);
		
		for(SpinOutcome rouletteOutcome : rouletteOutcomes){
			System.out.println( String.format("%-22s", rouletteOutcome.getPlayerName()) 
					+ String.format("%-7s", rouletteOutcome.getBet()) 
					+ String.format("%-9s", rouletteOutcome.getOutcome())
					+ rouletteOutcome.getWinnings()
					);			
		}
		
		// We need to clear the list of player bets before the next round.
		playerBets.clear();
	}
	
	public static SpinCompleteTask getInstance(){
		return new SpinCompleteTask();
	}
}
