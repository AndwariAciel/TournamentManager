package de.andwari.tournamentcore.event.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable
public class Standing extends BaseEntity {

	public Standing() {
		playedMatches = new ArrayList<>();
	}

	public Standing(Player player) {
		this();
		this.player = player;
		this.score = 0;
		this.matchWinPercentage = BigDecimal.ZERO;
		this.gameWinPercentage = BigDecimal.ZERO;
		this.opponentGameWinPercentage = BigDecimal.ZERO;
		this.opponentMatchWinPercentage = BigDecimal.ZERO;
		this.dropped = false;
	}

	@DatabaseField(foreign = true)
	private Event event;

	@DatabaseField(foreign = true)
	private Player player;

	@ForeignCollectionField(eager = true)
	private Collection<MatchResult> playedMatches;

	@DatabaseField
	BigDecimal gameWinPercentage;

	@DatabaseField
	BigDecimal matchWinPercentage;

	@DatabaseField
	Integer score;

	@DatabaseField
	BigDecimal opponentGameWinPercentage;

	@DatabaseField
	BigDecimal opponentMatchWinPercentage;
	
	@DatabaseField
	private
	Boolean dropped;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void addPlayedMatches(MatchResult playedMatch) {
		if (getPlayedMatches() == null) {
			setPlayedMatches(new ArrayList<>());
		}
		getPlayedMatches().add(playedMatch);
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Collection<MatchResult> getPlayedMatches() {
		return playedMatches;
	}

	public void setPlayedMatches(Collection<MatchResult> playedMatches) {
		this.playedMatches = playedMatches;
	}

	public BigDecimal getGameWinPercentage() {
		return gameWinPercentage;
	}

	public void setGameWinPercentage(BigDecimal gameWinPercentage) {
		this.gameWinPercentage = gameWinPercentage;
	}

	public BigDecimal getMatchWinPercentage() {
		return matchWinPercentage;
	}

	public void setMatchWinPercentage(BigDecimal matchWinPercentage) {
		this.matchWinPercentage = matchWinPercentage;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public BigDecimal getOpponentGameWinPercentage() {
		return opponentGameWinPercentage;
	}

	public void setOpponentGameWinPercentage(BigDecimal opponentGameWinPercentage) {
		this.opponentGameWinPercentage = opponentGameWinPercentage;
	}

	public BigDecimal getOpponentMatchWinPercentage() {
		return opponentMatchWinPercentage;
	}

	public void setOpponentMatchWinPercentage(BigDecimal opponentMatchWinPercentage) {
		this.opponentMatchWinPercentage = opponentMatchWinPercentage;
	}

	public String toString() {
		return player + ": " + score + " points; OpScore: "
				+ opponentMatchWinPercentage.setScale(4, RoundingMode.HALF_UP).toPlainString() + "; GameScore: "
				+ gameWinPercentage.setScale(4, RoundingMode.HALF_UP).toPlainString() + "; OpGameScore: "
				+ opponentGameWinPercentage.setScale(4, RoundingMode.HALF_UP).toPlainString();
	}

	public Boolean getDropped() {
		return dropped;
	}

	public void setDropped(Boolean dropped) {
		this.dropped = dropped;
	}

}
