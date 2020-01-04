package de.andwari.tournamentcore.matches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.rounds.RoundRepository;
import de.andwari.tournamentcore.standings.StandingService;
import de.andwari.tournamentcore.utils.MathUtils;

public class MatchFactory {

	@Inject
	private MatchService matchService;

	@Inject
	private RoundRepository roundRepos;

	@Inject
	private StandingService standingService;

	public Round createCrosspairings(Event event) {
		Round round = new Round();
		round.setEvent(event);
		List<Player> seatings = new ArrayList<>(event.getSeatings());
		Player bye = checkForBye(seatings);
		if (bye != null) {
			round.setBye(bye);
			makeResultForBye(round);
		}
		List<Match> matches = new ArrayList<>();
		matches.addAll(getCrossPairingsForEvenPlayers(seatings));
		round.setMatches(matches);
		round.setFinished(false);
		for (Match match : matches) {
			match.setRound(round);
		}
		roundRepos.create(round);
		return round;
	}

	public Round createManualPairings(Event event, List<Player> left, List<Player> right, List<Player> unassigned,
			Player bye) {
		Round round = new Round();
		round.setEvent(event);
		round.setMatches(new ArrayList<>());

		for (int x = 0; x < left.size(); x++) {
			Player leftPlayer = left.get(x);
			Player rightPlayer = right.get(x);
			if (leftPlayer.getPlayerName() == null) {
				leftPlayer = getRandom(unassigned);
			}
			if (rightPlayer.getPlayerName() == null) {
				rightPlayer = getRandom(unassigned);
			}
			round.getMatches().add(matchService.createMatch(leftPlayer, rightPlayer));
		}
		if (bye != null) {
			round.setBye(bye);
			makeResultForBye(round);
		} else if (unassigned.size() == 1) {
			round.setBye(unassigned.get(0));
			makeResultForBye(round);
		}
		round.setFinished(false);
		for (Match match : round.getMatches()) {
			match.setRound(round);
		}
		roundRepos.create(round);
		return round;
	}

	private Player getRandom(List<Player> unassigned) {
		int rnd = MathUtils.getRandomNumberFrom(unassigned.size());
		Player p = unassigned.get(rnd);
		unassigned.remove(p);
		return p;
	}

	private Collection<? extends Match> getCrossPairingsForEvenPlayers(List<Player> seatings) {
		int cross = seatings.size() / 2;
		ArrayList<Match> matches = new ArrayList<>();
		for (int x = 0; x < cross; x++) {
			matches.add(matchService.createMatch(seatings.get(x), seatings.get(x + cross)));
		}
		return matches;
	}

	private Player checkForBye(List<Player> seatings) {
		if (MathUtils.isUneven(seatings.size())) {
			Player player = seatings.get(MathUtils.getRandomNumberFrom(seatings.size()));
			seatings.remove(player);
			return player;
		}
		return null;
	}

	private void makeResultForBye(Round round) {
		Standing standing = standingService.getStandingForPlayer(round.getBye(), round.getEvent());
		MatchResult bye = matchService.createBye(round.getBye());
		bye.setStanding(standing);
		standing.addPlayedMatches(bye);
		standingService.updateStanding(standing);
		standingService.updateOpponentScores(round.getEvent());
	}

}
