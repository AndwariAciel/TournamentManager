package de.andwari.tournamentcore.matchmaking;

import java.util.ArrayList;
import java.util.List;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.player.entity.Player;

public class Pool {

	public final int scorePool;

	public List<Player> poolOfPlayers;
	
	public List<Possibility> possibilities;

	public Pool(Pool pool) {
		scorePool = pool.scorePool;
		poolOfPlayers = new ArrayList<>(pool.poolOfPlayers);
		possibilities = new ArrayList<>(pool.possibilities);
	}

	public Pool(int score, Event event) {
		scorePool = score;
		possibilities = new ArrayList<>();
		poolOfPlayers = new ArrayList<>();
	}

}
