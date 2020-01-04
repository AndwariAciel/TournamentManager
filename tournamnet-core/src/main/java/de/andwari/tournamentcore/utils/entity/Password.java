package de.andwari.tournamentcore.utils.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.andwari.tournamentcore.database.BaseEntity;

@DatabaseTable(tableName = "password")
public class Password extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 6253907287499877713L;

	public final static String FIELD_USER = "USER";
	public final static String FIELD_HASH = "HASH";
	public final static String FIELD_SALT = "SALT";

	@DatabaseField(unique = true, canBeNull = false, columnName = FIELD_USER)
	private String user;

	@DatabaseField(canBeNull = false, columnName = FIELD_HASH)
	private String hash;

	@DatabaseField(canBeNull = false, columnName = FIELD_SALT)
	private String salt;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
