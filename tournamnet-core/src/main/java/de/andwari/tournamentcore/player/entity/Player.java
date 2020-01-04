package de.andwari.tournamentcore.player.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;

@DatabaseTable(tableName = "player")
public class Player extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -6831739212400179940L;

	public final static String FIELD_PLAYER_NAME = "PLAYERNAME";
	public final static String FIELD_DCI = "DCI";
	public final static String FIELD_MEMBER = "MEMBER";

	public Player() {

	}

	public Player(String name) {
		this.playerName = name;
	}

	@DatabaseField(unique = true, canBeNull = false, columnName = FIELD_PLAYER_NAME)
	private String playerName;

	@DatabaseField(unique = true, columnName = FIELD_DCI, canBeNull = true)
	private String dci;

	@DatabaseField(columnName = FIELD_MEMBER)
	private Boolean member;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getDci() {
		return dci;
	}

	public void setDci(String dci) {
		this.dci = dci;
	}

	public String toString() {
		return playerName;
	}

	public Boolean getMember() {
		return member;
	}

	public void setMember(Boolean member) {
		this.member = member;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (this.id != other.id)
			return false;
		return true;
	}

}
