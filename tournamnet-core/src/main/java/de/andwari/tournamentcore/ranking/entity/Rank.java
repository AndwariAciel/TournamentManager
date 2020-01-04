package de.andwari.tournamentcore.ranking.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;
import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.player.entity.Player;

@DatabaseTable(tableName = "rank")
public class Rank extends BaseEntity {

	public static final String PLAYER = "player";
	public static final String YEAR = "year";
	public static final String MONTH = "month";
	public static final String POINTS = "points";
	public static final String EVENT = "event";
	public static final String COMMENT = "comment";

	@DatabaseField(foreign = true, columnName = PLAYER)
	private Player player;

	@DatabaseField(columnName = YEAR)
	private int year;

	@DatabaseField(columnName = MONTH)
	private int month;

	@DatabaseField(columnName = POINTS)
	private int points;

	@DatabaseField(foreign = true, columnName = EVENT)
	private Event event;

	@DatabaseField(columnName = COMMENT)
	private String comment;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
