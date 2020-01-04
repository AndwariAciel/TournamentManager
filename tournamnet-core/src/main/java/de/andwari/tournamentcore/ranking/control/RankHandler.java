package de.andwari.tournamentcore.ranking.control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.player.repos.PlayerRepository;
import de.andwari.tournamentcore.ranking.entity.Rank;
import de.andwari.tournamentcore.ranking.repository.RankRepository;

public class RankHandler {

	@Inject
	private RankRepository repository;

	@Inject
	private PlayerRepository playerRepository;

	public void distributePoints(Player player, int points, Calendar time) {
		Rank rank = repository.findByPlayerAndTime(player, time.get(Calendar.YEAR), Calendar.MONTH);
		if (rank == null) {
			rank = createNewRank(player, time, points);
			repository.create(rank);
		} else {
			rank.setPoints(rank.getPoints() + points);
			repository.update(rank);
		}
	}

	private Rank createNewRank(Player player, Calendar time, int points) {
		Rank rank = new Rank();
		rank.setPlayer(player);
		rank.setYear(time.get(Calendar.YEAR));
		rank.setMonth(time.get(Calendar.MONTH));
		rank.setPoints(points);
		return rank;
	}

	public void saveRankings(List<Rank> rankings) {
		rankings.stream().filter(r -> r.getPoints() > 0).forEach(repository::create);
	}

	public List<PlayerRank> getRankings(int year) {
		List<Rank> allRanks = repository.findAllForYear(year);
		return collectAndSortRanks(allRanks);
	}

	private List<PlayerRank> collectAndSortRanks(List<Rank> allRanks) {
		List<List<Rank>> playerRanks = filterByPlayers(allRanks);
		List<PlayerRank> ranks = new ArrayList<>();
		playerRanks.forEach(b -> ranks.add(sumUpRanks(b)));
		ranks.sort((r1, r2) -> -r1.points.compareTo(r2.points));
		for (int x = 0; x < ranks.size(); x++) {
			ranks.get(x).rank = x + 1;
			ranks.get(x).player = playerRepository.findById(ranks.get(x).player.getId());
		}
		return ranks;
	}

	private PlayerRank sumUpRanks(List<Rank> playerRanks) {
		int sum = playerRanks.stream().mapToInt(r -> r.getPoints()).sum();
		PlayerRank playerRank = new PlayerRank();
		playerRank.player = playerRanks.get(0).getPlayer();
		playerRank.points = new Integer(sum);
		return playerRank;
	}

	private List<List<Rank>> filterByPlayers(List<Rank> allRanks) {
		List<Player> players = allRanks.stream().map(r -> r.getPlayer()).distinct().collect(Collectors.toList());
		List<List<Rank>> playerRanks = new ArrayList<>();
		for (Player player : players) {
			playerRanks.add(allRanks.stream().filter(r -> r.getPlayer().equals(player)).collect(Collectors.toList()));
		}
		return playerRanks;
	}

	public List<PlayerRank> getRankings(int year, int month) {
		List<Rank> allRanks = repository.findAllForYearAndMonth(year, month);
		return collectAndSortRanks(allRanks);
	}

	public List<String> getYears() {
		return repository.findYearsDistinct().stream().map(r -> Integer.toString(r.getYear()))
				.collect(Collectors.toList());
	}

	public void saveRank(Rank rank) {
		repository.create(rank);
	}

	public void resetGivenRankingPoints(Event event) {
		if (event.getRankingPoints()) {
			repository.deleteForEvent(event);
		}
	}

}
