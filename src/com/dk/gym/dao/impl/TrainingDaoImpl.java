package com.dk.gym.dao.impl;

import com.dk.gym.dao.TrainingDao;
import com.dk.gym.entity.Trainer;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingDaoImpl extends TrainingDao {

    private static final String SQL_INSERT_TRAINING = "INSERT INTO training(id_training, trg_date, trg_start_time, trg_end_time, trg_visited," +
            "trg_client_note, trg_trainer_note, id_order, id_trainer) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TRAINING = "UPDATE training SET trg_date = ?, trg_start_time = ?, trg_end_time = ?, " +
            "trg_visited = ?, trg_client_note = ?, trg_trainer_note = ?, id_order = ?, id_trainer = ? WHERE id_training = ?";
    private static final String SQL_SELECT_ALL_TRAININGS = "SELECT id_training, trg_date, trg_start_time, " +
            "trg_end_time, trg_visited, trg_client_note, trg_trainer_note, id_order, tr.id_trainer, tr_name, tr_lastname  " +
            "FROM training " +
            "LEFT JOIN trainer tr ON training.id_training = tr.id_trainer;";
    private static final String SQL_SELECT_TRAINER_TRAININGS= "SELECT id_training, trg_date, trg_start_time, " +
            "trg_end_time, trg_visited, trg_client_note, trg_trainer_note, id_order, t.id_trainer " +
            "FROM training " +
            "LEFT JOIN trainer t ON training.id_trainer = t.id_trainer " +
            "LEFT JOIN user u ON t.id_user = u.id_user " +
            "WHERE u.id_user=? " +
            "ORDER BY trg_date DESC, trg_start_time ASC";
    private static final String SQL_SELECT_CLIENT_TRAININGS = "SELECT id_training, trg_date, trg_start_time, " +
            "trg_end_time, trg_visited, trg_client_note, trg_trainer_note, training.id_order, tr.id_trainer, " +
            "tr_name, tr_lastname " +
            "FROM training " +
            "LEFT JOIN trainer tr ON training.id_training = tr.id_trainer " +
            "JOIN order_ o ON training.id_order = o.id_order " +
            "JOIN client c ON o.id_client = c.id_client " +
            "JOIN user u ON c.id_user = u.id_user " +
            "WHERE u.id_user=? " +
            "ORDER BY trg_date DESC, trg_start_time ASC";
    private static final String SQL_SELECT_TRAINING_BY_ID = "SELECT id_training, trg_date, trg_start_time, trg_end_time, trg_visited, trg_client_note," +
            "trg_trainer_note, id_order, id_trainer  FROM training WHERE id_training=?";
    private static final String SQL_DELETE_TRAINING = "DELETE FROM training WHERE id_training = ?";


    public TrainingDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Training entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_TRAINING, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
            statement.setTime(2, entity.getStartTime() != null ? Time.valueOf(entity.getStartTime()) : null);
            statement.setTime(3, entity.getEndTime() != null ? Time.valueOf(entity.getEndTime()) : null);
            statement.setInt(4, entity.isVisited() ? 1 : 0);
            statement.setString(5, entity.getClientNote());
            statement.setString(6, entity.getTrainerNote());
            statement.setInt(7, entity.getIdOrder());
            statement.setObject(8, entity.getIdTrainer()!=0 ? entity.getIdTrainer() : null);

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
    public boolean update(Training entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_TRAINING)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
            statement.setTime(2, entity.getStartTime() != null ? Time.valueOf(entity.getStartTime()) : null);
            statement.setTime(3, entity.getEndTime() != null ? Time.valueOf(entity.getEndTime()) : null);
            statement.setInt(4, entity.isVisited() ? 1 : 0);
            statement.setString(5, entity.getClientNote());
            statement.setString(6, entity.getTrainerNote());
            statement.setInt(7, entity.getIdOrder());
            statement.setObject(8, entity.getIdTrainer()!=0 ? entity.getIdTrainer() : null);
            statement.setInt(9, entity.getIdTraining());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
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
                    training.setDate(resultSet.getDate("trg_date") != null ? resultSet.getDate("trg_date").toLocalDate() : null);
                    training.setStartTime(resultSet.getTime("trg_start_time") != null ? resultSet.getTime("trg_start_time").toLocalTime() : null);
                    training.setEndTime(resultSet.getTime("trg_end_time") != null ? resultSet.getTime("trg_end_time").toLocalTime() : null);
                    training.setVisited(resultSet.getInt("trg_visited") == 1);
                    training.setClientNote(resultSet.getString("trg_client_note"));
                    training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                    training.setIdOrder(resultSet.getInt("id_order"));
                    training.setIdTrainer(resultSet.getInt("id_trainer"));

                    list.add(training);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find all: ", e);
        }
        return list;
    }

    @Override
    public List<Trainer> findAllTrainer() throws DaoException {
        List<Trainer> list = new ArrayList<>();

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
            throw new DaoException("Can't find all: ", e);
        }
        return list;
    }

    @Override
    public Training findEntityById(int id) throws DaoException {

        Training training = new Training();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINING_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                training.setIdTraining(resultSet.getInt("id_training"));
                training.setDate(resultSet.getDate("trg_date") != null ? resultSet.getDate("trg_date").toLocalDate() : null);
                training.setStartTime(resultSet.getTime("trg_start_time") != null ? resultSet.getTime("trg_start_time").toLocalTime() : null);
                training.setEndTime(resultSet.getTime("trg_end_time") != null ? resultSet.getTime("trg_end_time").toLocalTime() : null);
                training.setVisited(resultSet.getInt("trg_visited") == 1);
                training.setClientNote(resultSet.getString("trg_client_note"));
                training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                training.setIdOrder(resultSet.getInt("id_order"));
                training.setIdTrainer(resultSet.getInt("id_trainer"));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found entityById: ", e);
        }
        return training;
    }

    @Override
    public List<Training> findEntitiesByTrainer(int idUser) throws DaoException {
        List<Training> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINER_TRAININGS)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Training training = new Training();

                    training.setIdTraining(resultSet.getInt("id_training"));
                    training.setDate(resultSet.getDate("trg_date") != null ? resultSet.getDate("trg_date").toLocalDate() : null);
                    training.setStartTime(resultSet.getTime("trg_start_time") != null ? resultSet.getTime("trg_start_time").toLocalTime() : null);
                    training.setEndTime(resultSet.getTime("trg_end_time") != null ? resultSet.getTime("trg_end_time").toLocalTime() : null);
                    training.setVisited(resultSet.getInt("trg_visited") == 1);
                    training.setClientNote(resultSet.getString("trg_client_note"));
                    training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                    training.setIdOrder(resultSet.getInt("id_order"));
                    training.setIdTrainer(resultSet.getInt("id_trainer"));

                    list.add(training);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find entityByTrainer: ", e);
        }
        return list;
    }

    @Override
    public List<Training> findClientTrainings(int idUser) throws DaoException {
        List<Training> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_TRAININGS)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Training training = new Training();

                    training.setIdTraining(resultSet.getInt("id_training"));
                    training.setDate(resultSet.getDate("trg_date") != null ? resultSet.getDate("trg_date").toLocalDate() : null);
                    training.setStartTime(resultSet.getTime("trg_start_time") != null ? resultSet.getTime("trg_start_time").toLocalTime() : null);
                    training.setEndTime(resultSet.getTime("trg_end_time") != null ? resultSet.getTime("trg_end_time").toLocalTime() : null);
                    training.setVisited(resultSet.getInt("trg_visited") == 1);
                    training.setClientNote(resultSet.getString("trg_client_note"));
                    training.setTrainerNote(resultSet.getString("trg_trainer_note"));
                    training.setIdOrder(resultSet.getInt("id_order"));
                    training.setIdTrainer(resultSet.getInt("id_trainer"));

                    list.add(training);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find trainerTrainings: ", e);
        }
        return list;
    }

    @Override
    public List<Trainer> findClientTrainers() throws DaoException {
        List<Trainer> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_CLIENT_TRAININGS)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));

                    list.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find all: ", e);
        }
        return list;
    }
}
