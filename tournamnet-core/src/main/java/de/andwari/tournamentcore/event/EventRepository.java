package de.andwari.tournamentcore.event;

import javax.inject.Inject;

import de.andwari.tournamentcore.database.BaseRepository;
import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.rounds.RoundRepository;
import de.andwari.tournamentcore.standings.StandingRepository;

public class EventRepository extends BaseRepository<Event> {
	
	@Inject
	private StandingRepository standingRepos;
	
	@Inject
	private RoundRepository roundRepos;
	
	@Override
	public void create(Event event) {	
		super.create(event);
		event.getRankings().stream().forEach(standingRepos::create);
		event.getRounds().stream().forEach(roundRepos::create);
	}
	
	@Override
	public void update(Event event) {
		super.update(event);
		event.getRankings().stream().forEach(standingRepos::update);
		event.getRounds().stream().forEach(roundRepos::update);
	}
}
