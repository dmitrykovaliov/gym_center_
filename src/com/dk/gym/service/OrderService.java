package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.dao.OrderDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.OrderDaoImpl;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.builder.OrderDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validation.entity.OrderValidator;
import com.dk.gym.validation.chain.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.PARAM_ID;
import static com.dk.gym.service.ParamConstant.PARAM_USER_ID;


/**
 * The Class OrderService. Singleton. Logical layer of the application.
 * Contains several useful methods to support interaction between dao and commands.
 */
public class OrderService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The instance. */
    private static OrderService instance;

    /**
     * Instantiates a new order service.
     */
    private OrderService() {
    }

    /**
     * Gets the single instance of OrderService.
     *
     * @return single instance of OrderService
     */
    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    /**
     * Creates the order.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType createOrder(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new OrderValidator().validate(content)) {
            try (OrderDao orderDao = new OrderDaoImpl()) {

                Order order = new OrderDirector().buildOrder(content);

                int createdItemId = orderDao.create(order);

                if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                    message = DONE;
                } else {
                    message = ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "CreateOrderMessage: " + message);

        return message;
    }

    /**
     * Find all order.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Order> findAllOrder() throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Find related all client.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Client> findRelatedAllClient() throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findRelatedAllClient();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Find related all activity.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Activity> findRelatedAllActivity() throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findRelatedAllActivity();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Update order.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType updateOrder(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new OrderValidator().validate(content)) {
            TransactionManager transactionManager = new TransactionManager();
                try (OrderDao orderDao = new OrderDaoImpl()) {

                    transactionManager.startTransaction(orderDao);

                    Order order = orderDao.findById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new OrderDirector().buildOrder(order, content);

                    orderDao.update(order);

                    transactionManager.commit();

                    message = DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
            }

        LOGGER.log(Level.DEBUG, "UpdateOrderMessage: " + message);

        return message;
    }

    /**
     * Delete order.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType deleteOrder(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (OrderDao orderDao = new OrderDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                orderDao.delete(parsedId);

                message = DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return message;
    }

    /**
     * Find all order by trainer.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Order> findAllOrderByTrainer(SessionRequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllOrderByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    /**
     * Find related all client by trainer.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Client> findRelatedAllClientByTrainer(SessionRequestContent content) throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findRelatedAllClientByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    /**
     * Find related all activity by trainer.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Activity> findRelatedAllActivityByTrainer(SessionRequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findRelatedAllActivityByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }


    /**
     * Find all order by client.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Order> findAllOrderByClient(SessionRequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findRelatedAllOrderByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    /**
     * Find related all activity by client.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Activity> findRelatedAllActivityByClient(SessionRequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivityByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }
}
