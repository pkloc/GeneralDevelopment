package com.klocworx.helpers;

import java.util.ArrayList;
import java.util.List;

import com.klocworx.PlayerBet;

public class SpinOutcomeCalculatorImpl implements SpinOutcomeCalculator {

	@Override
	public List<SpinOutcome> calculateOutcomes(List<PlayerBet> playerBets, int ballNumber) {
		
		List<SpinOutcome> rouletteOutcomes = new ArrayList<SpinOutcome>();
		
		for(PlayerBet playerBet : playerBets){
			
			if( playerBet.getBet().equalsIgnoreCase("even") && ((ballNumber % 2) == 0) )			{				
				rouletteOutcomes.add(new SpinOutcome(playerBet.getPlayerName(), playerBet.getBet(), "WIN", playerBet.getBetAmount() * 2));
			}
			else if( playerBet.getBet().equalsIgnoreCase("odd") && !((ballNumber % 2) == 0) ){				
				rouletteOutcomes.add(new SpinOutcome(playerBet.getPlayerName(), playerBet.getBet(), "WIN", playerBet.getBetAmount() * 2));
			}
			else if( playerBet.getBet().equalsIgnoreCase("even") || playerBet.getBet().equalsIgnoreCase("odd")){				
				rouletteOutcomes.add(new SpinOutcome(playerBet.getPlayerName(), playerBet.getBet(), "LOSE", 0.0));
			}
			else if( Integer.parseInt(playerBet.getBet()) == ballNumber ){				
				rouletteOutcomes.add(new SpinOutcome(playerBet.getPlayerName(), playerBet.getBet(), "WIN", playerBet.getBetAmount() * 36));
			}
			else{				
				rouletteOutcomes.add(new SpinOutcome(playerBet.getPlayerName(), playerBet.getBet(), "LOSE", 0.0));
			}			
		}
		
		return rouletteOutcomes;
	}

}
