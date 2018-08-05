package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.dao.TrainerDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.TrainerDaoImpl;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.builder.TrainerDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.util.FileLoader;
import com.dk.gym.validator.impl.TrainerValidator;
import com.dk.gym.validator.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.PARAM_ID;


public class TrainerService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static TrainerService instance;

    private TrainerService() {
    }

    public static TrainerService getInstance() {
        if (instance == null) {
            instance = new TrainerService();
        }

        return instance;
    }


    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new TrainerValidator().validate(content)) {
            try (TrainerDao trainerDao = new TrainerDaoImpl()) {

                new FileLoader().loadFile(Trainer.class.getSimpleName().toLowerCase(), content);

                Trainer trainer = new TrainerDirector().buildTrainer(content);

                int createdItemId = trainerDao.create(trainer);

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

    public List<Trainer> findItems() throws ServiceException {
        List<Trainer> itemList;

        try (TrainerDao trainerDao = new TrainerDaoImpl()) {
            itemList = trainerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new TrainerValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (TrainerDao trainerDao = new TrainerDaoImpl()) {

                    transactionManager.startTransaction(trainerDao);

                    Trainer trainer = trainerDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new FileLoader().loadFile(Trainer.class.getSimpleName().toLowerCase(), content);

                    new TrainerDirector().buildTrainer(trainer, content);

                    trainerDao.update(trainer);

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
            try (TrainerDao trainerDao = new TrainerDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                trainerDao.delete(parsedId);
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
