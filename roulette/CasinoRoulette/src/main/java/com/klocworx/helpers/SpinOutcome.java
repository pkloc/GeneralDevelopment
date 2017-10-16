package com.klocworx.helpers;

public class SpinOutcome {

	private String playerName;
	private String bet;
	private String outcome;
	private double winnings;
	
	public SpinOutcome(String playerName, String bet, String outcome, double winnings) {
		
		this.playerName = playerName;
		this.bet = bet;
		this.outcome = outcome;
		this.winnings = winnings;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	public String getBet() {
		return bet;
	}
	public String getOutcome() {
		return outcome;
	}
	public double getWinnings() {
		return winnings;
	}
	
	
}
