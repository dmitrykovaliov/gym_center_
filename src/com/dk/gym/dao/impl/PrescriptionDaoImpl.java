package com.dk.gym.dao.impl;

import com.dk.gym.entity.Trainer;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.PrescriptionDao;
import com.dk.gym.entity.Prescription;
import com.dk.gym.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDaoImpl extends PrescriptionDao {

    private static final String SQL_INSERT_PRESCRIPTION = "INSERT INTO prescription(id_order, id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRESCRIPTION = "UPDATE prescription SET pre_date = ?," +
            " pre_weeks = ?, pre_trainings_per_week = ?, pre_trainer_note = ?, pre_client_note = ?, pre_agreed = ?" +
            " WHERE id_order = ? AND id_trainer = ?";

    private static final String SQL_SELECT_ALL_PRESCRIPTION = "SELECT id_order, tr.id_trainer, tr_name, tr_lastname, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription " +
            "JOIN trainer tr ON prescription.id_trainer = tr.id_trainer";
    private static final String SQL_SELECT_TRAINER_PRESCRIPTION = "SELECT id_order, tr.id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription " +
            "JOIN trainer tr ON prescription.id_trainer = tr.id_trainer " +
            "JOIN user u ON tr.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "ORDER BY pre_date DESC";
    private static final String SQL_SELECT_CLIENT_PRESCRIPTION = "SELECT o.id_order, tr.id_trainer, tr_name, " +
            "tr_lastname, tr_name, tr_lastname, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription " +
            "JOIN trainer tr ON prescription.id_trainer = tr.id_trainer " +
            "JOIN order_ o ON prescription.id_order = o.id_order " +
            "JOIN client c ON o.id_client = c.id_client " +
            "JOIN user u ON c.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "ORDER BY pre_date DESC;";


    private static final String SQL_SELECT_PRESCRIPTION_BY_ID = "SELECT id_order, id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription WHERE id_order=? AND id_trainer=?";
    private static final String SQL_DELETE_PRESCRIPTION = "DELETE FROM prescription WHERE id_order = ? AND id_trainer = ?";


    public PrescriptionDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Prescription entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRESCRIPTION, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getIdOrder());
            statement.setInt(2, entity.getIdTrainer());
            statement.setDate(3, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
            statement.setInt(4, entity.getWeekQuantity());
            statement.setInt(5, entity.getTrainingsWeek());
            statement.setString(6, entity.getTrainerNote());
            statement.setString(7, entity.getClientNote());
            statement.setDate(8, entity.getAgreedDate() != null ? Date.valueOf(entity.getAgreedDate()) : null);

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
    public boolean update(Prescription entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRESCRIPTION)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
            statement.setInt(2, entity.getWeekQuantity());
            statement.setInt(3, entity.getTrainingsWeek());
            statement.setString(4, entity.getTrainerNote());
            statement.setString(5, entity.getClientNote());
            statement.setDate(6, entity.getAgreedDate() != null ? Date.valueOf(entity.getAgreedDate()) : null);
            statement.setInt(7, entity.getIdOrder());
            statement.setInt(8, entity.getIdTrainer());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
        }
    }

    public boolean delete(int idOrder, int idTrainer) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRESCRIPTION)) {
            statement.setInt(1, idOrder);
            statement.setInt(2, idTrainer);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Prescription> findAll() throws DaoException {
        List<Prescription> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRESCRIPTION)) {

                while (resultSet.next()) {

                    Prescription prescription = new Prescription();

                    prescription.setIdOrder(resultSet.getInt("id_order"));
                    prescription.setIdTrainer(resultSet.getInt("id_trainer"));
                    if (resultSet.getDate("pre_date") != null) {
                        prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                    }
                    prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                    prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                    prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                    prescription.setClientNote(resultSet.getString("pre_client_note"));
                    if (resultSet.getDate("pre_agreed") != null) {
                        prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());
                    }

                    list.add(prescription);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return list;
    }

    @Override
    public List<Trainer> findAllTrainer() throws DaoException {
        List<Trainer> set = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRESCRIPTION)) {

                while (resultSet.next()) {

                    Trainer trainer = new Trainer();

                    trainer.setIdTrainer(resultSet.getInt("id_trainer"));
                    trainer.setName(resultSet.getString("tr_name"));
                    trainer.setLastName(resultSet.getString("tr_lastname"));

                    set.add(trainer);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allTrainer", e);
        }
        return set;
    }


    @Override
    public Prescription findEntityById(int idOrder, int idTrainer) throws DaoException {

        Prescription prescription = new Prescription();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRESCRIPTION_BY_ID)) {
            statement.setInt(1, idOrder);
            statement.setInt(2, idTrainer);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();

                prescription.setIdOrder(resultSet.getInt("id_order"));
                prescription.setIdTrainer(resultSet.getInt("id_trainer"));
                if (resultSet.getDate("pre_date") != null) {
                    prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                }
                prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                prescription.setClientNote(resultSet.getString("pre_client_note"));
                if (resultSet.getDate("pre_agreed") != null) {
                    prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found by id: ", e);
        }
        return prescription;
    }

    @Override
    public List<Prescription> findTrainerPrescriptions(int idUser) throws DaoException {
        List<Prescription> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_TRAINER_PRESCRIPTION)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Prescription prescription = new Prescription();

                    prescription.setIdOrder(resultSet.getInt("id_order"));
                    prescription.setIdTrainer(resultSet.getInt("id_trainer"));
                    if (resultSet.getDate("pre_date") != null) {
                        prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                    }
                    prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                    prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                    prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                    prescription.setClientNote(resultSet.getString("pre_client_note"));
                    if (resultSet.getDate("pre_agreed") != null) {
                        prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());
                    }

                    list.add(prescription);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found trainerPrescription: ", e);
        }

        return list;
    }

    @Override
    public List<Prescription> findClientPrescriptions(int idUser) throws DaoException {
        List<Prescription> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_CLIENT_PRESCRIPTION)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Prescription prescription = new Prescription();

                    prescription.setIdOrder(resultSet.getInt("id_order"));
                    prescription.setIdTrainer(resultSet.getInt("id_trainer"));
                    if (resultSet.getDate("pre_date") != null) {
                        prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                    }
                    prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                    prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                    prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                    prescription.setClientNote(resultSet.getString("pre_client_note"));
                    if (resultSet.getDate("pre_agreed") != null) {
                        prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());
                    }

                    list.add(prescription);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found clientPrescription", e);
        }

        return list;
    }
}
