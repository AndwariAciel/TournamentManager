package de.andwari.tournamentcore.event.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable
public class Match extends BaseEntity {

	public Match() {
	}

	@DatabaseField(foreign = true)
	private Player player1;

	@DatabaseField(foreign = true)
	private Player player2;

	@DatabaseField
	private int scorePlayer1;

	@DatabaseField
	private int scorePlayer2;

	@DatabaseField
	private boolean bye = false;

	@DatabaseField
	private boolean finished = true;

	@DatabaseField(foreign = true)
	private Standing standing;

	@DatabaseField(foreign = true)
	private Round round;

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public int getScorePlayer1() {
		return scorePlayer1;
	}

	public void setScorePlayer1(int scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	public int getScorePlayer2() {
		return scorePlayer2;
	}

	public void setScorePlayer2(int scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}

	public boolean isBye() {
		return bye;
	}

	public void setBye(boolean bye) {
		this.bye = bye;
	}

	public Standing getStanding() {
		return standing;
	}

	public void setStanding(Standing standing) {
		this.standing = standing;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String toString() {
		return player1 + " vs " + player2;
	}
}
