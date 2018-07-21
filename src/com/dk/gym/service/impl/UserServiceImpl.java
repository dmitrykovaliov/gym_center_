package com.dk.gym.service.impl;


import com.dk.gym.dao.UserDao;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.User;
import com.dk.gym.exception.DaoException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.UserService;
import com.dk.gym.util.CryptPass;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public Role checkLogin(String enterLogin, String enterPass) throws ServiceException {

        List<User> list;
        Role role = null;

            String encryptPass = CryptPass.cryptSha(enterPass);

            if(encryptPass == null) {
                throw new ServiceException(MESSAGE_ERROR_ENCRYPT);
            }

        try (UserDao userDao = new UserDaoImpl()) {
            list = userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

        for (User user : list) {
            if(user.getLogin().equals(enterLogin) && user.getPassword().equals(encryptPass)) {
                role = user.getRole();
                break;
            }
        }

        LOGGER.log(Level.DEBUG, role);

        return role;
    }

}
