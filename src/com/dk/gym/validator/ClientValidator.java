package com.dk.gym.validator;

import com.dk.gym.entity.Role;
import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;

import static com.dk.gym.constant.ParamConstant.*;

public class ClientValidator {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public ClientValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateName(content.findParameter(PARAM_NAME));
        valid = valid && validateLastName(content.findParameter(PARAM_LASTNAME));
        valid = valid && validatePhone(content.findParameter(PARAM_PHONE));
        valid = valid && validateEmail(content.findParameter(PARAM_EMAIL));
        valid = valid && validatePersonalData(content.findParameter(PARAM_PERSONAL_DATA));
        valid = valid && validateIconPath(content.findPart(PARAM_ICONPATH));

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
    private boolean validateEmail(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new EmailValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "email " + valid);
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
    private boolean validateIconPath(Part part) {
        boolean valid = true;
        if(part != null) {
                lineNotEmpty = true;
                valid = !part.getSubmittedFileName().isEmpty();
        }
        LOGGER.log(Level.DEBUG, "idconPath: " + valid);
        return valid;
    }
}


