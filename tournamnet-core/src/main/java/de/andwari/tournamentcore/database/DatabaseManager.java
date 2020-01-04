package de.andwari.tournamentcore.database;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.h2.message.DbException;
import org.h2.tools.Backup;
import org.h2.tools.DeleteDbFiles;
import org.h2.tools.Restore;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.event.entity.Match;
import de.andwari.tournamentcore.event.entity.MatchResult;
import de.andwari.tournamentcore.event.entity.Round;
import de.andwari.tournamentcore.event.entity.Standing;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.ranking.entity.Rank;
import de.andwari.tournamentcore.utils.entity.Password;

public class DatabaseManager {

	private static final String DB_FOLDER = "~/atmdb1";
	private static final String DB_NAME = "tournamentmanager_db";
	private static final String DB_CONNECTION = "jdbc:h2:";
//	private static final String DB_CONNECTION_TEST = "jdbc:h2:~/" + DB_FOLDER + "/test";

	private static Logger logger = Logger.getLogger(DatabaseManager.class);

	private static DatabaseManager databaseManager;

	public static DatabaseManager getInstance() {
		if (databaseManager == null) {
			databaseManager = new DatabaseManager();
		}
		return databaseManager;
	}

	public ConnectionSource getConnection() {
		try {
			ConnectionSource connection = new JdbcConnectionSource(getDbSource());
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getDbSource() {
		return DB_CONNECTION + DB_FOLDER + "/" + DB_NAME;
	}

	public static void closeConnection(ConnectionSource connection) {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void purgeDatabase() {
		logger.info("Deleting Database");
		DeleteDbFiles.execute(DB_FOLDER, DB_NAME, true);
		init();
	}

	public static void init() {

		for (Class<? extends BaseEntity> clazz : getEntityClasses()) {
			try {
				ConnectionSource con = DatabaseManager.getInstance().getConnection();
				TableUtils.createTableIfNotExists(con, clazz);
				closeConnection(con);
			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

	}

	public static boolean backup(File path) {
		try {
			logger.info("Backup Data to: " + path.getAbsolutePath());
			Backup.execute(path.getAbsolutePath(), DB_FOLDER, DB_NAME, false);
			return true;
		} catch (SQLException e) {
			logger.error("Backup failed", e);
			return false;
		}
	}

	public static boolean restore(File selectedFile) {
		try {
			Restore.execute(selectedFile.getAbsolutePath(), DB_FOLDER, DB_NAME);
			return true;
		} catch (DbException e) {
			logger.error("Restore failed", e);
			return false;
		}
	}

	private static List<Class<? extends BaseEntity>> getEntityClasses() {
		ArrayList<Class<? extends BaseEntity>> listOfEntities = new ArrayList<>();
		listOfEntities.add(Player.class);
		listOfEntities.add(Event.class);
		listOfEntities.add(Match.class);
		listOfEntities.add(Standing.class);
		listOfEntities.add(Round.class);
		listOfEntities.add(MatchResult.class);
		listOfEntities.add(Rank.class);
		listOfEntities.add(Password.class);
		return listOfEntities;
	}

}
