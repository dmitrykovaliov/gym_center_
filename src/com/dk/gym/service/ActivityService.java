package com.dk.gym.service;

import com.dk.gym.dao.TransactionManager;
import com.dk.gym.builder.ActivityDirector;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.ActivityDao;
import com.dk.gym.dao.impl.ActivityDaoImpl;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.validation.chain.ChainIdValidator;
import com.dk.gym.validation.entity.ActivityValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.*;

/**
 * The Class ActivityService. Singleton. Logical layer of the application.
 * Contains several useful methods to support interaction between dao and commands.
 */
public class ActivityService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The instance. */
    private static ActivityService instance;

    /**
     * Instantiates a new activity service.
     */
    private ActivityService() {
    }

    /**
     * Gets the single instance of ActivityService.
     *
     * @return single instance of ActivityService
     */
    public static ActivityService getInstance() {
        if (instance == null) {
            instance = new ActivityService();
        }
        return instance;
    }

    /**
     * Creates the activity.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType createActivity(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ActivityValidator().validate(content)) {
            try (ActivityDao activityDao = new ActivityDaoImpl()) {

                Activity activity = new ActivityDirector().buildActivity(content);

                int createdItemId = activityDao.create(activity);

                if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                    message = DONE;
                } else {
                    message = ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.log(Level.DEBUG, "CreateActivityMessage: " + message);

        return message;
    }

    /**
     * Find all activity.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Activity> findAllActivity() throws ServiceException {
        List<Activity> itemList;

        try (ActivityDao activityDao = new ActivityDaoImpl()) {
            itemList = activityDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Update activity.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType updateActivity(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ActivityValidator().validate(content)) {
            TransactionManager transactionManager = new TransactionManager();
                try (ActivityDao activityDao = new ActivityDaoImpl()) {

                    transactionManager.startTransaction(activityDao);

                    Activity activity = activityDao.findById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new ActivityDirector().buildActivity(activity, content);

                    activityDao.update(activity);

                    transactionManager.commit();

                    message = DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
        }

        LOGGER.log(Level.DEBUG, "UpdateActivityMessage: " + message);

        return message;
    }

    /**
     * Delete activity.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType deleteActivity(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message =INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (ActivityDao activityDao = new ActivityDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                activityDao.delete(parsedId);

                message = DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.log(Level.DEBUG, "DeleteActivityMessage: " + message);

        return message;
    }
}
