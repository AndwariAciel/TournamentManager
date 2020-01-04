package de.andwari.tournamentcore.matchmaking.combinator;

import java.util.ArrayList;
import java.util.List;

import de.andwari.tournamentcore.utils.MathUtils;

public class Combinator<E> {

	public List<List<Pair<E>>> combinate(List<E> items) {
		if (MathUtils.isUneven(items.size())) {
			return combinateUneven(items);
		} else {
			return combinateEven(items);
		}
	}

	private List<List<Pair<E>>> combinateUneven(List<E> items) {
		List<List<Pair<E>>> listOfPairings = new ArrayList<>();
		for (int x = 0; x < items.size(); x++) {
			Pair<E> pair1 = new Pair<E>(items.get(x), null);
			ArrayList<Pair<E>> sublistOfPairings = new ArrayList<>();
			sublistOfPairings.add(pair1);
			ArrayList<E> sublist = new ArrayList<>(items);
			sublist.remove(x);
			if (sublist.isEmpty()) {
				listOfPairings.add(sublistOfPairings);
			}
			for (List<Pair<E>> list : combinateEven(sublist)) {
				sublistOfPairings.addAll(list);
				listOfPairings.add(sublistOfPairings);
				sublistOfPairings = new ArrayList<>();
				sublistOfPairings.add(pair1);
			}
		}
		return listOfPairings;
	}

	private List<List<Pair<E>>> combinateEven(List<E> items) {
		List<List<Pair<E>>> listOfPairings = new ArrayList<>();
		for (int x = 1; x < items.size(); x++) {
			Pair<E> pair1 = new Pair<E>(items.get(0), items.get(x));
			ArrayList<Pair<E>> sublistOfPairings = new ArrayList<>();
			sublistOfPairings.add(pair1);
			ArrayList<E> sublist = new ArrayList<>(items);
			sublist.remove(x);
			sublist.remove(0);
			if (sublist.isEmpty()) {
				listOfPairings.add(sublistOfPairings);
			}
			for (List<Pair<E>> list : combinateEven(sublist)) {
				sublistOfPairings.addAll(list);
				listOfPairings.add(sublistOfPairings);
				sublistOfPairings = new ArrayList<>();
				sublistOfPairings.add(pair1);
			}
		}

		return listOfPairings;
	}
}
