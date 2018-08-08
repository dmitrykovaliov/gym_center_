package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.dao.PrescriptionDao;
import com.dk.gym.dao.TransactionManager;
import com.dk.gym.dao.impl.PrescriptionDaoImpl;
import com.dk.gym.entity.Prescription;
import com.dk.gym.builder.PrescriptionDirector;
import com.dk.gym.entity.Trainer;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.validator.impl.PrescriptionValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new PrescriptionValidator().validate(content)) {
            try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {

                Prescription prescription = new PrescriptionDirector().buildPrescription(content);

                int createdItemId = prescriptionDao.create(prescription);

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

    public List<Prescription> findItems() throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public List<Trainer> findTrainerItems() throws ServiceException {
        List<Trainer> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findAllTrainer();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        LOGGER.log(Level.INFO, "Size of TrainerItems: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new PrescriptionValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {

                    transactionManager.startTransaction(prescriptionDao);

                    Prescription prescription = prescriptionDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ORDER_ID)),
                            Integer.parseInt(content.findParameter(PARAM_TRAINER_ID)));

                    new PrescriptionDirector().buildPrescription(prescription, content);

                    prescriptionDao.update(prescription);

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

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            int parsedOrderId = Integer.parseInt(content.findParameter(PARAM_ORDER_ID));
            int parsedTrainerId = Integer.parseInt(content.findParameter(PARAM_TRAINER_ID));

            LOGGER.log(Level.DEBUG, "orderId: " + parsedOrderId);
            LOGGER.log(Level.DEBUG, "trainerId: " + parsedTrainerId);

            prescriptionDao.delete(parsedOrderId, parsedTrainerId);

            message = ReturnMessageType.DONE;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.DEBUG, "DeleteItemMessage: " + message);

        return message;
    }

    public List<Prescription> findItemsByTrainer(RequestContent content) throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findTrainerPrescriptions((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public List<Prescription> findClientPrescriptions(RequestContent content) throws ServiceException {
        List<Prescription> itemList;

        try (PrescriptionDao prescriptionDao = new PrescriptionDaoImpl()) {
            itemList = prescriptionDao.findClientPrescriptions((int) content.findSessionAttribute(PARAM_USER_ID));
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }
}
