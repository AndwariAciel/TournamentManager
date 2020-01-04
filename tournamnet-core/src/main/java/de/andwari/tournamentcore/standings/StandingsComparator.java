package de.andwari.tournamentcore.standings;

import java.util.Comparator;

import de.andwari.tournamentcore.event.entity.Standing;

public class StandingsComparator implements Comparator<Standing> {

	@Override
	public int compare(Standing s1, Standing s2) {
		if (s1.getPlayedMatches() == null) {
			return -1;
		}
		if (s1.getScore() == s2.getScore()) {
			if (s1.getOpponentMatchWinPercentage().equals(s2.getOpponentMatchWinPercentage())) {
				if (s1.getGameWinPercentage().equals(s2.getGameWinPercentage())) {
					return s1.getOpponentGameWinPercentage().compareTo(s2.getOpponentGameWinPercentage());
				}
				return s1.getGameWinPercentage().compareTo(s2.getGameWinPercentage());
			}
			return s1.getOpponentMatchWinPercentage().compareTo(s2.getOpponentMatchWinPercentage());
		}
		return s1.getScore().compareTo(s2.getScore());
	}
}
