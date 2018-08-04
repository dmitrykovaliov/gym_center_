package com.dk.gym.dao.impl;

import com.dk.gym.dao.OrderDao;
import com.dk.gym.entity.join.JoinOrder;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends OrderDao {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SQL_INSERT_ORDER = "INSERT INTO order_(id_order, ord_date, ord_price, ord_discount, " +
            "ord_closure, ord_feedback, id_client, id_activity) " +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE order_ SET ord_date = ?, ord_price = ?, " +
            "ord_discount = ?, ord_closure = ?, ord_feedback = ?, id_client = ?, id_activity = ?  " +
            "WHERE id_order = ?";
    private static final String SQL_SELECT_ALL_ORDER = "SELECT id_order, ord_date, ord_price, ord_discount, " +
            "ord_closure, ord_feedback, id_client, id_activity FROM order_";
    private static final String SQL_JOIN_ALL_ORDER = "SELECT id_order, ord_date, ord_price, ord_discount,ord_closure," +
            " ord_feedback, cl_name, cl_lastname, act_name FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity";
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT id_order, ord_date, ord_price, ord_discount," +
            "ord_closure, ord_feedback, id_client, id_activity FROM order_ " +
            "where id_order=?";
    private static final String SQL_DELETE_ORDER = "DELETE FROM order_ WHERE id_order = ?";

    public OrderDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    @Override
    public int create(Order entity) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, String.valueOf(entity.getDate()));
            statement.setBigDecimal(2, entity.getPrice());
            statement.setInt(3, entity.getDiscount());
            LocalDate date = entity.getClosureDate();
            if(date != null) {
                statement.setDate(4, Date.valueOf(date));
            } else {
                statement.setDate(4, null);
            }
            statement.setString(5, entity.getFeedback());
            statement.setInt(6, entity.getIdClient());
            statement.setInt(7, entity.getIdActivity());

            statement.executeUpdate();

            try (ResultSet generatedKey = statement.getGeneratedKeys()) {

                if (generatedKey.next()) {
                    return generatedKey.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new DaoException("Can't create order ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {

            statement.setString(1, String.valueOf(entity.getDate()));
            statement.setBigDecimal(2, entity.getPrice());
            statement.setInt(3, entity.getDiscount());

            LocalDate date = entity.getClosureDate();
            if(date != null) {
                statement.setDate(4, Date.valueOf(date));
            } else {
                statement.setDate(4, null);
            }
            statement.setString(5, entity.getFeedback());
            statement.setInt(6, entity.getIdClient());
            statement.setInt(7, entity.getIdActivity());
            statement.setInt(8, entity.getIdOrder());

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            throw new DaoException("Can't update order", e);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {

                while (resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt("id_order"));
                    order.setDate(resultSet.getDate("ord_date").toLocalDate());
                    order.setPrice(resultSet.getBigDecimal("ord_price"));
                    order.setDiscount(resultSet.getInt("ord_discount"));

                    Date date = resultSet.getDate("ord_closure");
                    if(date!=null) {
                        order.setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                    }
                    order.setFeedback(resultSet.getString("ord_feedback"));
                    order.setIdClient(resultSet.getInt("id_client"));
                    order.setIdActivity(resultSet.getInt("id_activity"));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public List<JoinOrder> findJoinAll() throws DaoException {
        List<JoinOrder> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_JOIN_ALL_ORDER)) {

                while (resultSet.next()) {

                    JoinOrder joinOrder = new JoinOrder();

                    joinOrder.getOrder().setIdOrder(resultSet.getInt("id_order"));
                    joinOrder.getOrder().setDate(resultSet.getDate("ord_date").toLocalDate());
                    joinOrder.getOrder().setPrice(resultSet.getBigDecimal("ord_price"));
                    joinOrder.getOrder().setDiscount(resultSet.getInt("ord_discount"));
                    if(resultSet.getDate("ord_closure")!=null) {
                        joinOrder.getOrder().setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                    }
                    joinOrder.getOrder().setFeedback(resultSet.getString("ord_feedback"));
                    joinOrder.getClient().setName(resultSet.getString("cl_name"));
                    joinOrder.getClient().setLastName(resultSet.getString("cl_lastname"));
                    joinOrder.getActivity().setName(resultSet.getString("act_name"));

                    list.add(joinOrder);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't findJoinAll", e);
        }

        LOGGER.log(Level.INFO, list.size());

        return list;
    }

    @Override
    public Order findEntityById(int id) throws DaoException {

        Order order = new Order();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet != null) {
                    resultSet.next();
                }

                order.setIdOrder(resultSet.getInt("id_order"));
                order.setDate(resultSet.getDate("ord_date").toLocalDate());
                order.setPrice(resultSet.getBigDecimal("ord_price"));
                order.setDiscount(resultSet.getInt("ord_discount"));
                Date date = resultSet.getDate("ord_closure");
                if(date!=null) {
                    order.setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                }
                order.setFeedback(resultSet.getString("ord_feedback"));
                order.setIdClient(resultSet.getInt("id_client"));
                order.setIdActivity(resultSet.getInt("id_activity"));

            }
        } catch (SQLException e) {
            throw new DaoException("Can't find", e);
        }

        return order;
    }

    public static void main(String[] args) throws DaoException {
        OrderDao orderDao = new OrderDaoImpl();
        List<Order> entities = orderDao.findAll();

        Order entity = orderDao.findEntityById(3);

        LOGGER.log(Level.INFO, entities);
        LOGGER.log(Level.INFO, entity);

       LOGGER.log(Level.INFO, orderDao.create(entity));

       entity.setFeedback("CHECK TEXT");
        LOGGER.log(Level.INFO, entity);


       LOGGER.log(Level.INFO, orderDao.update(entity));

    }
}
