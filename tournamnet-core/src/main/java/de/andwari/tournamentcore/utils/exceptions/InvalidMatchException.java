package de.andwari.tournamentcore.utils.exceptions;

public class InvalidMatchException extends Exception {

	private static final long serialVersionUID = -5641184958592546204L;
	
	private static final String MSG = "Match parameters are not ok!";
	
	public InvalidMatchException() {
		super(MSG);
	}

}
