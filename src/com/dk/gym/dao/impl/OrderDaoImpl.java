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
import static com.dk.gym.dao.DatabaseConstant.*;

/**
 * The Class OrderDaoImpl. Implementation of order dao.
 */
public class OrderDaoImpl extends OrderDao {

    /** The Constant SQL_INSERT_ORDER. */
    private static final String SQL_INSERT_ORDER = "INSERT INTO order_(id_order, ord_date, ord_price, ord_discount, " +
            "ord_closure, ord_feedback, id_client, id_activity) " +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    
    /** The Constant SQL_UPDATE_ORDER. */
    private static final String SQL_UPDATE_ORDER = "UPDATE order_ SET ord_date = ?, ord_price = ?, " +
            "ord_discount = ?, ord_closure = ?, ord_feedback = ?, id_client = ?, id_activity = ?  " +
            "WHERE id_order = ?";
    
    /** The Constant SQL_SELECT_ALL_ORDER. */
    private static final String SQL_SELECT_ALL_ORDER = "SELECT id_order, ord_date, ord_price, ord_discount, " +
            "ord_closure, ord_feedback, c.id_client, cl_name, cl_lastname, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "ORDER BY ord_date DESC, id_order ASC";
    
    /** The Constant SQL_SELECT_ALL_ORDER_BY_TRAINER. */
    private static final String SQL_SELECT_ALL_ORDER_BY_TRAINER = "SELECT order_.id_order, ord_date, ord_price, " +
            "ord_discount, ord_closure, " +
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
            "ORDER BY ord_date DESC,  id_order ASC;";
    
    /** The Constant SQL_SELECT_RELATED_ALL_ORDER_BY_CLIENT. */
    private static final String SQL_SELECT_RELATED_ALL_ORDER_BY_CLIENT = "SELECT id_order, ord_date, ord_price, " +
            "ord_discount, ord_closure, ord_feedback, c.id_client, a.id_activity, act_name " +
            "FROM order_ " +
            "JOIN client c ON order_.id_client = c.id_client " +
            "JOIN activity a ON order_.id_activity = a.id_activity " +
            "JOIN user u ON c.id_user = u.id_user " +
            "WHERE u.id_user = ? " +
            "ORDER BY ord_date DESC,  id_order ASC;";
    
    /** The Constant SQL_SELECT_ORDER_BY_ID. */
    private static final String SQL_SELECT_ORDER_BY_ID = "SELECT id_order, ord_date, ord_price, ord_discount," +
            "ord_closure, ord_feedback, id_client, id_activity FROM order_ " +
            "where id_order=?";
    
    /** The Constant SQL_DELETE_ORDER. */
    private static final String SQL_DELETE_ORDER = "DELETE FROM order_ WHERE id_order = ?";


    /**
     * Instantiates a new order dao impl.
     */
    public OrderDaoImpl() {
        connection = ConnectionPool.getInstance().receiveConnection();
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#create(com.dk.gym.entity.Entity)
     */
    @Override
    public int create(Order entity) throws DaoException {

        try (PreparedStatement statement = connection
                .prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
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

        return RETURNED_NEGATIVE_RESULT;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#update(com.dk.gym.entity.Entity)
     */
    @Override
    public boolean update(Order entity) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ORDER)) {

            statement.setDate(1, entity.getDate() != null ? Date.valueOf(entity.getDate()) : null);
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#delete(int)
     */
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

    /* (non-Javadoc)
     * @see com.dk.gym.dao.AbstractDao#findAll()
     */
    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {

                while (resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt(ID_ORDER));
                    if(resultSet.getDate(ORD_DATE)!=null) {
                        order.setDate(resultSet.getDate(ORD_DATE).toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal(ORD_PRICE));
                    order.setDiscount(resultSet.getInt(ORD_DISCOUNT));
                    if(resultSet.getDate(ORD_CLOSURE)!=null) {
                        order.setClosureDate(resultSet.getDate(ORD_CLOSURE).toLocalDate());
                    }
                    order.setFeedback(resultSet.getString(ORD_FEEDBACK));
                    order.setIdClient(resultSet.getInt(ID_CLIENT));
                    order.setIdActivity(resultSet.getInt(ID_ACTIVITY));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found all: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findRelatedAllClient()
     */
    @Override
    public List<Client> findRelatedAllClient() throws DaoException {
        List<Client> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt(ID_CLIENT));
                    client.setName(resultSet.getString(CL_NAME));
                    client.setLastName(resultSet.getString(CL_LASTNAME));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allClient: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findRelatedAllActivity()
     */
    @Override
    public List<Activity> findRelatedAllActivity() throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ORDER)) {
                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt(ID_ACTIVITY));
                    activity.setName(resultSet.getString(ACT_NAME));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allClient: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findById(int)
     */
    @Override
    public Order findById(int id) throws DaoException {

        Order order = new Order();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ORDER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();

                order.setIdOrder(resultSet.getInt(ID_ORDER));
                if(resultSet.getDate(ORD_DATE)!=null) {
                    order.setDate(resultSet.getDate(ORD_DATE).toLocalDate());
                }
                order.setPrice(resultSet.getBigDecimal(ORD_PRICE));
                order.setDiscount(resultSet.getInt(ORD_DISCOUNT));
                if(resultSet.getDate(ORD_CLOSURE)!=null) {
                    order.setClosureDate(resultSet.getDate(ORD_CLOSURE).toLocalDate());
                }
                order.setFeedback(resultSet.getString(ORD_FEEDBACK));
                order.setIdClient(resultSet.getInt(ID_CLIENT));
                order.setIdActivity(resultSet.getInt(ID_ACTIVITY));
            }
        } catch (SQLException e) {
            throw new DaoException("Not found byId", e);
        }
        return order;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findAllOrderByTrainer(int)
     */
    @Override
    public List<Order> findAllOrderByTrainer(int idUser) throws DaoException {

        List<Order> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDER_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt(ID_ORDER));
                    if (resultSet.getDate(ORD_DATE) != null) {
                        order.setDate(resultSet.getDate(ORD_DATE).toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal(ORD_PRICE));
                    order.setDiscount(resultSet.getInt(ORD_DISCOUNT));
                    if (resultSet.getDate(ORD_CLOSURE) != null) {
                        order.setClosureDate(resultSet.getDate(ORD_CLOSURE).toLocalDate());
                    }
                    order.setFeedback(resultSet.getString(ORD_FEEDBACK));
                    order.setIdClient(resultSet.getInt(ID_CLIENT));
                    order.setIdActivity(resultSet.getInt(ID_ACTIVITY));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allByTrainer", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findRelatedAllClientByTrainer(int)
     */
    @Override
    public List<Client> findRelatedAllClientByTrainer(int idUser) throws DaoException {
        List<Client> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDER_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Client client = new Client();

                    client.setIdClient(resultSet.getInt(ID_CLIENT));
                    client.setName(resultSet.getString(CL_NAME));
                    client.setLastName(resultSet.getString(CL_LASTNAME));

                    list.add(client);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allClientByTrainer: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findRelatedAllActivityByTrainer(int)
     */
    @Override
    public List<Activity> findRelatedAllActivityByTrainer(int idUser) throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_ORDER_BY_TRAINER)) {
            statement.setInt(1, idUser);
            statement.setInt(2, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt(ID_ACTIVITY));
                    activity.setName(resultSet.getString(ACT_NAME));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allActivityByTrainer: ", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findRelatedAllOrderByClient(int)
     */
    @Override
    public List<Order> findRelatedAllOrderByClient(int idUser) throws DaoException {
        List<Order> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RELATED_ALL_ORDER_BY_CLIENT)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {

                    Order order = new Order();

                    order.setIdOrder(resultSet.getInt(ID_ORDER));
                    if (resultSet.getDate(ORD_DATE) != null) {
                        order.setDate(resultSet.getDate(ORD_DATE).toLocalDate());
                    }
                    order.setPrice(resultSet.getBigDecimal(ORD_PRICE));
                    order.setDiscount(resultSet.getInt(ORD_DISCOUNT));
                    if (resultSet.getDate(ORD_CLOSURE) != null) {
                        order.setClosureDate(resultSet.getDate(ORD_CLOSURE).toLocalDate());
                    }
                    order.setFeedback(resultSet.getString(ORD_FEEDBACK));
                    order.setIdClient(resultSet.getInt(ID_CLIENT));
                    order.setIdActivity(resultSet.getInt(ID_ACTIVITY));

                    list.add(order);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found relatedAllByClient", e);
        }
        return list;
    }

    /* (non-Javadoc)
     * @see com.dk.gym.dao.OrderDao#findAllActivityByClient(int)
     */
    @Override
    public List<Activity> findAllActivityByClient(int idUser) throws DaoException {
        List<Activity> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RELATED_ALL_ORDER_BY_CLIENT)) {
            statement.setInt(1, idUser);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    Activity activity = new Activity();

                    activity.setIdActivity(resultSet.getInt(ID_ACTIVITY));
                    activity.setName(resultSet.getString(ACT_NAME));

                    list.add(activity);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("Not found allActivityByClient: ", e);
        }
        return list;
    }
}
