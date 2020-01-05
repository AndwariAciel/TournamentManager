# TournamentManager
New version of my tournament manager

## Project structure
This is a modular maven project. It consists of two modules, the tournament-fx and tournament-core.
The fx-module contains everything related to the GUI, specifically JavaFX. It knows the core and uses its logic.

The core itself doesn't know the fx and is completely independent of it.

## Maven setup
The project is compiled with Java 13.

The maven-shade plugin is used to create an executable jar-file. It is generated within the tournament-fx/shade folder. A simple *clean install* on the main pom is enough.

