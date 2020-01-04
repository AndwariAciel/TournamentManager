package de.andwari.tournamentcore.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

public abstract class BaseEntity {

	public BaseEntity() {
		
	}
	
	@DatabaseField(generatedId = true)
	protected long id;
	
	@DatabaseField(dataType = DataType.DATE)
	protected Date created = new Date();
	
	@DatabaseField(version = true, dataType = DataType.DATE)
	protected Date lastModified;


	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}


}
