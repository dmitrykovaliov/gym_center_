package com.dk.gym.dao.impl;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.ActivityDao;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.dk.gym.dao.DatabaseConstant.*;

public class ActivityDaoImpl extends ActivityDao {

    private static final String SQL_INSERT_ACTIVITY = "INSERT INTO activity(id_activity, act_name, act_price, " +
            "act_note) " +
            "VALUES (NULL, ?, ?, ?)";
    private static final String SQL_UPDATE_ACTIVITY = "UPDATE activity SET act_name = ?, act_price = ?, act_note = ?" +
            " WHERE id_activity = ?";

    private static final String SQL_SELECT_ALL_ACTIVITY = "SELECT id_activity, act_name, act_price, act_note " +
            "FROM activity";
    private static final String SQL_SELECT_ACTIVITY_BY_ID = "SELECT id_activity, act_name, act_price, act_note " +
            "FROM activity WHERE id_activity=?";

    private static final String SQL_DELETE_ACTIVITY = "DELETE FROM activity WHERE id_activity = ?";


    public ActivityDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }


    @Override
    public int create(Activity entity) throws DaoException {

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)) {

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
            throw new DaoException("Not created: ", e);
        }

        return RETURNED_NEGATIVE_RESULT;
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
            throw new DaoException("Not updated: ", e);
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
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTIVITY)) {
                while (resultSet.next()) {
                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt(ID_ACTIVITY));
                    activity.setName(resultSet.getString(ACT_NAME));
                    activity.setPrice(resultSet.getBigDecimal(ACT_PRICE));
                    activity.setNote(resultSet.getString(ACT_NOTE));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found all: ", e);
        }

        return list;
    }

    @Override
    public Activity findById(int id) throws DaoException {

        Activity activity = new Activity();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ACTIVITY_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                activity.setIdActivity(resultSet.getInt(ID_ACTIVITY));
                activity.setName(resultSet.getString(ACT_NAME));
                activity.setPrice(resultSet.getBigDecimal(ACT_PRICE));
                activity.setNote(resultSet.getString(ACT_NOTE));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found by id: ", e);
        }
        return activity;
    }
}
