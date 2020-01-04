package de.andwari.tournamentcore.player.repos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import de.andwari.tournamentcore.database.BaseRepository;
import de.andwari.tournamentcore.database.DatabaseManager;
import de.andwari.tournamentcore.player.entity.Player;

public class PlayerRepository extends BaseRepository<Player> {

	public PlayerRepository() {
		super();
	}

	public Player findByName(String name) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<Player, Long> dao = DaoManager.createDao(con, Player.class);

			QueryBuilder<Player, Long> queryBuilder = dao.queryBuilder();
			Where<Player, Long> where = queryBuilder.where();
			SelectArg selectArg = new SelectArg();
			where.eq(Player.FIELD_PLAYER_NAME, selectArg);
			PreparedQuery<Player> preparedQuery = queryBuilder.prepare();
			selectArg.setValue(name);

			Player player = dao.queryForFirst(preparedQuery);
			con.close();

			return player;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Player> findAllPlayersWithMembership() {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<Player, Long> dao = DaoManager.createDao(con, Player.class);

			QueryBuilder<Player, Long> queryBuilder = dao.queryBuilder();
			Where<Player, Long> where = queryBuilder.where();
			where.eq(Player.FIELD_MEMBER, true);
			queryBuilder.orderBy(Player.FIELD_PLAYER_NAME, true);
			PreparedQuery<Player> preparedQuery = queryBuilder.prepare();

			List<Player> list = dao.query(preparedQuery);
			con.close();

			return list;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
