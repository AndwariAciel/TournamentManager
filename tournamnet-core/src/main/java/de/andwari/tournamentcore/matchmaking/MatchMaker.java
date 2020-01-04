package de.andwari.tournamentcore.matchmaking;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Round;

public interface MatchMaker {
	
	public Round createMatches(Event event);

}
