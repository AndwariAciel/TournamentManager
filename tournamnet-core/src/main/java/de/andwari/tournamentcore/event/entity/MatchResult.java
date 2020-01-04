package de.andwari.tournamentcore.event.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable
public class MatchResult extends BaseEntity {

	@DatabaseField(foreign = true)
	private Player opponent;

	@DatabaseField
	private int score;

	@DatabaseField
	private int scoreOpponent;

	@DatabaseField
	private boolean bye;

	@DatabaseField
	private boolean loss;

	@DatabaseField(foreign = true)
	private Standing standing;

	@DatabaseField(foreign = true)
	private Match correspondingMatch;

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScoreOpponent() {
		return scoreOpponent;
	}

	public void setScoreOpponent(int score_opponent) {
		this.scoreOpponent = score_opponent;
	}

	public boolean isBye() {
		return bye;
	}

	public void setBye(boolean bye) {
		this.bye = bye;
	}

	public boolean isLoss() {
		return loss;
	}

	public void setLoss(boolean loss) {
		this.loss = loss;
	}

	public Standing getStanding() {
		return standing;
	}

	public void setStanding(Standing standing) {
		this.standing = standing;
	}

	public Match getCorrespondingMatch() {
		return correspondingMatch;
	}

	public void setCorrespondingMatch(Match correspondingMatch) {
		this.correspondingMatch = correspondingMatch;
	}

	public String toString() {
		return "Opponent: " + opponent + " -- " + score + "-" + scoreOpponent;
	}
}
