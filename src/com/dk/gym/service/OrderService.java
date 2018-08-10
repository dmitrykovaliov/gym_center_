package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.dao.OrderDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.OrderDaoImpl;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.builder.OrderDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validator.OrderValidator;
import com.dk.gym.validator.chain.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.PARAM_ID;
import static com.dk.gym.service.ParamConstant.PARAM_USER_ID;


public class OrderService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static OrderService instance;

    private OrderService() {
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public ReturnMessageType createOrder(RequestContent content) throws ServiceException {

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

        LOGGER.log(Level.DEBUG, "CreateItemMessage: " + message);

        return message;
    }

    public List<Order> findAllOrder() throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Client> findAllClient() throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllClient();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Activity> findAllActivity() throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivity();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public ReturnMessageType updateOrder(RequestContent content) throws ServiceException {

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

    public ReturnMessageType deleteOrder(RequestContent content) throws ServiceException {

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

    public List<Order> findAllOrderByTrainer(RequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllOrderByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    public List<Client> findAllClientByTrainer(RequestContent content) throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllClientByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    public List<Activity> findAllActivityByTrainer(RequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivityByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }


    public List<Order> findAllOrderByClient(RequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllOrderByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }

    public List<Activity> findAllActivityByClient(RequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivityByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return itemList;
    }
}
