package de.andwari.tournamentcore.ranking.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.ranking.entity.Rank;
import de.andwari.tournamentcore.standings.StandingService;

public class RankingPointsDistributor {

	private static final int BASE_POINTS = 3;
	
	@Inject
	private StandingService service;
	
	public List<Rank> getRankPointsDistribution(Event event) {
		List<Rank> rankDistribution = new ArrayList<>();
		ArrayList<Standing> sortedRankings = service.getRankings(event.getRankings());
		int numberOfPlayer = sortedRankings.size();
		for(int x = 0; x < sortedRankings.size(); x++) {
			int points = 0;
			if(sortedRankings.get(x).getPlayer().getMember()) {
				points = BASE_POINTS + numberOfPlayer - (x+1);
			} 
			rankDistribution.add(createRank(points, sortedRankings.get(x).getPlayer(), event));
		}
		return rankDistribution;
	}

	private Rank createRank(int points, Player player, Event event) {
		Rank rank = new Rank();
		rank.setPoints(points);
		rank.setPlayer(player);
		rank.setEvent(event);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(event.getCreated());
		rank.setYear(calendar.get(Calendar.YEAR));
		rank.setMonth(calendar.get(Calendar.MONTH));
		return rank;
	}

}
