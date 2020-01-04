package de.andwari.tournamentcore.matchmaking.combinator;

public class Pair<F> {
	
	public Pair(F item1, F item2) {
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public F item1;
	public F item2;
}