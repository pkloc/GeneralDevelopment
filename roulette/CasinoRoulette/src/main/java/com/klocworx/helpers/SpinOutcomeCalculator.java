package com.klocworx.helpers;

import java.util.List;

import com.klocworx.PlayerBet;

public interface SpinOutcomeCalculator {
	
	/*
	 * Given a list of PlayerBets and the ball number, this method will return a corresponding list of outcomes.
	 */
	public List<SpinOutcome> calculateOutcomes(List<PlayerBet> playerBets, int ballNumber);

}
