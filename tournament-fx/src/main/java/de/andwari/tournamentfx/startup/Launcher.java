package de.andwari.tournamentfx.startup;

public class Launcher {

	/**
	 * The maven shade plugin seems to require a main class that does not extend the
	 * javafx application. So this is a simple main method thats launches a javafx
	 * application.
	 */
	public static void main(String[] args) {
		CdiApplication.launchAppWithCdi();
	}

}
