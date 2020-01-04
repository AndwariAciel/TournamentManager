package de.andwari.tournamentcore.event.entity;

import java.util.Collection;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable
public class Round extends BaseEntity {
		
	@ForeignCollectionField(eager=true)
	private Collection<Match> matches;
	
	@DatabaseField(foreign=true)
	private Event event;
	
	@DatabaseField(foreign=true)
	private Player bye;

	@DatabaseField
	private Boolean finished;
	
	public Collection<Match> getMatches() {
		return matches;
	}

	public void setMatches(Collection<Match> matches) {
		this.matches = matches;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Player getBye() {
		return bye;
	}

	public void setBye(Player bye) {
		this.bye = bye;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}
	
}
