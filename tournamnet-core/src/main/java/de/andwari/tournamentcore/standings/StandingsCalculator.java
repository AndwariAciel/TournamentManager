package de.andwari.tournamentcore.standings;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.matchresult.MatchResultService;
import de.andwari.tournamentcore.player.entity.Player;

public class StandingsCalculator {

	@Inject
	private MatchResultService resultService;

	public void calculateStandingsForEvent(Event event) {
		for (Standing standing : event.getRankings()) {
			calculateOpponentScore(standing, event);
		}

	}

	public void calculateStanding(Standing standing) {
		int matchScore = 0;
		int gameScore = 0;
		int roundsPlayed = 0;
		int gamesPlayed = 0;
		for (MatchResult result : standing.getPlayedMatches()) {
			roundsPlayed++;
			gamesPlayed += result.getScore() + result.getScoreOpponent();
			matchScore += resultService.getMatchPoints(result);
			gameScore += resultService.getGamePoints(result);
		}

		BigDecimal matchWinPercentage = getWinPercentage(matchScore, roundsPlayed);
		BigDecimal gameWinPercentage = getWinPercentage(gameScore, gamesPlayed);

		standing.setGameWinPercentage(gameWinPercentage);
		standing.setMatchWinPercentage(matchWinPercentage);
		standing.setScore(matchScore);
	}

	private BigDecimal getWinPercentage(int matchScore, int roundsPlayed) {
		BigDecimal third = BigDecimal.ONE.divide(new BigDecimal(3), 10, RoundingMode.HALF_UP);
		if (roundsPlayed == 0) {
			return third;
		}
		BigDecimal score = new BigDecimal(matchScore);
		BigDecimal rounds = new BigDecimal(roundsPlayed);
		BigDecimal result = score.divide(rounds.multiply(new BigDecimal(3)), 10, RoundingMode.HALF_UP);
		if (result.compareTo(third) == -1) {
			result = third;
		}
		return result;
	}

	private void calculateOpponentScore(Standing standing, Event event) {
		BigDecimal opponentMatchScore = BigDecimal.ZERO;
		BigDecimal opponentGameScore = BigDecimal.ZERO;
		int numberOfOpponents = 0;
		if (standing.getPlayedMatches() != null) {
			for (MatchResult result : standing.getPlayedMatches()) {
				if (!result.isBye()) {
					numberOfOpponents++;
					Player opponent = result.getOpponent();
					Standing opponentStanding = getStandingForPlayer(opponent, event);
					opponentGameScore = opponentGameScore.add(opponentStanding.getGameWinPercentage());
					opponentMatchScore = opponentMatchScore.add(opponentStanding.getMatchWinPercentage());
				}
			}
		}
		if (numberOfOpponents != 0) {
			opponentGameScore = opponentGameScore.divide(new BigDecimal(numberOfOpponents), 10, RoundingMode.HALF_DOWN);
			opponentMatchScore = opponentMatchScore.divide(new BigDecimal(numberOfOpponents), 10,
					RoundingMode.HALF_DOWN);
		}
		standing.setOpponentGameWinPercentage(opponentGameScore);
		standing.setOpponentMatchWinPercentage(opponentMatchScore);
	}

	private Standing getStandingForPlayer(Player opponent, Event event) {
		return event.getRankings().stream().filter(standing -> standing.getPlayer().equals(opponent)).findAny().get();
	}

}
