package com.klocworx.factories;

import com.klocworx.helpers.PlayerNameProvider;
import com.klocworx.helpers.PlayerNameProviderImpl;

public class PlayerNameProviderFactory {

	public static PlayerNameProvider getPlayerNameProvider(){
		return new PlayerNameProviderImpl();
	}
}
