package com.dk.gym.service.impl;

import com.dk.gym.dao.ActivityDao;
import com.dk.gym.dao.impl.ActivityDaoImpl;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ActivityService;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;

import java.math.BigDecimal;
import java.util.List;

import static com.dk.gym.constant.ParamConstant.PARAM_ERROR;

public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static ActivityServiceImpl instance;

    private ActivityServiceImpl() {
    }

    public static ActivityServiceImpl getInstance() {
        if (instance == null) {
            instance = new ActivityServiceImpl();
        }

        return instance;
    }

    @Override
    public List<Activity> revertItems() throws ServiceException {
        List<Activity> itemList;

        try (ActivityDao activityDao = new ActivityDaoImpl()) {
            itemList = activityDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }


    @Override
    public String createItem(boolean updated, Activity entity) throws ServiceException {

        String message;

        if (updated) {
            try (ActivityDao activityDao = new ActivityDaoImpl()) {
                int idInt = activityDao.create(entity);
                message = String.valueOf(idInt);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            message = LocaleManager.getProperty("message.error.invalid");
        }

        return message;
    }

    @Override
    public Activity findItemById(String id) throws ServiceException {

        Activity entity;
        int idInt = Integer.parseInt(id);

        try (ActivityDao activityDao = new ActivityDaoImpl()) {
            entity = activityDao.findEntityById(idInt);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        return entity;
    }

    @Override
    public String updateItem(boolean updated, Activity entity) throws ServiceException {
        String message;

        if (updated) {
            try (ActivityDao activityDao = new ActivityDaoImpl()) {
                boolean done = activityDao.update(entity);
                message = String.valueOf(done);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            message = LocaleManager.getProperty("message.error.invalid");
        }

        return message;
    }

    @Override
    public boolean initItem(String name, String price, String note, Activity updateEntity) {
        BaseValidator notEmptyValidator = new NotEmptyValidator();

        boolean updated = true;

        if (notEmptyValidator.validate(name)) {
            if (new LengthValidator(1, 200).validate(name)) {
                updateEntity.setName(name);
            } else {
                updated = false;
            }
        }

        if (notEmptyValidator.validate(price)) {
            if (new ChainPriceValidator().validate(price)) {
                updateEntity.setPrice(new BigDecimal(price));
            } else {
                updated = false;
            }
        }

        if (notEmptyValidator.validate(note)) {
            if (new LengthValidator(1, 1500).validate(note)) {
                updateEntity.setNote(note);
            } else {
                updated = false;
            }
        }
        return updated;
    }

    @Override
    public String deleteItem(String id) throws ServiceException {

        String message;

            if (new ChainIdValidator().validate(id)) {

                int parsedId = Integer.parseInt(id);

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                try (ActivityDao activityDao = new ActivityDaoImpl()) {

                    boolean deleted = activityDao.delete(parsedId);
                    message = String.valueOf(deleted);

                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
            } else {
                message = LocaleManager.getProperty("message.error.invalid");
            }

        LOGGER.log(Level.DEBUG, "Message: " + message);

        return message;
    }
}
