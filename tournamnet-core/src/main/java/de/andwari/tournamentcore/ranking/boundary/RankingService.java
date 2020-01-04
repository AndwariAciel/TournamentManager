package de.andwari.tournamentcore.ranking.boundary;

import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.player.repos.PlayerRepository;
import de.andwari.tournamentcore.ranking.control.PlayerRank;
import de.andwari.tournamentcore.ranking.control.RankHandler;
import de.andwari.tournamentcore.ranking.control.RankingPointsDistributor;
import de.andwari.tournamentcore.ranking.entity.Rank;

public class RankingService {

	@Inject
	private RankHandler handler;

	@Inject
	private PlayerRepository playerRepos;

	@Inject
	private RankingPointsDistributor distributor;

	public void saveRankings(List<Rank> rankings) {
		handler.saveRankings(rankings);
	}

	public List<Rank> getRankPointsDistribution(Event event) {
		return distributor.getRankPointsDistribution(event);
	}

	public List<PlayerRank> getRankings(int year) {
		return handler.getRankings(year);
	}

	public List<PlayerRank> getRankings(int year, int month) {
		return handler.getRankings(year, month);
	}

	public List<String> getYears() {
		return handler.getYears();
	}

	public List<Player> getAllPlayersWithMembership() {
		return playerRepos.findAllPlayersWithMembership();
	}

	public void saveRank(Rank rank) {
		handler.saveRank(rank);
	}

	public void resetRankingsForEvent(Event event) {
		handler.resetGivenRankingPoints(event);
	}
}
