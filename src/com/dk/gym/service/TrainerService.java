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

/**
 * The Class TrainerService. Singleton. Logical layer of the application.
 * Contains several useful methods to support interaction between dao and commands.
 */
public class TrainerService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The instance. */
    private static TrainerService instance;

    /**
     * Instantiates a new trainer service.
     */
    private TrainerService() {
    }

    /**
     * Gets the single instance of TrainerService.
     *
     * @return single instance of TrainerService
     */
    public static TrainerService getInstance() {
        if (instance == null) {
            instance = new TrainerService();
        }

        return instance;
    }


    /**
     * Creates the trainer.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Find all trainer.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    public List<Trainer> findAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (TrainerDao trainerDao = new TrainerDaoImpl()) {
            itemList = trainerDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    /**
     * Update trainer.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Delete trainer.
     *
     * @param content the content
     * @return the return message type
     * @throws ServiceException the service exception
     */
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

    /**
     * Find trainer by user id.
     *
     * @param content the content
     * @return the trainer
     * @throws ServiceException the service exception
     */
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
