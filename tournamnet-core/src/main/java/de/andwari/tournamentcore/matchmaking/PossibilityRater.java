package de.andwari.tournamentcore.matchmaking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.utils.MathUtils;

public class PossibilityRater {
	
	public ArrayList<Possibility> ratePossibilities(Pool pool, ArrayList<Player> unmatchedPlayers) {
		TreeMap<Integer, ArrayList<Possibility>> map = new TreeMap<>();
		pool.possibilities.stream().forEach(p -> addToMap(map, p, unmatchedPlayers));
		map.values().stream().forEach(Collections::shuffle);
		ArrayList<Possibility> orderedPossibilities = new ArrayList<>();
		map.values().stream().forEach(orderedPossibilities::addAll);
		return orderedPossibilities;
	}

	private void addToMap(Map<Integer, ArrayList<Possibility>> map, Possibility p, ArrayList<Player> unmatchedPlayers) {
		Integer rating = ratePossibility(p, unmatchedPlayers);
		if(!map.containsKey(rating)) {
			map.put(rating, new ArrayList<>());
		}
		map.get(rating).add(p);
	}
	
	private Integer ratePossibility(Possibility possibility, ArrayList<Player> unmatchedPlayers) {
		int startRating = getStartRating(possibility.getRatingClass());
		startRating += possibility.unmatchedPlayers.stream().filter(p -> unmatchedPlayers.contains(p))
				.collect(Collectors.toList()).size();
		return startRating;
	}

	private int getStartRating(int size) {
		int rating = 0;
		if (MathUtils.isUneven(size)) {
			for (int x = 1; x <= (size - 1) / 2; x++) {
				rating += 2 * x;
			}
		} else {
			for (int x = 0; x < size / 2; x++) {
				rating += 2 * x + 1;
			}
		}
		return rating;
	}
	
}
