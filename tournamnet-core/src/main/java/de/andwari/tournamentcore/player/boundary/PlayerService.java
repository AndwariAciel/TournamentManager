package de.andwari.tournamentcore.player.boundary;

import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.player.control.PlayerController;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.player.exceptions.NameNotUniqueException;
import de.andwari.tournamentcore.player.repos.PlayerRepository;

public class PlayerService {

	@Inject
	private PlayerController playerControl;

	@Inject
	private PlayerRepository playerRepos;

	public Player addPlayer(String name, String dci, Boolean membership) throws NameNotUniqueException {
		return playerControl.addPlayer(name, dci, membership);
	}

	public List<Player> getListOfAllPlayers() {
		return playerControl.getListOfAllPlayers();
	}

	public void deletePlayer(long id) {
		playerControl.deletePlayer(id);
	}

	public void updatePlayer(long id, String name, String dci, Boolean member) {
		playerControl.update(id, name, dci, member);
	}

	public Player findPlayerByName(String name) {
		return playerRepos.findByName(name);
	}
}
