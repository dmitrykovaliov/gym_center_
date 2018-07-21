package com.dk.gym.dao.impl;

import com.dk.gym.dao.UserDao;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends UserDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_USER = "INSERT INTO user(id_user, us_login, us_password, us_role, us_iconpath) VALUES (NULL, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET us_login = ?, us_password = ?, us_role = ?, us_iconpath = ? WHERE id_user = ?";

    private static final String SQL_SELECT_ALL_USERS = "SELECT id_user, us_login, us_password, us_role, us_iconpath FROM user";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user, us_login, us_password, us_role, us_iconpath FROM user WHERE id_user=?";

    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id_user = ?";


    public UserDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(User entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRole().toString());
            statement.setString(4, entity.getPicPath());
            statement.executeUpdate();
            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Can't create user ", e);
        }

        return -1;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {

            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRole().toString());
            statement.setString(4, entity.getPicPath());
            statement.setInt(5, entity.getIdUser());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DaoException("Can't update user", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {

                while (resultSet.next()) {

                    User user = new User();

                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setLogin(resultSet.getString("us_login"));
                    user.setPassword(resultSet.getString("us_password"));
                    user.setRole(Role.valueOf(resultSet.getString("us_role").toUpperCase()));
                    user.setPicPath(resultSet.getString("us_iconpath"));

                    list.add(user);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public User findEntityById(int id) throws DaoException {

        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                }
                user.setIdUser(resultSet.getInt("id_user"));
                user.setLogin(resultSet.getString("us_login"));
                user.setPassword(resultSet.getString("us_password"));
                user.setRole(Role.valueOf(resultSet.getString("us_role").toUpperCase()));
                user.setPicPath(resultSet.getString("us_iconpath"));

            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return user;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(User entity) throws DaoException {
        int id = entity.getIdUser();
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public static void main(String[] args) throws DaoException {
        UserDao userDao = new UserDaoImpl();
        List<User> entities = userDao.findAll();

        User entity = userDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

        //LOGGER.log(Level.INFO, userDao.create(entity));

//        entity.setPicPath("this the text");
//        LOGGER.log(Level.INFO, entity);
//
//        LOGGER.log(Level.INFO, userDao.update(entity));

    }
}
