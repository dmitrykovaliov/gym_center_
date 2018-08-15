package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.dao.TrainerDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.TrainerDaoImpl;
import com.dk.gym.entity.Trainer;
import com.dk.gym.builder.TrainerDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.util.FileLoader;
import com.dk.gym.validation.entity.TrainerValidator;
import com.dk.gym.validation.chain.ChainIdValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.PARAM_ID;
import static com.dk.gym.service.ParamConstant.PARAM_USER_ID;


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


    public ReturnMessageType createTrainer(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new TrainerValidator().validate(content)) {
            try (TrainerDao trainerDao = new TrainerDaoImpl()) {

                new FileLoader().loadFile(Trainer.class.getSimpleName().toLowerCase(), content);

                Trainer trainer = new TrainerDirector().buildTrainer(content);

                int createdItemId = trainerDao.create(trainer);

                if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                    message = DONE;
                } else {
                    message = ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "CreateTrainerMessage: " + message);

        return message;
    }

    public List<Trainer> findAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (TrainerDao trainerDao = new TrainerDaoImpl()) {
            itemList = trainerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public ReturnMessageType updateTrainer(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new TrainerValidator().validate(content)) {
            TransactionManager transactionManager = new TransactionManager();
                try (TrainerDao trainerDao = new TrainerDaoImpl()) {

                    transactionManager.startTransaction(trainerDao);

                    Trainer trainer = trainerDao.findById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new FileLoader().loadFile(Trainer.class.getSimpleName().toLowerCase(), content);

                    new TrainerDirector().buildTrainer(trainer, content);

                    trainerDao.update(trainer);

                    transactionManager.commit();

                    message = DONE;

                } catch (DaoException e) {
                    transactionManager.rollback();
                    throw new ServiceException(e);
                }

        }
        LOGGER.log(Level.DEBUG, "updateTrainerMessage: " + message);

        return message;
    }

    public ReturnMessageType deleteTrainer(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new ChainIdValidator().validate(content.findParameter(PARAM_ID))) {
            try (TrainerDao trainerDao = new TrainerDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                trainerDao.delete(parsedId);

                message = DONE;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }

        LOGGER.log(Level.DEBUG, "deleteTrainerMessage: " + message);

        return message;
    }

    public Trainer findTrainerByUserId(SessionRequestContent content) throws ServiceException {
        Trainer trainer;

        try (TrainerDao trainerDao = new TrainerDaoImpl()) {
            trainer = trainerDao.findByUserId((int)content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "trainerByUserId: " + trainer);

        return trainer;
    }
}
