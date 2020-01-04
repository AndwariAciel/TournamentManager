package de.andwari.tournamentcore.rounds;

import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.matches.MatchService;
import de.andwari.tournamentcore.matchmaking.MatchMakerImpl2;
import de.andwari.tournamentcore.standings.StandingService;

public class RoundService {

	@Inject
	private StandingService standingService;

	@Inject
	private MatchService matchService;

	@Inject
	private MatchMakerImpl2 matchMaker;

	@Inject
	private RoundRepository roundRepos;

	public Match findBye(Round round) {
		for (Match match : round.getMatches()) {
			if (match.isBye()) {
				return match;
			}

		}
		return null;
	}

	public Round getNextRound(Event event) {
		Round round = matchMaker.createMatches(event);
		round.setEvent(event);
		event.getRounds().add(round);
		handleBye(round);
		round.setFinished(false);
		roundRepos.create(round);
		event.setCurrentRound(event.getCurrentRound() + 1);
		return round;
	}

	public Round revokeRound(Round round) {
		Event event = round.getEvent();
		/*
		 * First round cannot be reset
		 */
		if (event.getCurrentRound() == 1) {
			return round;
		}
		/*
		 * Last round resets itself if event is finished. Dont go to previous round.
		 */
		if (event.getCurrentRound() == event.getMaxNumberOfRounds() && round.getFinished()) {
			round.setFinished(false);
			return round;
		}
		/*
		 * Delete and reset all matches and go back to the previous round.
		 */
		int previousRound = event.getCurrentRound() - 1;
		round.getMatches().stream().filter(m -> m.isFinished()).forEach(matchService::revokeMatch);
		revokeBye(round);
		event.setCurrentRound(previousRound);
		event.getRounds().remove(round);
		Round prevRound = ((ArrayList<Round>) event.getRounds()).get(previousRound - 1);
		prevRound.setFinished(false);
		return prevRound;
	}

	private void revokeBye(Round round) {
		if (round.getBye() != null) {
			Standing standing = standingService.getStandingForPlayer(round.getBye(), round.getEvent());
			((ArrayList<MatchResult>) standing.getPlayedMatches()).remove(standing.getPlayedMatches().size() - 1);
			standingService.updateStanding(standing);
		}
	}

	private void handleBye(Round round) {
		if (round.getBye() != null) {
			Standing standing = standingService.getStandingForPlayer(round.getBye(), round.getEvent());
			MatchResult bye = matchService.createBye(round.getBye());
			bye.setStanding(standing);
			standing.addPlayedMatches(bye);
			standingService.updateStanding(standing);
		}
	}

	public boolean checkIfFinished(Round round) {
		Optional<Match> unfinishedMatches = round.getMatches().stream().filter(m -> !m.isFinished()).findAny();
		return !unfinishedMatches.isPresent();
	}

}
