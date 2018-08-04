package com.dk.gym.dao.impl;

import com.dk.gym.dao.TrainerDao;
import com.dk.gym.entity.Trainer;
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

public class TrainerDaoImpl extends TrainerDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_CLIENT = "INSERT INTO trainer(id_trainer, tr_name, tr_lastname, tr_phone, " +
            "tr_personal_data, tr_iconpath) " +
            "VALUES (NULL, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_CLIENT = "UPDATE trainer SET tr_name = ?, tr_lastname = ?, tr_phone = ?, " +
            "tr_personal_data = ?, tr_iconpath = ? " +
            "WHERE id_trainer = ?";
    private static final String SQL_SELECT_ALL_ACTIVITIES = "SELECT id_trainer, tr_name, tr_lastname, tr_phone," +
            "tr_personal_data, tr_iconpath FROM trainer";
    private static final String SQL_SELECT_CLIENT_BY_ID = "SELECT id_trainer, tr_name, tr_lastname, tr_phone," +
            "tr_personal_data, tr_iconpath FROM trainer WHERE id_trainer = ?";

    private static final String SQL_DELETE_CLIENT = "DELETE FROM trainer WHERE id_trainer = ?";

    public TrainerDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Trainer entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CLIENT,
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getPersonalData());
            statement.setString(5, entity.getIconPath());

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
    public boolean update(Trainer entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CLIENT)) {

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPhone());
            statement.setString(4, entity.getPersonalData());
            statement.setString(5, entity.getIconPath());
            statement.setInt(6, entity.getIdTrainer());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update trainer", e);
        }
    }

    @Override
    public List<Trainer> findAll() throws DaoException {

        List<Trainer> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ACTIVITIES)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));
                    trainer.setPhone(resultSet.getString("tr_phone"));
                    trainer.setPersonalData(resultSet.getString("tr_personal_data"));
                    trainer.setIconPath(resultSet.getString("tr_iconpath"));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }


    @Override
    public Trainer findEntityById(int id) throws DaoException {

        Trainer trainer = new Trainer();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));
                    trainer.setPhone(resultSet.getString("tr_phone"));
                    trainer.setPersonalData(resultSet.getString("tr_personal_data"));
                    trainer.setIconPath(resultSet.getString("tr_iconpath"));
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found: ", e);
        }

        return trainer;
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

    public static void main(String[] args) throws DaoException {
        TrainerDao trainerDao = new TrainerDaoImpl();
        List<Trainer> entities = trainerDao.findAll();

        Trainer entity = trainerDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

        LOGGER.log(Level.INFO, trainerDao.create(entity));

        LOGGER.log(Level.INFO, trainerDao.update(entity));
    }
}
