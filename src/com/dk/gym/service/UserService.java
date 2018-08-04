package com.dk.gym.service;

import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.dao.*;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.User;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.builder.UserDirector;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.*;

public class UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }

        return instance;
    }

    public ReturnMessageType createItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new UserValidator().validate(content)) {
            try (UserDao userDao = new UserDaoImpl()) {

                User user = new UserDirector().buildUser(content);

                int createdItemId = userDao.create(user);

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

    public List<User> findItems() throws ServiceException {
        List<User> itemList;

        try (UserDao userDao = new UserDaoImpl()) {
            itemList = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        LOGGER.log(Level.INFO, "Size of collection: " + itemList.size());

        return itemList;
    }

    public ReturnMessageType updateItem(RequestContent content) throws ServiceException {

        ReturnMessageType message;

        if (new UserValidator().validate(content)) {
            try (TransactionManager transactionManager = new TransactionManager()) {
                try (UserDao userDao = new UserDaoImpl()) {

                    transactionManager.startTransaction(userDao);

                    User user = userDao.findEntityById(Integer.parseInt(content.findParameter(PARAM_ID)));

                    new UserDirector().buildUser(user, content);

                    userDao.update(user);

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
            try (UserDao userDao = new UserDaoImpl()) {
                int parsedId = Integer.parseInt(content.findParameter(PARAM_ID));

                LOGGER.log(Level.DEBUG, "ID: " + parsedId);

                userDao.delete(parsedId);

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

    public ReturnMessageType checkUser(RequestContent content) throws ServiceException {

        ReturnMessageType message;
        Role role;

        String login = content.findParameter(PARAM_LOGIN);
        String pass = content.findParameter(PARAM_PASS);
        String encryptPass = CryptPass.cryptSha(pass);

        if (encryptPass == null) {
            throw new ServiceException("Pass was not encrypted");
        }

        LOGGER.log(Level.DEBUG, String.format("Login: %s, pass: %s", login, encryptPass));

        if (new UserValidator().validate(content)) {
                try (UserDao userDao = new UserDaoImpl()) {
                    role = userDao.findUser(login, encryptPass);
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
            
            if (role == null) {
                message = ReturnMessageType.USER_NOT_EXIST;
            } else {
                message = ReturnMessageType.DONE;
                content.insertSessionAttribute(PARAM_ROLE, role.toString());
            }
        } else {
            message = ReturnMessageType.INVALID;
        }
        return message;
    }
}
