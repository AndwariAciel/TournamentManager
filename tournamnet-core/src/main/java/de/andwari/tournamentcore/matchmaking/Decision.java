package de.andwari.tournamentcore.matchmaking;

import java.util.ArrayList;

import de.andwari.tournamentcore.player.entity.Player;

public class Decision {

	private ArrayList<Possibility> orderdPossibilities;
	
	private int decision = -1;

	public Decision(ArrayList<Possibility> orderdPossibilities) {
		this.orderdPossibilities = orderdPossibilities;
	}

	public boolean selectNext() { 
		if(orderdPossibilities.size() > decision + 1) {
			decision++;
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Player> getUnmatchedPlayers() {
		return orderdPossibilities.get(decision).unmatchedPlayers;
	}
	
	public Possibility getDecision() {
		return orderdPossibilities.get(decision);
	}
	
}
