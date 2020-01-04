package de.andwari.tournamentcore.player.control;

import java.util.List;

import javax.inject.Inject;

import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.player.exceptions.NameNotUniqueException;
import de.andwari.tournamentcore.player.repos.PlayerRepository;

public class PlayerController {

	@Inject
	private PlayerRepository playerRepo;

	public PlayerController() {
	}

	public Player addPlayer(String name, String dci, Boolean membership) throws NameNotUniqueException {
		validateUnique(name);
		Player player = new Player(name);
		player.setDci(dci.isEmpty() ? null : dci);
		player.setMember(membership);
		playerRepo.create(player);
		return player;
	}

	private void validateUnique(String name) throws NameNotUniqueException {
		Player p = playerRepo.findByName(name);
		if (p != null) {
			throw new NameNotUniqueException();
		}
	}

	public List<Player> getListOfAllPlayers() {
		return playerRepo.findAll();
	}

	public void deletePlayer(long id) {
		playerRepo.delete(id);
	}

	public void update(long id, String name, String dci, Boolean member) {
		Player player = playerRepo.findById(id);
		player.setPlayerName(name);
		player.setDci(dci);
		player.setMember(member);
		playerRepo.update(player);
	}

	public Player load(Player p) {
		return playerRepo.findByName(p.getPlayerName());
	}

}
