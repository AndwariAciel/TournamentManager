package de.andwari.tournamentcore.matchmaking.combinator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.matches.MatchService;
import de.andwari.tournamentcore.player.entity.Player;

public class PairingsCombinator {

	@Inject
	private MatchService matchService;
	
	@Inject
	private Combinator<Player> combinator;

	public List<Match[]> getAllPairings(List<Player> players) {
		List<List<Pair<Player>>> allPossibilities = combinator.combinate(players);
		return allPossibilities.stream().map(this::createMatchArray).collect(Collectors.toList());
	}

	private Match[] createMatchArray(List<Pair<Player>> listOfPairs) {
		ArrayList<Match> matches = (ArrayList<Match>) listOfPairs.stream().map(this::createMatch)
				.collect(Collectors.toList());
		return matches.toArray(new Match[0]);
	}

	private Match createMatch(Pair<Player> pair) {
		Match match = matchService.createMatch(pair.item1, pair.item2);
		return match;
	}

}
