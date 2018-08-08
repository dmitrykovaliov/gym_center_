package com.dk.gym.dao.impl;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.ClientDao;
import com.dk.gym.entity.Client;
import com.dk.gym.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl extends ClientDao {

    private static final String SQL_INSERT_CLIENT = "INSERT INTO client(id_client, cl_name, cl_lastname, cl_phone, " +
            "cl_email, cl_personal_data, cl_iconpath) " +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_CLIENT = "UPDATE client SET cl_name = ?, cl_lastname = ?, cl_phone = ?, " +
            "cl_email = ?, cl_personal_data = ?, cl_iconpath = ? " +
            "WHERE id_client = ?";
    private static final String SQL_UPDATE_USERID_CLIENT = "UPDATE client SET id_user = ? " +
            "WHERE id_client = ?";
    private static final String SQL_SELECT_ALL_CLIENTS = "SELECT id_client, cl_name, cl_lastname, cl_phone," +
            "cl_email, cl_personal_data, cl_iconpath FROM client";
    private static final String SQL_SELECT_CLIENT_BY_ID = "SELECT id_client, cl_name, cl_lastname, cl_phone," +
            "cl_email, cl_personal_data, cl_iconpath FROM client WHERE id_client = ?";
    private static final String SQL_SELECT_CLIENT_BY_USERID = "SELECT id_client, cl_name, cl_lastname, cl_phone," +
            "cl_email, cl_personal_data, cl_iconpath FROM client WHERE id_user = ?";

    private static final String SQL_SELECT_CLIENT_BY_TRAINER = "SELECT client.id_client, cl_name, cl_lastname, " +
            "cl_phone, cl_email, cl_personal_data, cl_iconpath " +
            "FROM client " +
            "JOIN order_ o ON client.id_client = o.id_client " +
            "JOIN prescription p ON o.id_order = p.id_order " +
            "JOIN trainer t ON p.id_trainer = t.id_trainer " +
            "JOIN user u ON t.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "GROUP BY client.id_client " +
            "UNION " +
            "SELECT client.id_client, cl_name, cl_lastname, cl_phone, cl_email, cl_personal_data, cl_iconpath " +
            "FROM client " +
            "JOIN order_ o ON client.id_client = o.id_client " +
            "JOIN training t ON o.id_order = t.id_order " +
            "JOIN trainer t2 ON t.id_trainer = t2.id_trainer " +
            "JOIN user u ON t2.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "GROUP BY client.id_client " +
            "ORDER BY cl_name ASC";
    private static final String SQL_DELETE_CLIENT = "DELETE FROM client WHERE id_client = ?";

    public ClientDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Client entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CLIENT,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPersonalData());
            statement.setString(6, entity.getIconPath());

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
    public boolean update(Client entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getPersonalData());
            statement.setString(6, entity.getIconPath());
            statement.setInt(7, entity.getIdClient());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
        }
    }

    @Override
    public boolean updateUserId(Integer idUser, int idClient) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USERID_CLIENT)) {

            statement.setObject(1, idUser);
            statement.setInt(2, idClient);

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update userId: ", e);
        }
    }

    @Override
    public List<Client> findAll() throws DaoException {
        List<Client> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_CLIENTS)) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt("id_client"));
                    client.setName(resultSet.getString("cl_name"));
                    client.setLastName(resultSet.getString("cl_lastname"));
                    client.setPhone(resultSet.getString("cl_phone"));
                    client.setEmail(resultSet.getString("cl_email"));
                    client.setPersonalData(resultSet.getString("cl_personal_data"));
                    client.setIconPath(resultSet.getString("cl_iconpath"));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found: ", e);
        }
        return list;
    }

    @Override
    public List<Client> findTrainerAll(int idUser) throws DaoException {
        List<Client> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet != null) {
                    while (resultSet.next()) {

                        Client client = new Client();

                        client.setIdClient(resultSet.getInt("id_client"));
                        client.setName(resultSet.getString("cl_name"));
                        client.setLastName(resultSet.getString("cl_lastname"));
                        client.setPhone(resultSet.getString("cl_phone"));
                        client.setEmail(resultSet.getString("cl_email"));
                        client.setPersonalData(resultSet.getString("cl_personal_data"));
                        client.setIconPath(resultSet.getString("cl_iconpath"));

                        list.add(client);
                    }
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Not found: ", e);
        }
        return list;
    }

    @Override
    public Client findEntityById(int id) throws DaoException {

        Client client = new Client();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                client.setIdClient(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("cl_name"));
                client.setLastName(resultSet.getString("cl_lastname"));
                client.setPhone(resultSet.getString("cl_phone"));
                client.setEmail(resultSet.getString("cl_email"));
                client.setPersonalData(resultSet.getString("cl_personal_data"));
                client.setIconPath(resultSet.getString("cl_iconpath"));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found: ", e);
        }
        return client;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_CLIENT)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Client findUser(int idUser) throws DaoException {
        Client client = new Client();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_USERID)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                client.setIdClient(resultSet.getInt("id_client"));
                client.setName(resultSet.getString("cl_name"));
                client.setLastName(resultSet.getString("cl_lastname"));
                client.setPhone(resultSet.getString("cl_phone"));
                client.setEmail(resultSet.getString("cl_email"));
                client.setPersonalData(resultSet.getString("cl_personal_data"));
                client.setIconPath(resultSet.getString("cl_iconpath"));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found entityByUserId: ", e);
        }
        return client;
    }
}
