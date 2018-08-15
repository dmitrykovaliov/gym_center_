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


public class TrainingService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static TrainingService instance;

    private TrainingService() {
    }

    public static TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

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

    public List<Training> findAllTraining() throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Trainer> findRelatedAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findRelatedAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

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

    public List<Training> findAllTrainingByTrainer(SessionRequestContent content) throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAllTrainingByTrainer((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Training> findAllTrainingByClient(SessionRequestContent content) throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAllTrainingByClient((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

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
