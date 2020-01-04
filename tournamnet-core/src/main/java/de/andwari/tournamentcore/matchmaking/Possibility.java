package de.andwari.tournamentcore.matchmaking;

import java.util.ArrayList;

import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.player.entity.Player;

public class Possibility {

	public ArrayList<Match> matches;	
	
	public ArrayList<Player> unmatchedPlayers;
	
	public Possibility() {
		matches = new ArrayList<>();
		unmatchedPlayers = new ArrayList<>();
	}
	
	public int getRatingClass() {
		return unmatchedPlayers.size();
	}
}
