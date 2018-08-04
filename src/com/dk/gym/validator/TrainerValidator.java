package com.dk.gym.validator;

import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.constant.ParamConstant.*;

public class TrainerValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public TrainerValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateName(content.findParameter(PARAM_NAME));
        valid = valid && validateLastName(content.findParameter(PARAM_LASTNAME));
        valid = valid && validatePhone(content.findParameter(PARAM_PHONE));
        valid = valid && validatePersonalData(content.findParameter(PARAM_PERSONAL_DATA));
        valid = valid && validatePicPath(content.findParameter(PARAM_ICONPATH));

        return lineNotEmpty && valid;
    }

    private boolean validateName(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 50).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "name: " + valid);
        return valid;
    }

    private boolean validateLastName(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 50).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "lastName: " + valid);
        return valid;
    }

    private boolean validatePhone(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainPhoneValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "phone: " + valid);
        return valid;
    }

    private boolean validatePersonalData(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 3000).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "personalData: " + valid);
        return valid;
    }
    private boolean validatePicPath(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 500).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "picPath: " + valid);
        return valid;
    }
}


