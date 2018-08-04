package com.dk.gym.dao.impl;

import com.dk.gym.entity.join.JoinPrescription;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.PrescriptionDao;
import com.dk.gym.entity.Prescription;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDaoImpl extends PrescriptionDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_PRESCRIPTION = "INSERT INTO prescription(id_order, id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PRESCRIPTION = "UPDATE prescription SET pre_date = ?," +
            " pre_weeks = ?, pre_trainings_per_week = ?, pre_trainer_note = ?, pre_client_note = ?, pre_agreed = ?" +
            " WHERE id_order = ? AND id_trainer = ?";

    private static final String SQL_SELECT_ALL_PRESCRIPTION = "SELECT id_order, id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription";

    private static final String SQL_JOIN_ALL_PRESCRIPTION = "SELECT id_order, tr_name, tr_lastname, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription " +
            "JOIN trainer tr ON prescription.id_trainer = tr.id_trainer";

    private static final String SQL_SELECT_PRESCRIPTION_BY_ID = "SELECT id_order, id_trainer, pre_date, pre_weeks, " +
            "pre_trainings_per_week, pre_trainer_note, pre_client_note, pre_agreed  FROM prescription where id_order=?";
    private static final String SQL_DELETE_PRESCRIPTION = "DELETE FROM prescription WHERE id_trainer = ? AND id_order = ?";


    public PrescriptionDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Prescription entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRESCRIPTION, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, entity.getIdOrder());
            statement.setInt(2, entity.getIdTrainer());
            statement.setDate(3, Date.valueOf(entity.getDate()));
            statement.setInt(4, entity.getWeekQuantity());
            statement.setInt(5, entity.getTrainingsWeek());
            statement.setString(6, entity.getTrainerNote());
            statement.setString(7, entity.getClientNote());
            statement.setDate(8, Date.valueOf(entity.getAgreedDate()));

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Can't create prescription ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Prescription entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRESCRIPTION)) {

            statement.setInt(1, entity.getIdOrder());
            statement.setInt(2, entity.getIdTrainer());
            statement.setDate(3, Date.valueOf(entity.getDate()));
            statement.setInt(4, entity.getWeekQuantity());
            statement.setInt(5, entity.getTrainingsWeek());
            statement.setString(6, entity.getTrainerNote());
            statement.setString(7, entity.getClientNote());
            statement.setDate(8, Date.valueOf(entity.getAgreedDate()));

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DaoException("Can't update prescription", e);
        }
    }

    public boolean delete(int idTrainer, int idOrder) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_PRESCRIPTION)) {
            statement.setInt(1, idTrainer);
            statement.setInt(2, idOrder);
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
                    prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                    prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                    prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                    prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                    prescription.setClientNote(resultSet.getString("pre_client_note"));
                    prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());

                    list.add(prescription);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public List<JoinPrescription> findJoinAll() throws DaoException {
        List<JoinPrescription> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_JOIN_ALL_PRESCRIPTION)) {

                while (resultSet.next()) {

                    JoinPrescription joinPrescription = new JoinPrescription();

                    joinPrescription.getPrescription().setIdOrder(resultSet.getInt("id_order"));
                    joinPrescription.getTrainer().setName(resultSet.getString("tr_name"));
                    joinPrescription.getTrainer().setLastName(resultSet.getString("tr_lastname"));
                    joinPrescription.getPrescription().setDate(resultSet.getDate("pre_date").toLocalDate());
                    joinPrescription.getPrescription().setWeekQuantity(resultSet.getInt("pre_weeks"));
                    joinPrescription.getPrescription().setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                    joinPrescription.getPrescription().setTrainerNote(resultSet.getString("pre_trainer_note"));
                    joinPrescription.getPrescription().setClientNote(resultSet.getString("pre_client_note"));
                    joinPrescription.getPrescription().setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());

                    list.add(joinPrescription);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't findJoinAll", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Prescription findEntityById(int id) throws DaoException {

        Prescription prescription = new Prescription();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PRESCRIPTION_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                }

                prescription.setIdOrder(resultSet.getInt("id_order"));
                prescription.setIdTrainer(resultSet.getInt(1));
                prescription.setDate(resultSet.getDate("pre_date").toLocalDate());
                prescription.setWeekQuantity(resultSet.getInt("pre_weeks"));
                prescription.setTrainingsWeek(resultSet.getInt("pre_trainings_per_week"));
                prescription.setTrainerNote(resultSet.getString("pre_trainer_note"));
                prescription.setClientNote(resultSet.getString("pre_client_note"));
                prescription.setAgreedDate(resultSet.getDate("pre_agreed").toLocalDate());

            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return prescription;
    }

    public static void main(String[] args) throws DaoException {
        PrescriptionDao prescriptionDao = new PrescriptionDaoImpl();
        List<Prescription> entities = prescriptionDao.findAll();

        Prescription entity = prescriptionDao.findEntityById(3);
        entity.setIdOrder(6);
        entity.setIdTrainer(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

       LOGGER.log(Level.INFO, prescriptionDao.create(entity));

       entity.setTrainerNote("this is the text");
        LOGGER.log(Level.INFO, entity);


       LOGGER.log(Level.INFO, prescriptionDao.update(entity));

    }
}
