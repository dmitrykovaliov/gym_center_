package com.dk.gym.dao.impl;

import com.dk.gym.dao.UserDao;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.User;
import com.dk.gym.entity.join.JoinUser;
import com.dk.gym.exception.DaoException;
import com.dk.gym.pool.ConnectionPool;
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

    private static final String SQL_INSERT_USER = "INSERT INTO user(id_user, us_login, us_password, us_role) " +
            "VALUES (NULL, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET us_login = ?, us_password = ?, us_role = ?" +
            "WHERE id_user = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT id_user, us_login, us_password, us_role FROM user";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user, us_login, us_password, us_role" +
            " FROM user WHERE id_user = ?";
    private static final String SQL_JOIN_ALL_USERS = "SELECT user.id_user, us_login, us_password, us_role, " +
            "id_client, cl_name, cl_lastname, id_trainer, tr_name, tr_lastname " +
            "FROM user " +
            "LEFT JOIN client c ON user.id_user = c.id_user " +
            "LEFT JOIN trainer t ON user.id_user = t.id_user " +
            "ORDER BY user.id_user ASC;";
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id_user = ?";
    private static final String SQL_LOGIN = "SELECT count(id_user) count FROM user WHERE us_login = ?";
    private static final String SQL_LOGIN_PASS = "SELECT id_user, us_role FROM user WHERE us_login = ? AND us_password = ?";

    public UserDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(User entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPass());
            statement.setString(3, entity.getRole() != null ? entity.getRole().toString() : "");

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not created: ", e);
        }

        return -1;
    }

    @Override
    public boolean update(User entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER)) {

            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPass());
            statement.setString(3, entity.getRole() != null ? entity.getRole().toString() : "");
            statement.setInt(4, entity.getIdUser());

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
                    user.setPass(resultSet.getString("us_password"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }

                    list.add(user);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public List<JoinUser> findJoinAll() throws DaoException {
        List<JoinUser> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_JOIN_ALL_USERS)) {

                while (resultSet.next()) {

                    JoinUser joinUser = new JoinUser();

                    joinUser.getUser().setIdUser(resultSet.getInt("id_user"));
                    joinUser.getUser().setLogin(resultSet.getString("us_login"));
                    joinUser.getUser().setPass(resultSet.getString("us_password"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        joinUser.getUser().setRole(Role.valueOf(role));
                    }
                    joinUser.getClient().setIdClient(resultSet.getInt("id_client"));
                    joinUser.getClient().setName(resultSet.getString("cl_name"));
                    joinUser.getClient().setLastName(resultSet.getString("cl_lastname"));
                    joinUser.getTrainer().setIdTrainer(resultSet.getInt("id_trainer"));
                    joinUser.getTrainer().setName(resultSet.getString("tr_name"));
                    joinUser.getTrainer().setLastName(resultSet.getString("tr_lastname"));

                    list.add(joinUser);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't findJoinAll", e);
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

                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setLogin(resultSet.getString("us_login"));
                    user.setPass(resultSet.getString("us_password"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found: ", e);
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
    public boolean findLogin(String login) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_LOGIN)) {
            statement.setString(1, login);

            int count = 0;

            try (ResultSet resultSet = statement.executeQuery()) {
                if(resultSet != null) {
                    resultSet.next();

                    count = resultSet.getInt("count");
                }
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("Not foundLogin: ", e);
        }
    }

    @Override
    public User findUser(String login, String pass) throws DaoException {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_LOGIN_PASS)) {
            statement.setString(1, login);
            statement.setString(2, pass);


            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();

                    user.setIdUser(resultSet.getInt("id_user"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not checked: ", e);
        }

        return user;
    }

    public static void main(String[] args) throws DaoException {
        UserDao userDao = new UserDaoImpl();
        List<User> entities = userDao.findAll();

        User entity = userDao.findEntityById(1);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

        LOGGER.log(Level.INFO, userDao.findUser("root", "PWbzEwjHFZuUqD0agfGETkV7eXKS+mt5GAmYE7nvQeE="));
        LOGGER.log(Level.INFO, "+++" + userDao.findLogin("rot"));

    }
}
