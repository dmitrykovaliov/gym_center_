package com.dk.gym.service;

import com.dk.gym.exception.ServiceException;

public interface UserService {

    String checkLogin(String enterLogin, String enterPass) throws ServiceException;
}
