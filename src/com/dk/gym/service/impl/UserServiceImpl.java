package com.dk.gym.service.impl;

import com.dk.gym.dao.UserDao;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.UserService;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.ChainPassValidator;
import com.dk.gym.validator.LengthValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;
import java.util.List;
import static com.dk.gym.constant.CommonConstant.MESSAGE_ERROR_ENCRYPT;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();

    private static UserServiceImpl instance;

    private UserServiceImpl() {}

    public static UserServiceImpl getInstance() {
        if(instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    public String checkLogin(String login, String pass) throws ServiceException {

        String message = "";
        List<User> userList;

        if (new LengthValidator(4, 15).validate(login) && new ChainPassValidator().validate(pass)) {

            String encryptPass = CryptPass.cryptSha(pass);

            if (encryptPass == null) {
                throw new ServiceException(MESSAGE_ERROR_ENCRYPT);
            }
            try (UserDao userDao = new UserDaoImpl()) {
                userList = userDao.findAll();
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
            for (User user : userList) {
                if (user.getLogin().equals(login) && user.getPassword().equals(encryptPass)) {
                    message = user.getRole().toString();
                    break;
                }
            }
        } else {
            message = LocaleManager.getProperty("message.error.invalid");
        }

        LOGGER.log(Level.DEBUG, "Message: " + message);

        return message;
    }
}
