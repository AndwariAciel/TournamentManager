package de.andwari.tournamentcore.event.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable
public class Event extends BaseEntity {
	
	@ForeignCollectionField(eager=true)
	private Collection<Standing> rankings;
	
	@ForeignCollectionField(eager=true)
	private Collection<Round> rounds;
	
	@DatabaseField
	private int maxNumberOfRounds;
	
	@DatabaseField
	private String eventName;
	
	@DatabaseField
	private Boolean rankingPoints;
	
	private List<Player> seatings;
	
	private int currentRound;

	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public Collection<Standing> getRankings() {
		if(rankings == null) {
			rankings = new ArrayList<>();
		}
		return rankings;
	}

	public void setRankings(Collection<Standing> rankings) {
		this.rankings = rankings;
	}

	public List<Player> getSeatings() {
		return seatings;
	}

	public void setSeatings(List<Player> seatings) {
		this.seatings = seatings;
	}

	public Collection<Round> getRounds() {
		if(rounds == null) {
			rounds = new ArrayList<>();
		}
		return rounds;
	}

	public void setRounds(Collection<Round> rounds) {
		this.rounds = rounds;
	}

	public int getMaxNumberOfRounds() {
		return maxNumberOfRounds;
	}

	public void setMaxNumberOfRounds(int maxNumberOfRounds) {
		this.maxNumberOfRounds = maxNumberOfRounds;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Boolean getRankingPoints() {
		return rankingPoints;
	}

	public void setRankingPoints(Boolean rankingPoints) {
		this.rankingPoints = rankingPoints;
	}
	
	

}
