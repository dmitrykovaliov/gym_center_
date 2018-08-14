package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.dao.PrescriptionDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.PrescriptionDaoImpl;
import com.dk.gym.entity.Prescription;
import com.dk.gym.builder.PrescriptionDirector;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validation.impl.PrescriptionValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.ReturnMessageType.*;
import static com.dk.gym.dao.AbstractDao.RETURNED_NEGATIVE_RESULT;
import static com.dk.gym.service.ParamConstant.*;


public class PrescriptionService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static PrescriptionService instance;

    private PrescriptionService() {
    }

    public static PrescriptionService getInstance() {
        if (instance == null) {
            instance = new PrescriptionService();
        }
        return instance;
    }

    public ReturnMessageType createPrescription(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;

        if (new PrescriptionValidator().validate(content)) {
            try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {

                Prescription prescription = new PrescriptionDirector().buildPrescription(content);

                int createdItemId = prescriptionDao.create(prescription);

                if (createdItemId != RETURNED_NEGATIVE_RESULT) {
                    message = DONE;
                } else {
                    message = ENTER_ERROR;
                }
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "createPrescriptionMessage: " + message);

        return message;
    }

    public List<Prescription> findAllPrescription() throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Trainer> findRelatedAllTrainer() throws ServiceException {
        List<Trainer> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findRelatedAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public ReturnMessageType updatePrescription(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message = INVALID;
        if (new PrescriptionValidator().validate(content)) {

            TransactionManager transactionManager = new TransactionManager();
            try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {

                transactionManager.startTransaction(prescriptionDao);

                Prescription prescription = prescriptionDao
                        .findById(Integer.parseInt(content.findParameter(PARAM_ORDER_ID)),
                                Integer.parseInt(content.findParameter(PARAM_TRAINER_ID)));

                new PrescriptionDirector().buildPrescription(prescription, content);

                prescriptionDao.update(prescription);

                transactionManager.commit();

                message = DONE;
            } catch (DaoException e) {
                transactionManager.rollback();
                throw new ServiceException(e);
            }
        }
        LOGGER.log(Level.DEBUG, "updatePrescriptionMessage: " + message);

        return message;
    }

    public ReturnMessageType deletePrescription(SessionRequestContent content) throws ServiceException {

        ReturnMessageType message;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            int orderIdInt = Integer.parseInt(content.findParameter(PARAM_ORDER_ID));
            int trainerIdInt = Integer.parseInt(content.findParameter(PARAM_TRAINER_ID));

            prescriptionDao.delete(orderIdInt, trainerIdInt);

            message = DONE;

        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public List<Prescription> findAllPrescriptionByTrainer(SessionRequestContent content) throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findAllPrescriptionByTrainer((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Prescription> findAllPrescriptionByClient(SessionRequestContent content) throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findAllPrescriptionByClient((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }

    public List<Trainer> findRelatedAllTrainerByClient(SessionRequestContent content) throws ServiceException {
        List<Trainer> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findRelatedAllTrainerByClient((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return itemList;
    }
}
