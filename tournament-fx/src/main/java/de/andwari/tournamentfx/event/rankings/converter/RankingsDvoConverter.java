package de.andwari.tournamentfx.event.rankings.converter;

import java.math.RoundingMode;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.standings.StandingService;
import de.andwari.tournamentfx.event.rankings.dvos.RankingsDvo;

public class RankingsDvoConverter {

	@Inject
	private StandingService standingService;

	public RankingsDvo convertToDvo(Standing standing) {
		RankingsDvo dvo = new RankingsDvo();
		dvo.setPlayer(standing.getPlayer().getPlayerName());
		dvo.setScore(Integer.toString(standing.getScore()));
		dvo.setOpGamesScorer(standing.getOpponentGameWinPercentage().setScale(4, RoundingMode.HALF_UP).toPlainString());
		dvo.setOpMatchScore(standing.getOpponentMatchWinPercentage().setScale(4, RoundingMode.HALF_UP).toPlainString());
		dvo.setGameScore(standing.getGameWinPercentage().setScale(4, RoundingMode.HALF_UP).toPlainString());
		dvo.setScoreString(standingService.getScoreString(standing));
		dvo.setStandingId(standing.getId());
		dvo.setDropped(standing.getDropped());
		return dvo;
	}

	public Standing convertToEntity(RankingsDvo dvo, Round round) {
		return round.getEvent().getRankings().stream().filter(standing -> standing.getId() == dvo.getStandingId())
				.findFirst().get();
	}

}
