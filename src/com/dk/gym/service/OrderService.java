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
import com.dk.gym.validator.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new OrderValidator().validate(content)) {
            try (OrderDao orderDao = new OrderDaoImpl()) {

                Order order = new OrderDirector().buildOrder(content);

                int createdItemId = orderDao.create(order);

                if (createdItemId != -1) {
                    message = ReturnMessageType.DONE;
                } else {
                    message = ReturnMessageType.ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            message = ReturnMessageType.INVALID;
        }

        LOGGER.log(Level.DEBUG, "CreateItemMessage: " + message);

        return message;
    }

    public List<Order> findItems() throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Order: " + itemList.size());

        return itemList;
    }

    public List<Client> findClient() throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllClient();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Client: " + itemList.size());

        return itemList;
    }

    public List<Activity> findActivity() throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivity();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Activity: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new OrderValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (OrderDao orderDao = new OrderDaoImpl()) {

                    transactionManager.startTransaction(orderDao);

                    Order order = orderDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new OrderDirector().buildOrder(order, content);

                    orderDao.update(order);

                    transactionManager.commit();

                    message = ReturnMessageType.DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
            }
        } else {
            message = ReturnMessageType.INVALID;
        }
        LOGGER.log(Level.DEBUG, "UpdateItemMessage: " + message);

        return message;
    }

    public ReturnMessageType deleteItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (OrderDao orderDao = new OrderDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                orderDao.delete(parsedId);

                message = ReturnMessageType.DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            message = ReturnMessageType.INVALID;
        }
        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public List<Order> findByTrainer(RequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllbyTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Orders by Trainer: " + itemList.size());

        return itemList;
    }

    public List<Client> findByTrainerClient(RequestContent content) throws ServiceException {
        List<Client> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllClientByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Client by Trainer: " + itemList.size());

        return itemList;
    }

    public List<Activity> findByTrainerActivity(RequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findAllActivityByTrainer((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Activity by Trainer: " + itemList.size());

        return itemList;
    }


    public List<Order> findClientOrders(RequestContent content) throws ServiceException {
        List<Order> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findOrdersByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Orders by Client: " + itemList.size());

        return itemList;
    }

    public List<Activity> findActivitiesByClient(RequestContent content) throws ServiceException {
        List<Activity> itemList;

        try (OrderDao orderDao = new OrderDaoImpl()) {
            itemList = orderDao.findActivitiesByClient((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of Activity by Trainer: " + itemList.size());

        return itemList;
    }
}
