package de.andwari.tournamentcore.matches;

import de.andwari.tournamentcore.event.entity.Match;

public class MatchValidator {

	public boolean validate(Match match) {
		if(match == null) {
			return false;
		}
		if(match.getScorePlayer1() > 2 || match.getScorePlayer1() < 0) {
			return false;
		}
		if(match.getScorePlayer2() > 2 || match.getScorePlayer2() < 0) {
			return false;
		}
		if(match.getScorePlayer1() == 2 && match.getScorePlayer2() == 2) {
			return false;
		}
		return true;
	}
}
