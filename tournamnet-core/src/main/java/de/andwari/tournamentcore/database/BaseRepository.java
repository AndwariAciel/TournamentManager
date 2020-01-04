package de.andwari.tournamentcore.database;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class BaseRepository<T> {

	private Class<T> genericClass;
	
	@SuppressWarnings("unchecked")
	public BaseRepository() {
		this.genericClass = ((Class<T>) ((ParameterizedType) getClass()
		        .getGenericSuperclass()).getActualTypeArguments()[0]);
	}
	
	public void create(T entity) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<T, Long> dao = DaoManager.createDao(con, genericClass);
			dao.create(entity);
			con.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(T entity) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<T, Long> dao = DaoManager.createDao(con, genericClass);
			dao.update(entity);
			con.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public T findById(Long id) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<T, Long> dao = DaoManager.createDao(con, genericClass);
			T entity = dao.queryForId(id);
			con.close();
			return entity;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public List<T> findAll() {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<T, Long> dao = DaoManager.createDao(con, genericClass);
			List<T> listOfEntities = dao.queryForAll();
			con.close();
			return listOfEntities;
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(long id) {
		try {
			ConnectionSource con = DatabaseManager.getInstance().getConnection();
			Dao<T, Long> dao = DaoManager.createDao(con, genericClass);
			dao.deleteById(id);
			con.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
