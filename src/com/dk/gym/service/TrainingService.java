package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.dao.TrainingDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.TrainingDaoImpl;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;
import com.dk.gym.builder.TrainingDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validation.entity.TrainingValidator;
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
 * The Class TrainingService. Singleton. Logical layer of the application.
 * Contains several useful methods to support interaction between dao and commands.
 */
public class TrainingService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The instance. */
    private static TrainingService instance;

    /**
     * Instantiates a new training service.
     */
    private TrainingService() {
    }

    /**
     * Gets the single instance of TrainingService.
     *
     * @return single instance of TrainingService
     */
    public static TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

    /**
     * Creates the training.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType createTraining(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new TrainingValidator().validate(content)) {
            try (TrainingDao trainingDao = new TrainingDaoImpl()) {

                Training training = new TrainingDirector().buildTraining(content);

                int createdItemId = trainingDao.create(training);

                if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                    message = DONE;
                } else {
                    message = ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "createTrainingMessage: " + message);

        return message;
    }

    /**
     * Find all training.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Training> findAllTraining() throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Find related all trainer.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Trainer> findRelatedAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findRelatedAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Update training.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType updateTraining(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new TrainingValidator().validate(content)) {
            TransactionManager transactionManager = new TransactionManager();
                try (TrainingDao trainingDao = new TrainingDaoImpl()) {

                    transactionManager.startTransaction(trainingDao);

                    Training training = trainingDao.findById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new TrainingDirector().buildTraining(training, content);

                    trainingDao.update(training);

                    transactionManager.commit();

                    message = DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }
        }
        LOGGER.log(Level.DEBUG, "updateTrainingMessage: " + message);

        return message;
    }

    /**
     * Delete training.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
    public ReturnMessageType deleteTraining(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (TrainingDao trainingDao = new TrainingDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                trainingDao.delete(parsedId);

                message = DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "deleteTrainingMessage: " + message);

        return message;
    }

    /**
     * Find all training by trainer.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Training> findAllTrainingByTrainer(SessionRequestContent content) throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAllTrainingByTrainer((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Find all training by client.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Training> findAllTrainingByClient(SessionRequestContent content) throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAllTrainingByClient((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Find related all trainer by client.
     *
     * @param content the content
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Trainer> findRelatedAllTrainerByClient(SessionRequestContent content) throws ServiceException {
        List<Trainer> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findRelatedAllTrainerByClient((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }
}
