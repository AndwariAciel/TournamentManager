package de.andwari.tournamentcore.matches;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.standings.StandingService;
import de.andwari.tournamentcore.utils.exceptions.InvalidMatchException;

public class MatchService {

	@Inject
	private MatchValidator validator;

	@Inject
	private StandingService standingService;

	public Match createMatch(Player player1, Player player2) {
		Match match = new Match();
		match.setPlayer1(player1);
		match.setPlayer2(player2);
		if (player2 == null) {
			match.setBye(true);
		}
		match.setScorePlayer1(0);
		match.setScorePlayer2(0);
		match.setFinished(false);
		return match;
	}

	public Match finishMatch(Match match) throws InvalidMatchException {
		if (!validator.validate(match)) {
			throw new InvalidMatchException();
		}
		MatchResult resultPlayer1 = createMatchPlayer1(match);
		Standing standingPlayer1 = standingService.getStandingForPlayer(match.getPlayer1(),
				match.getRound().getEvent());
		standingPlayer1.addPlayedMatches(resultPlayer1);
		standingService.updateStanding(standingPlayer1);

		MatchResult resultPlayer2 = createMatchPlayer2(match);
		Standing standingPlayer2 = standingService.getStandingForPlayer(match.getPlayer2(),
				match.getRound().getEvent());
		standingPlayer2.addPlayedMatches(resultPlayer2);
		standingService.updateStanding(standingPlayer2);

		match.setFinished(true);

		standingService.updateOpponentScores(match.getRound().getEvent());
		return match;

	}

	public void revokeMatch(Match match) {
		Standing standingPlayer1 = standingService.getStandingForPlayer(match.getPlayer1(),
				match.getRound().getEvent());
		standingService.deleteMatch(standingPlayer1, match);
		standingService.updateStanding(standingPlayer1);

		Standing standingPlayer2 = standingService.getStandingForPlayer(match.getPlayer2(),
				match.getRound().getEvent());
		standingService.deleteMatch(standingPlayer2, match);
		standingService.updateStanding(standingPlayer2);

		standingService.updateOpponentScores(match.getRound().getEvent());

		match.setScorePlayer1(0);
		match.setScorePlayer2(0);
		match.setFinished(false);
	}

	public MatchResult createBye(Player player) {
		MatchResult matchResult = new MatchResult();
		matchResult.setScore(2);
		matchResult.setScoreOpponent(0);
		matchResult.setBye(true);
		return matchResult;
	}

	public MatchResult createLoss() {
		MatchResult result = new MatchResult();
		result.setScore(0);
		result.setScoreOpponent(0);
		result.setLoss(true);
		return result;
	}

	private MatchResult createMatchPlayer2(Match match) {
		MatchResult matchResult = new MatchResult();
		matchResult.setOpponent(match.getPlayer1());
		matchResult.setScore(match.getScorePlayer2());
		matchResult.setScoreOpponent(match.getScorePlayer1());
		matchResult.setCorrespondingMatch(match);
		return matchResult;
	}

	private MatchResult createMatchPlayer1(Match match) {
		MatchResult matchResult = new MatchResult();
		matchResult.setOpponent(match.getPlayer2());
		matchResult.setScore(match.getScorePlayer1());
		matchResult.setScoreOpponent(match.getScorePlayer2());
		matchResult.setCorrespondingMatch(match);
		return matchResult;
	}
}
