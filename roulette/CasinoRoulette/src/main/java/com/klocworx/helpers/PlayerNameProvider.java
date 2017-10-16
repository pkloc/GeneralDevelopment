package com.klocworx.helpers;

import java.util.List;

public interface PlayerNameProvider {
	
	/*
	 * This method will read player names from a file. If the file is not found or cannot be read it will return
	 * an empty list.
	 */
	public List<String> getNames();

}
