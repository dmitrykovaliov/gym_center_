package com.dk.gym.dao.impl;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.ActivityDao;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivityDaoImpl extends ActivityDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_ACTIVITY = "INSERT INTO activity(id_activity, act_name, act_price, act_note) " +
            "VALUES (NULL, ?, ?, ?)";
    private static final String SQL_UPDATE_ACTIVITY = "UPDATE activity SET act_name = ?, act_price = ?, act_note = ?" +
            " WHERE id_activity = ?";

    private static final String SQL_SELECT_ALL_ACTIVITIES = "SELECT id_activity, act_name, act_price, act_note FROM activity";
    private static final String SQL_SELECT_ACTIVITY_BY_ID = "SELECT id_activity, act_name, act_price, act_note " +
            "FROM activity where id_activity=?";

    private static final String SQL_DELETE_ACTIVITY = "DELETE FROM activity WHERE id_activity = ?";


    public ActivityDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }


    @Override
    public int create(Activity entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setString(3, entity.getNote());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Can't create activity ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Activity entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ACTIVITY)) {
            statement.setString(1, entity.getName());
            statement.setBigDecimal(2, entity.getPrice());
            statement.setString(3, entity.getNote());
            statement.setInt(4, entity.getIdActivity());

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new DaoException("Can't update activity", e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ACTIVITY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Activity> findAll() throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTIVITIES)) {

                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt("id_activity"));
                    activity.setName(resultSet.getString("act_name"));
                    activity.setPrice(resultSet.getBigDecimal("act_price"));
                    activity.setNote(resultSet.getString("act_note"));

                    list.add(activity);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Activity findEntityById(int id) throws DaoException {

        Activity activity = new Activity();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTIVITY_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                }

                activity.setIdActivity(resultSet.getInt("id_activity"));
                activity.setName(resultSet.getString("act_name"));
                activity.setPrice(resultSet.getBigDecimal("act_price"));
                activity.setNote(resultSet.getString("act_note"));

            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return activity;
    }

    public static void main(String[] args) throws DaoException {
        ActivityDao activityDao = new ActivityDaoImpl();
        List<Activity> entities = activityDao.findAll();

        Activity entity = activityDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

       LOGGER.log(Level.INFO, activityDao.create(entity));

       entity.setNote("this is the text");
        LOGGER.log(Level.INFO, entity);


       LOGGER.log(Level.INFO, activityDao.update(entity));

    }
}
