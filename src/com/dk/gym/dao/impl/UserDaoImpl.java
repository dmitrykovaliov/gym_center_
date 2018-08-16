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
import static com.dk.gym.dao.DatabaseConstant.*;

/**
 * The Class UserDaoImpl. Implementation of user dao.
 */
public class UserDaoImpl extends UserDao {

    /** The Constant SQL_INSERT_USER. */
    private static final String SQL_INSERT_USER = "INSERT INTO user(id_user, us_login, us_password, us_role) " +
            "VALUES (NULL, ?, ?, ?)";
    
    /** The Constant SQL_UPDATE_USER. */
    private static final String SQL_UPDATE_USER = "UPDATE user SET us_login = ?, us_password = ?, us_role = ?" +
            "WHERE id_user = ?";
    
    /** The Constant SQL_SELECT_ALL_USER. */
    private static final String SQL_SELECT_ALL_USER = "SELECT user.id_user, us_login, us_password, us_role, " +
            "id_client, cl_name, cl_lastname, id_trainer, tr_name, tr_lastname " +
            "FROM user " +
            "LEFT JOIN client c ON user.id_user = c.id_user " +
            "LEFT JOIN trainer t ON user.id_user = t.id_user " +
            "ORDER BY user.id_user ASC";
    
    /** The Constant SQL_SELECT_USER_BY_ID. */
    private static final String SQL_SELECT_USER_BY_ID = "SELECT id_user, us_login, us_password, us_role" +
            " FROM user WHERE id_user = ?";

    /** The Constant SQL_DELETE_USER. */
    private static final String SQL_DELETE_USER = "DELETE FROM user WHERE id_user = ?";
    
    /** The Constant SQL_LOGIN. */
    private static final String SQL_LOGIN = "SELECT count(id_user) count FROM user WHERE us_login = ?";
    
    /** The Constant SQL_LOGIN_PASS. */
    private static final String SQL_LOGIN_PASS = "SELECT id_user, us_role FROM user WHERE us_login = ? AND us_password = ?";

    /**
     * Instantiates a new user dao impl.
     */
    public UserDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#create(com.dk.gym.entity.Entity)
     */
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
        return RETURNED_NEGATIVE_RESULT;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#update(com.dk.gym.entity.Entity)
     */
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#findAll()
     */
    @Override
    public List<User> findAll() throws DaoException {

        List<User> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER)) {

                    while (resultSet.next()) {

                        User user = new User();

                        user.setIdUser(resultSet.getInt(ID_USER));
                        user.setLogin(resultSet.getString(US_LOGIN));
                        user.setPass(resultSet.getString(US_PASS));

                        String role = resultSet.getString(US_ROLE);
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#findRelatedAllClient()
     */
    @Override
    public List<Client> findRelatedAllClient() throws DaoException {
        List<Client> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER)) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt(ID_CLIENT));
                    client.setName(resultSet.getString(CL_NAME));
                    client.setLastName(resultSet.getString(CL_LASTNAME));
                    client.setIdUser(resultSet.getInt(ID_USER));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allClient: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#findRelatedAllTrainer()
     */
    @Override
    public List<Trainer> findRelatedAllTrainer() throws DaoException {
        List<Trainer> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USER)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt(ID_TRAINER));
                    trainer.setName(resultSet.getString(TR_NAME));
                    trainer.setLastName(resultSet.getString(TR_LASTNAME));
                    trainer.setIdUser(resultSet.getInt(ID_USER));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allTrainer", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#findById(int)
     */
    @Override
    public User findById(int id) throws DaoException {

        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_USER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    user.setIdUser(resultSet.getInt(ID_USER));
                    user.setLogin(resultSet.getString(US_LOGIN));
                    user.setPass(resultSet.getString(US_PASS));
                    String role = resultSet.getString(US_ROLE);
                    if (role != null && !role.isEmpty()) {
                        user.setRole(Role.valueOf(role));
                    }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found entityById: ", e);
        }

        return user;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#delete(int)
     */
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#findLogin(java.lang.String)
     */
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.UserDao#findUser(java.lang.String, java.lang.String)
     */
    @Override
    public User findUser(String login, String pass) throws DaoException {
        User user = new User();

        try (PreparedStatement statement = connection.prepareStatement(SQL_LOGIN_PASS)) {
            statement.setString(1, login);
            statement.setString(2, pass);


            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                    user.setIdUser(resultSet.getInt(ID_USER));
                    String role = resultSet.getString(US_ROLE);
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
