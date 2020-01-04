package de.andwari.tournamentfx.event.matches.control;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.boundary.EventService;
import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.ranking.boundary.RankingService;
import de.andwari.tournamentcore.rounds.RoundService;
import de.andwari.tournamentcore.standings.StandingService;
import de.andwari.tournamentfx.event.matches.converter.MatchDvoConverter;
import de.andwari.tournamentfx.event.matches.dvos.MatchListDvo;
import de.andwari.tournamentfx.event.rankings.converter.RankingsDvoConverter;
import de.andwari.tournamentfx.event.rankings.dvos.RankingsDvo;

public class MatchesPageService {

	@Inject
	private MatchDvoConverter converter;

	@Inject
	private StandingService standingService;

	@Inject
	private RankingsDvoConverter rankingsConverter;

	@Inject
	private RankingService rankingService;

	@Inject
	private RoundService roundService;

	@Inject
	private EventService eventService;

	public List<MatchListDvo> getListOfDvos(Round round) {
		List<MatchListDvo> listOfDvos = new ArrayList<>();
		for (Match match : round.getMatches()) {
			listOfDvos.add(converter.convertToDvo(match));
		}
		return listOfDvos;
	}

	public ArrayList<RankingsDvo> getRankings(Event event) {
		ArrayList<Standing> orderdStandings = standingService.getRankings(event.getRankings());
		ArrayList<RankingsDvo> orderedDvos = new ArrayList<>();
		int rank = 1;
		for (Standing standing : orderdStandings) {
			RankingsDvo dvo = rankingsConverter.convertToDvo(standing);
			dvo.setRank(Integer.toString(rank));
			orderedDvos.add(dvo);
			rank++;

		}
		return orderedDvos;
	}

	public boolean validateRound(Round round) {
		return roundService.checkIfFinished(round);
	}

	public Round createNextRound(Event event) {
		return roundService.getNextRound(event);
	}

	public void dropPlayer(Standing standing) {
		standingService.dropPlayer(standing);
	}

	public Round revokeRound(Round round) {
		return roundService.revokeRound(round);
	}

	public void resetGivenRankingPoints(Event event) {
		rankingService.resetRankingsForEvent(event);
	}

    public void addPlayerToEvent(Event event, Player player) {
        eventService.addPlayer(event, player);
    }
}
