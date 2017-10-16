package com.klocworx;

public class Main {
	
	public static void main( String[] args )
    {
    	System.out.println("Welcome to roulette.  Please place some bets. (Type quit to quit game)");
        RouletteGame rouletteGame = new RouletteGame();
        rouletteGame.startGame();        
    }

}
