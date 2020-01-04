package de.andwari.tournamentcore.utils.repos;

import java.io.IOException;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import de.andwari.tournamentcore.database.BaseRepository;
import de.andwari.tournamentcore.database.DatabaseManager;
import de.andwari.tournamentcore.utils.entity.Password;

public class PasswordRepository extends BaseRepository<Password> {

	public PasswordRepository() {
		super();
	}

	public Password findByUser(String user) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<Password, Long> dao = DaoManager.createDao(con, Password.class);

			QueryBuilder<Password, Long> queryBuilder = dao.queryBuilder();
			Where<Password, Long> where = queryBuilder.where();
			SelectArg selectArg = new SelectArg();
			where.eq(Password.FIELD_USER, selectArg);
			PreparedQuery<Password> preparedQuery = queryBuilder.prepare();
			selectArg.setValue(user);

			Password password = dao.queryForFirst(preparedQuery);
			con.close();

			return password;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
