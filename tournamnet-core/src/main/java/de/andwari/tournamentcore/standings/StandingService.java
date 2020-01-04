package de.andwari.tournamentcore.standings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.exception.HasOngoingMatchException;
import de.andwari.tournamentcore.matchresult.MatchResultRepository;
import de.andwari.tournamentcore.player.entity.Player;

public class StandingService {

	@Inject
	private StandingsCalculator standingCalculator;

	@Inject
	private MatchResultRepository resultRepos;

	@Inject
	private StandingRepository standingRepos;

	public List<Player> getListOfPlayers(Collection<Standing> standings) {
		ArrayList<Player> players = new ArrayList<>();
		for (Standing standing : standings) {
			players.add(standing.getPlayer());
		}
		return players;
	}

	public Standing getStandingForPlayer(Player player, Event event) {
		return event.getRankings().stream().filter(standing -> standing.getPlayer().equals(player)).findAny().get();
	}

	public void updateStanding(Standing standing) {
		standingCalculator.calculateStanding(standing);
	}

	public void updateOpponentScores(Event event) {
		standingCalculator.calculateStandingsForEvent(event);
	}

	public void deleteMatch(Standing standing, Match match) {
		MatchResult result = standing.getPlayedMatches().stream().filter(m -> m.getCorrespondingMatch().equals(match))
				.findFirst().get();
		standing.getPlayedMatches().remove(result);
//		resultRepos.delete(result.getId());
	}

	public ArrayList<Standing> getRankings(Collection<Standing> oldStandings) {
		ArrayList<Standing> standings = new ArrayList<>(oldStandings);
		standings = (ArrayList<Standing>) standings.stream().collect(Collectors.toList());
		Collections.sort(standings, new StandingsComparator());
		Collections.reverse(standings);
		return standings;
	}

	public String getScoreString(Standing standing) {
		int wins = 0;
		int draws = 0;
		int losses = 0;
		if (standing.getPlayedMatches() == null) {
			return "0-0-0";
		}
		for (MatchResult match : standing.getPlayedMatches()) {
			if (match.getScore() > match.getScoreOpponent()) {
				wins++;
			} else if (match.getScore() == match.getScoreOpponent()) {
				draws++;
			} else {
				losses++;
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(wins);
		sb.append("-");
		sb.append(draws);
		sb.append("-");
		sb.append(losses);
		return sb.toString();
	}

	public Standing createStanding(Event event, Player p) {
		Standing standing = new Standing(p);
		standing.setEvent(event);
		return standing;
	}

	public void dropPlayer(Standing standing) {
		if (hasOngoingMatch(standing)) {
			throw new HasOngoingMatchException();
		}
		standing.setDropped(true);
		standingRepos.update(standing);
	}

	private boolean hasOngoingMatch(Standing standing) {
		Round round = ((ArrayList<Round>) standing.getEvent().getRounds())
				.get(standing.getEvent().getCurrentRound() - 1);
		Optional<Match> match = round.getMatches().stream().filter(m -> containsPlayer(m, standing)).findFirst();
		if (match.isPresent()) {
			return !match.get().isFinished();
		}
		return false;

	}

	private boolean containsPlayer(Match m, Standing s) {
		if (m.getPlayer1() != null && m.getPlayer1().equals(s.getPlayer())) {
			return true;
		}
		if (m.getPlayer2() != null && m.getPlayer2().equals(s.getPlayer())) {
			return true;
		}
		return false;
	}

	public Standing createLosses(Player player, int currentRound) {
		Standing standing = new Standing(player);
		standing.setPlayedMatches(new ArrayList<>());
		for (int x = 0; x < currentRound - 1; x++) {
			standing.addPlayedMatches(createLoss());
		}
		standingCalculator.calculateStanding(standing);
		return standing;
	}

	private MatchResult createLoss() {
		MatchResult result = new MatchResult();
		result.setScore(0);
		result.setScoreOpponent(0);
		result.setLoss(true);
		return result;
	}

}
