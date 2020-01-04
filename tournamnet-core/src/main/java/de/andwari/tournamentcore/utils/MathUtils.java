package de.andwari.tournamentcore.utils;

public class MathUtils {

	public static int getRandomNumberFrom(int x) {
		double rnd = Math.random();
		double x2 = (x * rnd);
		return (int) x2;
	}

	public static boolean isUneven(int size) {
		if( size % 2 == 0)
			return false;
		return true;
	}
	
}
