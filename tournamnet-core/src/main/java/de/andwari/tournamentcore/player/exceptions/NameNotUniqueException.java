package de.andwari.tournamentcore.player.exceptions;

public class NameNotUniqueException extends Exception {

	private static final long serialVersionUID = -4049006319780653878L;
	
	private static final String MESSAGE = "Name is already in Database";
	
	public NameNotUniqueException() {
		super(MESSAGE);
	}

}
