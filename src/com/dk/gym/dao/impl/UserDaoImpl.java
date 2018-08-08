package com.dk.gym.dao.impl;

import com.dk.gym.dao.UserDao;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;
import com.dk.gym.pool.ConnectionPool;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO user(id_user, us_login, us_password, us_role) " +
            "VALUES (NULL, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE user SET us_login = ?, us_password = ?, us_role = ?" +
            "WHERE id_user = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT user.id_user, us_login, us_password, us_role, " +
            "id_client, cl_name, cl_lastname, id_trainer, tr_name, tr_lastname " +
            "FROM user " +
            "LEFT JOIN client c ON user.id_user = c.id_user " +
            "LEFT JOIN trainer t ON user.id_user = t.id_user " +
            "ORDER BY user.id_user ASC";
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user, us_login, us_password, us_role" +
            " FROM user WHERE id_user = ?";

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
            statement.setString(3, entity.getRole() != null ? entity.getRole().toString() : null);

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
            statement.setString(3, entity.getRole() != null ? entity.getRole().toString() : null);
            statement.setInt(4, entity.getIdUser());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
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
            throw new DaoException("Not found: ", e);
        }
        return list;
    }

    @Override
    public List<Client> findAllClient() throws DaoException {
        List<Client> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt("id_client"));
                    client.setName(resultSet.getString("cl_name"));
                    client.setLastName(resultSet.getString("cl_lastname"));
                    client.setIdUser(resultSet.getInt("id_user"));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allClient: ", e);
        }
        return list;
    }

    @Override
    public List<Trainer> findAllTrainer() throws DaoException {
        List<Trainer> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));
                    trainer.setIdUser(resultSet.getInt("id_user"));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allTrainer", e);
        }
        return list;
    }

    @Override
    public User findEntityById(int id) throws DaoException {

        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setLogin(resultSet.getString("us_login"));
                    user.setPass(resultSet.getString("us_password"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found entityById: ", e);
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

            int count;

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    count = resultSet.getInt("count");
            }
            return count > 0;
        } catch (SQLException e) {
            throw new DaoException("Not found login: ", e);
        }
    }

    @Override
    public User findUser(String login, String pass) throws DaoException {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_LOGIN_PASS)) {
            statement.setString(1, login);
            statement.setString(2, pass);


            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    user.setIdUser(resultSet.getInt("id_user"));
                    String role = resultSet.getString("us_role");
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found user: ", e);
        }
        return user;
    }
}
