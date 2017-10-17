package com.klocworx.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerNameProviderImpl implements PlayerNameProvider {

	@Override
	public List<String> getNames() {
		
		List<String> playerNames = new ArrayList<String>();
		
		BufferedReader reader = null;
		try{
			reader = new BufferedReader( new FileReader( "src/main/resources/playerNames.txt" ) );
			String playerName = null;
			
			while ((playerName = reader.readLine()) != null) {
				playerNames.add(playerName);
			}
		}
		catch(IOException e){
			//Swallow
		}
		finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					//Swallow
				}
			}
		}
				
		return playerNames;
	}

}
