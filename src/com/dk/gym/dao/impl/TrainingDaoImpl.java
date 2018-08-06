package com.dk.gym.dao.impl;

import com.dk.gym.dao.TrainingDao;
import com.dk.gym.entity.Trainer;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainingDaoImpl extends TrainingDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_TRAINING = "INSERT INTO training(id_training, trg_date, trg_start_time, trg_end_time, trg_visited," +
            "trg_client_note, trg_trainer_note, id_order, id_trainer) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TRAINING = "UPDATE training SET trg_date = ?, trg_start_time = ?, trg_end_time = ?, " +
            "trg_visited = ?, trg_client_note = ?, trg_trainer_note = ?, id_order = ?, id_trainer = ? WHERE id_training = ?";

    private static final String SQL_SELECT_ALL_TRAININGS = "SELECT id_training, trg_date, trg_start_time, " +
            "trg_end_time, trg_visited, trg_client_note, trg_trainer_note, id_order, tr.id_trainer, tr_name, tr_lastname  " +
            "FROM training " +
            "LEFT JOIN trainer tr ON training.id_training = tr.id_trainer;";

    private static final String SQL_SELECT_TRAINING_BY_ID = "SELECT id_training, trg_date, trg_start_time, trg_end_time, trg_visited, trg_client_note," +
            "trg_trainer_note, id_order, id_trainer  FROM training where id_training=?";
    private static final String SQL_DELETE_TRAINING = "DELETE FROM training WHERE id_training = ?";


    public TrainingDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Training entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TRAINING, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()):null);
            statement.setTime(2, entity.getStartTime() != null ?Time.valueOf(entity.getStartTime()):null);
            statement.setTime(3, entity.getEndTime() != null ?Time.valueOf(entity.getEndTime()):null);
            statement.setInt(4, entity.isVisited()?1:0);
            statement.setString(5, entity.getClientNote());
            statement.setString(6, entity.getTrainerNote());
            statement.setInt(7, entity.getIdOrder());
            statement.setObject(8, entity.getIdTrainer());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't create training ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Training entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRAINING)) {

            statement.setDate(1, Date.valueOf(entity.getDate()));
            statement.setTime(2, Time.valueOf(entity.getStartTime()));
            statement.setTime(3, Time.valueOf(entity.getEndTime()));
            statement.setInt(4, entity.isVisited()?1:0);
            statement.setString(5, entity.getClientNote());
            statement.setString(6, entity.getTrainerNote());
            statement.setInt(7, entity.getIdOrder());
            statement.setInt(8, entity.getIdTrainer());
            statement.setInt(9, entity.getIdTraining());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't update training", e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_TRAINING)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Training> findAll() throws DaoException {
        List<Training> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TRAININGS)) {
                while (resultSet.next()) {

                    Training training = new Training();

                    training.setIdTraining(resultSet.getInt("id_training"));
                    training.setDate(resultSet.getDate("trg_date") != null ?resultSet.getDate("trg_date").toLocalDate():null);
                    training.setStartTime(resultSet.getTime("trg_start_time")!= null?resultSet.getTime("trg_start_time").toLocalTime():null);
                    training.setEndTime(resultSet.getTime("trg_end_time")!= null?resultSet.getTime("trg_end_time").toLocalTime():null);
                    training.setVisited(resultSet.getInt("trg_visited") == 1);
                    training.setClientNote(resultSet.getString("trg_client_note"));
                    training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                    training.setIdOrder(resultSet.getInt("id_order"));
                    training.setIdTrainer(resultSet.getInt("id_trainer"));

                    list.add(training);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Set<Trainer> findAllTrainer() throws DaoException {
        Set<Trainer> list = new HashSet<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_TRAININGS)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find trainerAll", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Training findEntityById(int id) throws DaoException {

        Training training = new Training();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINING_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {

                training.setIdTraining(resultSet.getInt("id_training"));
                training.setDate(resultSet.getDate("trg_date").toLocalDate());
                training.setStartTime(resultSet.getTime("trg_start_time").toLocalTime());
                training.setEndTime(resultSet.getTime("trg_end_time").toLocalTime());
                training.setVisited(resultSet.getInt("trg_visited") == 1);
                training.setClientNote(resultSet.getString("trg_client_note"));
                training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                training.setIdOrder(resultSet.getInt("id_order"));
                training.setIdTrainer(resultSet.getInt("id_trainer"));
                    resultSet.next();
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return training;
    }

    public static void main(String[] args) throws DaoException {
        TrainingDao trainingDao = new TrainingDaoImpl();
        List<Training> entities = trainingDao.findAll();

        Training entity = trainingDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

       LOGGER.log(Level.INFO, trainingDao.create(entity));

       entity.setClientNote("this the text");
        LOGGER.log(Level.INFO, entity);


       LOGGER.log(Level.INFO, trainingDao.update(entity));

    }
}
