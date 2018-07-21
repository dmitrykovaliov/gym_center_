package com.dk.gym.dao.impl;

import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.dao.PaymentDao;
import com.dk.gym.entity.Payment;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDaoImpl extends PaymentDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_PAYMENT = "INSERT INTO payment(id_payment, pay_date, pay_amount, pay_note, id_order) " +
            "VALUES (NULL, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PAYMENT = "UPDATE payment SET pay_date = ?, pay_amount = ?, pay_note = ?, id_order = ?" +
            " WHERE id_payment = ?";

    private static final String SQL_SELECT_ALL_PAYMENT = "SELECT id_payment, pay_date, pay_amount, pay_note, id_order FROM payment";
    private static final String SQL_SELECT_PAYMENT_BY_ID = "SELECT id_payment, pay_date, pay_amount, pay_note, id_order " +
            "FROM payment where id_payment=?";

    public PaymentDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Payment entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, Date.valueOf(entity.getDate()));
            statement.setBigDecimal(2, entity.getAmount());
            statement.setString(3, entity.getNote());
            statement.setInt(4, entity.getIdOrder());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Can't create payment ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Payment entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PAYMENT)) {

            statement.setDate(1, Date.valueOf(entity.getDate()));
            statement.setBigDecimal(2, entity.getAmount());
            statement.setString(3, entity.getNote());
            statement.setInt(4, entity.getIdOrder());
            statement.setInt(5, entity.getIdPayment());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DaoException("Can't update payment", e);
        }
    }

    @Override
    public List<Payment> findAll() throws DaoException {
        List<Payment> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PAYMENT)) {

                while (resultSet.next()) {

                    Payment payment = new Payment();

                    payment.setIdPayment(resultSet.getInt("id_payment"));
                    payment.setDate(resultSet.getDate("pay_date").toLocalDate());
                    payment.setAmount(resultSet.getBigDecimal("pay_amount"));
                    payment.setNote(resultSet.getString("pay_note"));
                    payment.setIdOrder(resultSet.getInt("id_order"));

                    list.add(payment);

                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Payment findEntityById(int id) throws DaoException {

        Payment payment = new Payment();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_PAYMENT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                }

                payment.setIdPayment(resultSet.getInt("id_payment"));
                payment.setDate(resultSet.getDate("pay_date").toLocalDate());
                payment.setAmount(resultSet.getBigDecimal("pay_amount"));
                payment.setNote(resultSet.getString("pay_note"));
                payment.setIdOrder(resultSet.getInt("id_order"));

            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return payment;
    }

    public static void main(String[] args) throws DaoException {
        PaymentDao paymentDao = new PaymentDaoImpl();
        List<Payment> entities = paymentDao.findAll();

        Payment entity = paymentDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

       LOGGER.log(Level.INFO, paymentDao.create(entity));

       entity.setNote("this is the text");
        LOGGER.log(Level.INFO, entity);


       LOGGER.log(Level.INFO, paymentDao.update(entity));

    }
}
