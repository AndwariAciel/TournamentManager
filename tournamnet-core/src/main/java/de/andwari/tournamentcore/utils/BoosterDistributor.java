package de.andwari.tournamentcore.utils;

public class BoosterDistributor {

	private final static int[][] distributionMatrix = { { 3, 2, 1 }, // 6
			{ 3, 2, 2 }, // 7
			{ 3, 2, 2, 1 }, // 8
			{ 4, 2, 2, 1 }, // 9
			{ 4, 2, 2, 1, 1 }, // 10
			{ 4, 3, 2, 1, 1 }, // 11
			{ 4, 3, 2, 1, 1, 1 }, // 12
			{ 4, 3, 2, 2, 1, 1 }, // 13
			{ 4, 3, 2, 2, 1, 1, 1 }, // 14
			{ 4, 3, 3, 2, 1, 1, 1 }, // 15
			{ 4, 3, 3, 2, 1, 1, 1, 1 }, // 16
			{ 5, 3, 3, 2, 1, 1, 1, 1 }, // 17
			{ 5, 3, 3, 2, 1, 1, 1, 1, 1 }, // 18
			{ 5, 3, 3, 2, 2, 1, 1, 1, 1 }, // 19
			{ 5, 3, 3, 2, 2, 1, 1, 1, 1, 1 }, // 20
			{ 5, 4, 3, 2, 2, 1, 1, 1, 1, 1 } // 21
	};
	
	public static int min = 6;
	public final static int max = 21;

	public int[] getBoosterDistribution(int numberOfPlayers) {
		if (numberOfPlayers < min)
			return new int[0];
		if (numberOfPlayers > max) {
			numberOfPlayers = max;
		}
		return distributionMatrix[numberOfPlayers - min];
	}

}
