package com.klocworx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Timer;

import com.klocworx.Exceptions.InvalidInputException;
import com.klocworx.factories.InputValidatorFactory;
import com.klocworx.factories.PlayerNameProviderFactory;
import com.klocworx.helpers.PlayerNameProvider;
import com.klocworx.helpers.InputValidator;


public class RouletteGame 
{	
	private Timer timer;
	
	private SpinCompleteTask spinCompleteTask;
	private InputValidator inputValidator;
	private PlayerNameProvider playerNameProvider;
	
	private final int spinTimeInSeconds = 30;
	
	public RouletteGame(){		
		
		spinCompleteTask = SpinCompleteTask.getInstance();
		
		this.playerNameProvider = PlayerNameProviderFactory.getPlayerNameProvider();;
		this.inputValidator = InputValidatorFactory.getInputValidator();
	}
	
	public void startGame()
	{
		List<String> players = playerNameProvider.getNames();
		
		System.out.println("Current players are:");
		for(String player : players){
			System.out.println(player);
		}
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(spinCompleteTask, spinTimeInSeconds*1000, spinTimeInSeconds*1000);
        
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
    	while(true){
    		
			try {
				String userInput = consoleReader.readLine();
				
				// If user types quit, it's game over
				if(userInput.equalsIgnoreCase("quit")){
	    			stopGame();
	    			break;
	    		}
				
				PlayerBet playerBet = inputValidator.validate(userInput, players);
				
				addNewPlayerBet(playerBet);
				
			}
			catch (InvalidInputException e){
				System.out.println(e.getMessage());
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    		
    	}        
	}
	
	private void addNewPlayerBet(PlayerBet playerBet){
		if(!hasPlayerBetYet(playerBet.getPlayerName()))
		{
			spinCompleteTask.addBet(playerBet);
		}
		else
		{
			System.out.println("Player has already");
		}
	}
	
	private boolean hasPlayerBetYet(String playerName){
		for(PlayerBet playerBet : spinCompleteTask.getCurrentBets()){
			if(playerBet.getPlayerName().equals(playerName)){
				return true;
			}
		}
		return false;
	}
	
	private void stopGame()
	{
		timer.cancel();
	}
	
}
