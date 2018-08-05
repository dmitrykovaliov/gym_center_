package com.dk.gym.validator.impl;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Role;
import com.dk.gym.validator.ChainPassValidator;
import com.dk.gym.validator.EnumValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.NotEmptyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.constant.ParamConstant.*;

public class UserValidator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MIN_LOGIN_LENGTH = 4;
    private static final int MAX_LOGIN_LENGTH = 15;

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public UserValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateLogin(content.findParameter(PARAM_LOGIN));
        valid = valid && validatePass(content.findParameter(PARAM_PASS));
        valid = valid && validateRole(content.findParameter(PARAM_ROLE));

        return lineNotEmpty && valid;
    }

    private boolean validateLogin(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "login: " + valid);
        return valid;
    }

    private boolean validatePass(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainPassValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "pass: " + valid);
        return valid;
    }

    private boolean validateRole(String parameter) {

        boolean valid = true;

        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new EnumValidator().validate(Role.class, parameter);
        }
        LOGGER.log(Level.DEBUG, "role: " + valid);
        return valid;
    }
}


