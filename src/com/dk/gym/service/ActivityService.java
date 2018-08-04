package com.dk.gym.service;

import com.dk.gym.dao.TransactionManager;
import com.dk.gym.entity.builder.ActivityDirector;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.ActivityDao;
import com.dk.gym.dao.impl.ActivityDaoImpl;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.validator.*;
import com.dk.gym.validator.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.*;


public class ActivityService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static ActivityService instance;

    private ActivityService() {
    }

    public static ActivityService getInstance() {
        if (instance == null) {
            instance = new ActivityService();
        }
        return instance;
    }

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ActivityValidator().validate(content)) {
            try (ActivityDao activityDao = new ActivityDaoImpl()) {

                Activity activity = new ActivityDirector().buildActivity(content);

                int createdItemId = activityDao.create(activity);

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

    public List<Activity> findItems() throws ServiceException {
        List<Activity> itemList;

        try (ActivityDao activityDao = new ActivityDaoImpl()) {
            itemList = activityDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new ActivityValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (ActivityDao activityDao = new ActivityDaoImpl()) {

                    transactionManager.startTransaction(activityDao);

                    Activity activity = activityDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new ActivityDirector().buildActivity(activity, content);

                    activityDao.update(activity);

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
            try (ActivityDao activityDao = new ActivityDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                activityDao.delete(parsedId);

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
}
