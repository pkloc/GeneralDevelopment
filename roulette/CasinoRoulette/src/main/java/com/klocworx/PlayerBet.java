package com.klocworx;

public class PlayerBet {
	
	private String mPlayerName;
	private String mBet;
	private double mBetAmount;
	
	public PlayerBet(String playerName, String bet, double betAmount){
		mPlayerName = playerName;
		mBet = bet;
		mBetAmount = betAmount;
	}

	public String getPlayerName() {
		return mPlayerName;
	}

	public String getBet() {
		return mBet;
	}

	public double getBetAmount() {
		return mBetAmount;
	}

}
