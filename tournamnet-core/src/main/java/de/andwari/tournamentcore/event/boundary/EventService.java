package de.andwari.tournamentcore.event.boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.EventRepository;
import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.standings.StandingService;
import de.andwari.tournamentcore.utils.MathUtils;

public class EventService {

	@Inject
	private EventRepository eventRepos;

	@Inject
	private StandingService standingService;

	public Event createNewEvent(List<Player> players) {
		Event event = new Event();
		event.setRankings(
				players.stream().map(p -> standingService.createStanding(event, p)).collect(Collectors.toList()));
		eventRepos.create(event);
		return event;
	}

	public List<Player> createSeatings(Event event) {
		ArrayList<Player> copyOfList = (ArrayList<Player>) standingService.getListOfPlayers(event.getRankings());
		ArrayList<Player> seatings = new ArrayList<>();
		while (!copyOfList.isEmpty()) {
			int rnd = MathUtils.getRandomNumberFrom(copyOfList.size());
			seatings.add(copyOfList.get(rnd));
			copyOfList.remove(rnd);
		}
		event.setSeatings(seatings);
		return seatings;
	}

	public int getMaxNumberOfRounds(int numberOfPlayers) {
		int round = 0;
		while (Math.pow(2, round) < numberOfPlayers) {
			round++;
		}
		return round;
	}

	public void addPlayer(Event event, Player player) {
		Collection<Standing> standings = event.getRankings();
		Standing losses = standingService.createLosses(player, event.getCurrentRound());
		losses.setEvent(event);
		standings.add(losses);
	}
}
