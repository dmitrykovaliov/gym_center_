package com.dk.gym.dao.impl;

import com.dk.gym.dao.OrderDao;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.pool.ConnectionPool;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.DaoException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl extends OrderDao {

    private static final String SQL_INSERT_ORDER = "INSERT INTO order_(id_order, ord_date, ord_price, ord_discount, " +
            "ord_closure, ord_feedback, id_client, id_activity) " +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_ORDER = "UPDATE order_ SET ord_date = ?, ord_price = ?, " +
            "ord_discount = ?, ord_closure = ?, ord_feedback = ?, id_client = ?, id_activity = ?  " +
            "WHERE id_order = ?";
    private static final String SQL_SELECT_ALL_ORDER = "SELECT id_order, ord_date, ord_price, ord_discount,ord_closure," +
            " ord_feedback, c.id_client, cl_name, cl_lastname, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "ORDER BY ord_date DESC;";
    private static final String SQL_SELECT_ORDERS_BY_TRAINER = "SELECT order_.id_order, ord_date, ord_price, ord_discount,ord_closure, " +
            "ord_feedback, c.id_client, cl_name, cl_lastname, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "WHERE order_.id_order IN ( " +
            "SELECT order_.id_order " +
            "FROM order_ " +
            "JOIN prescription p ON order_.id_order = p.id_order " +
            "JOIN trainer t ON p.id_trainer = t.id_trainer " +
            "JOIN user u ON t.id_user = u.id_user " +
            "WHERE u.id_user = ?) " +
            "UNION " +
            "SELECT order_.id_order, ord_date, ord_price, ord_discount,ord_closure, " +
            "ord_feedback, c.id_client, cl_name, cl_lastname, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "WHERE order_.id_order IN ( " +
            "SELECT order_.id_order " +
            "FROM order_ " +
            "JOIN training t ON order_.id_order = t.id_order " +
            "JOIN trainer t2 ON t.id_trainer = t2.id_trainer " +
            "JOIN user u ON t2.id_user = u.id_user " +
            "WHERE u.id_user = ?) " +
            "ORDER BY ord_date DESC;";
    private static final String SQL_SELECT_ORDERS_BY_CLIENT = "SELECT id_order, ord_date, ord_price, " +
            "ord_discount, ord_closure, ord_feedback, c.id_client, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "JOIN user u ON c.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "ORDER BY ord_date DESC;";
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
            statement.setDate(4, entity.getClosureDate() != null ? Date.valueOf(entity.getClosureDate()) : null);
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
            throw new DaoException("Not created: ", e);
        }

        return -1;
    }

    @Override
    public boolean update(Order entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {

            statement.setString(1, String.valueOf(entity.getDate()));
            statement.setBigDecimal(2, entity.getPrice());
            statement.setInt(3, entity.getDiscount());
            statement.setDate(4, entity.getClosureDate() != null ? Date.valueOf(entity.getClosureDate()) : null);
            statement.setString(5, entity.getFeedback());
            statement.setInt(6, entity.getIdClient());
            statement.setInt(7, entity.getIdActivity());
            statement.setInt(8, entity.getIdOrder());

            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            throw new DaoException("Not updated: ", e);
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
                    if(resultSet.getDate("ord_date")!=null) {
                        order.setDate(resultSet.getDate("ord_date").toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal("ord_price"));
                    order.setDiscount(resultSet.getInt("ord_discount"));
                    if(resultSet.getDate("ord_closure")!=null) {
                        order.setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                    }
                    order.setFeedback(resultSet.getString("ord_feedback"));
                    order.setIdClient(resultSet.getInt("id_client"));
                    order.setIdActivity(resultSet.getInt("id_activity"));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found all: ", e);
        }
        return list;
    }

    @Override
    public List<Client> findAllClient() throws DaoException {
        List<Client> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt("id_client"));
                    client.setName(resultSet.getString("cl_name"));
                    client.setLastName(resultSet.getString("cl_lastname"));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find allClient: ", e);
        }
        return list;
    }

    @Override
    public List<Activity> findAllActivity() throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {

                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt("id_activity"));
                    activity.setName(resultSet.getString("act_name"));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find allClient: ", e);
        }
        return list;
    }



    @Override
    public Order findEntityById(int id) throws DaoException {

        Order order = new Order();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                order.setIdOrder(resultSet.getInt("id_order"));
                if(resultSet.getDate("ord_date")!=null) {
                    order.setDate(resultSet.getDate("ord_date").toLocalDate());
                }
                order.setPrice(resultSet.getBigDecimal("ord_price"));
                order.setDiscount(resultSet.getInt("ord_discount"));
                if(resultSet.getDate("ord_closure")!=null) {
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

    @Override
    public List<Order> findAllbyTrainer(int idUser) throws DaoException {

        List<Order> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt("id_order"));
                    if (resultSet.getDate("ord_date") != null) {
                        order.setDate(resultSet.getDate("ord_date").toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal("ord_price"));
                    order.setDiscount(resultSet.getInt("ord_discount"));
                    if (resultSet.getDate("ord_closure") != null) {
                        order.setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                    }
                    order.setFeedback(resultSet.getString("ord_feedback"));
                    order.setIdClient(resultSet.getInt("id_client"));
                    order.setIdActivity(resultSet.getInt("id_activity"));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find allByTrainer", e);
        }
        return list;
    }

    @Override
    public List<Client> findAllClientByTrainer(int idUser) throws DaoException {
        List<Client> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt("id_client"));
                    client.setName(resultSet.getString("cl_name"));
                    client.setLastName(resultSet.getString("cl_lastname"));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find allClientByTrainer: ", e);
        }
        return list;
    }

    @Override
    public List<Activity> findAllActivityByTrainer(int idUser) throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt("id_activity"));
                    activity.setName(resultSet.getString("act_name"));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find allActivityByTrainer: ", e);
        }
        return list;
    }

    @Override
    public List<Order> findOrdersByClient(int idUser) throws DaoException {
        List<Order> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_CLIENT)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt("id_order"));
                    if (resultSet.getDate("ord_date") != null) {
                        order.setDate(resultSet.getDate("ord_date").toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal("ord_price"));
                    order.setDiscount(resultSet.getInt("ord_discount"));
                    if (resultSet.getDate("ord_closure") != null) {
                        order.setClosureDate(resultSet.getDate("ord_closure").toLocalDate());
                    }
                    order.setFeedback(resultSet.getString("ord_feedback"));
                    order.setIdClient(resultSet.getInt("id_client"));
                    order.setIdActivity(resultSet.getInt("id_activity"));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allByClient", e);
        }
        return list;
    }

    @Override
    public List<Activity> findActivitiesByClient(int idUser) throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDERS_BY_CLIENT)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt("id_activity"));
                    activity.setName(resultSet.getString("act_name"));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allActivityByClient: ", e);
        }
        return list;
    }
}
