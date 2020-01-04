package de.andwari.tournamentcore.rounds;

import javax.inject.Inject;

import de.andwari.tournamentcore.database.BaseRepository;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.matches.MatchRepository;

public class RoundRepository extends BaseRepository<Round> {

	@Inject
	private MatchRepository matchRepos;
	
	@Override
	public void create(Round round) {
		super.create(round);
		for(Match match : round.getMatches()) {
			matchRepos.create(match);
		}
	}
	
}
