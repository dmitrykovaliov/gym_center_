package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.dao.TrainingDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.TrainingDaoImpl;
import com.dk.gym.entity.Training;
import com.dk.gym.entity.builder.TrainingDirector;
import com.dk.gym.entity.join.JoinTraining;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validator.TrainingValidator;
import com.dk.gym.validator.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.PARAM_ID;


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

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new TrainingValidator().validate(content)) {
            try (TrainingDao trainingDao = new TrainingDaoImpl()) {

                Training training = new TrainingDirector().buildTraining(content);

                int createdItemId = trainingDao.create(training);

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

    public List<Training> findItems() throws ServiceException {
        List<Training> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public List<JoinTraining> findJoinItems() throws ServiceException {
        List<JoinTraining> itemList;

        try (TrainingDao trainingDao = new TrainingDaoImpl()) {
            itemList = trainingDao.findJoinAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new TrainingValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (TrainingDao trainingDao = new TrainingDaoImpl()) {

                    transactionManager.startTransaction(trainingDao);

                    Training training = trainingDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new TrainingDirector().buildTraining(training, content);

                    trainingDao.update(training);

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
            try (TrainingDao trainingDao = new TrainingDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                trainingDao.delete(parsedId);

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
