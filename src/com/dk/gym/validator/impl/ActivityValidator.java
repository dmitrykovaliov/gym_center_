package com.dk.gym.validator.impl;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.validator.ChainPriceValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.NotEmptyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.constant.ParamConstant.*;

public class ActivityValidator {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_NOTE_LENGTH = 1;
    private static final int MAX_NOTE_LENGTH = 1500;

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public ActivityValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateName(content.findParameter(PARAM_NAME));
        valid = valid && validatePrice(content.findParameter(PARAM_PRICE));
        valid = valid && validateNote(content.findParameter(PARAM_NOTE));

        return lineNotEmpty && valid;
    }

    private boolean validateName(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(MIN_NAME_LENGTH, MAX_NAME_LENGTH).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "name: " + valid);
        return valid;
    }

    private boolean validatePrice(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainPriceValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "price: " + valid);
        return valid;
    }

    private boolean validateNote(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(MIN_NOTE_LENGTH, MAX_NOTE_LENGTH).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "note: " + valid);
        return valid;
    }
}


