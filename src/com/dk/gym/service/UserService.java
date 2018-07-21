package com.dk.gym.service;

import com.dk.gym.entity.Role;
import com.dk.gym.exception.ServiceException;


public interface UserService {

    Role checkLogin(String enterLogin, String enterPass) throws ServiceException;
}
