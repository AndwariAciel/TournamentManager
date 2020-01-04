package de.andwari.tournamentcore.ranking.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import de.andwari.tournamentcore.database.BaseRepository;
import de.andwari.tournamentcore.database.DatabaseManager;
import de.andwari.tournamentcore.event.entity.Event;
import de.andwari.tournamentcore.player.entity.Player;
import de.andwari.tournamentcore.ranking.entity.Rank;

public class RankRepository extends BaseRepository<Rank> {

	public Rank findByPlayerAndTime(Player p, int year, int month) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<Rank, Long> dao = DaoManager.createDao(con, Rank.class);
			QueryBuilder<Rank, Long> queryBuilder = dao.queryBuilder();
			PreparedQuery<Rank> query = queryBuilder.where().eq(Rank.PLAYER, p).and().eq(Rank.YEAR, year).and()
					.eq(Rank.MONTH, month).prepare();
			List<Rank> ranks = dao.query(query);
			con.close();
			if (ranks.size() == 1) {
				return ranks.get(0);
			}
			return null;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Rank> findAllForYear(int year) {
		try (ConnectionSource con = DatabaseManager.getInstance().getConnection()) {
			Dao<Rank, Long> dao = DaoManager.createDao(con, Rank.class);
			QueryBuilder<Rank, Long> queryBuilder = dao.queryBuilder();
			PreparedQuery<Rank> query = queryBuilder.where().eq(Rank.YEAR, year).prepare();
			List<Rank> ranks = dao.query(query);
			return ranks;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Rank> findAllForYearAndMonth(int year, int month) {
		try (ConnectionSource con = DatabaseManager.getInstance().getConnection()) {
			Dao<Rank, Long> dao = DaoManager.createDao(con, Rank.class);
			QueryBuilder<Rank, Long> queryBuilder = dao.queryBuilder();
			PreparedQuery<Rank> query = queryBuilder.where().eq(Rank.YEAR, year).and().eq(Rank.MONTH, month).prepare();
			List<Rank> ranks = dao.query(query);
			return ranks;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Rank> findYearsDistinct() {
		try (ConnectionSource con = DatabaseManager.getInstance().getConnection()) {
			Dao<Rank, Long> dao = DaoManager.createDao(con, Rank.class);
			QueryBuilder<Rank, Long> queryBuilder = dao.queryBuilder();
			PreparedQuery<Rank> query = queryBuilder.distinct().selectColumns(Rank.YEAR).orderBy(Rank.YEAR, false)
					.prepare();
			return dao.query(query);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void deleteForEvent(Event event) {
		try (ConnectionSource con = DatabaseManager.getInstance().getConnection()) {
			Dao<Rank, Long> dao = DaoManager.createDao(con, Rank.class);
			DeleteBuilder<Rank, Long> deleteBuilder = dao.deleteBuilder();
			deleteBuilder.where().eq(Rank.EVENT, event);
			deleteBuilder.delete();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
