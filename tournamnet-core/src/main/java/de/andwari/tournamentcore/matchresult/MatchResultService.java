package de.andwari.tournamentcore.matchresult;

import de.andwari.tournamentcore.event.entity.MatchResult;

public class MatchResultService {

	public int getMatchPoints(MatchResult result) {
		if(result.getScore() > result.getScoreOpponent()) {
			return 3;
		} else if(result.getScore() == result.getScoreOpponent()) {
			return 1;
		}
		return 0;
	}

	public int getGamePoints(MatchResult result) {		
		return result.getScore() * 3;
	}

}
