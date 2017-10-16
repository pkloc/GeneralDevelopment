package com.klocworx.Exceptions;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 3565376623398826012L;

	public InvalidInputException(){
		
	}
	
	public InvalidInputException(String message){
		super(message);
	}
}
