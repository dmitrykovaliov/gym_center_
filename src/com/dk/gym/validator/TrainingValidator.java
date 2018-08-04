package com.dk.gym.validator;

import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.constant.ParamConstant.*;

public class TrainingValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TRUE_FIGURE = "1";
    private static final String FALSE_FIGURE = "0";

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public TrainingValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateDate(content.findParameter(PARAM_DATE));
        valid = valid && validateStartTime(content.findParameter(PARAM_START_TIME));
        valid = valid && validateEndTime(content.findParameter(PARAM_END_TIME));
        valid = valid && validateVisited(content.findParameter(PARAM_VISITED));
        valid = valid && validateClientNote(content.findParameter(PARAM_CLIENT_NOTE));
        valid = valid && validateTrainerNote(content.findParameter(PARAM_TRAINER_NOTE));
        valid = valid && validateOrderId(content.findParameter(PARAM_ORDER_ID));

        return lineNotEmpty && valid;
    }

    private boolean validateDate(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainDateValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "date: " + valid);
        return valid;
    }

    private boolean validateStartTime(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainTimeValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "startTime: " + valid);
        return valid;
    }

    private boolean validateEndTime(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainTimeValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "endTime: " + valid);
        return valid;
    }

    private boolean validateVisited(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = TRUE_FIGURE.equals(parameter) || FALSE_FIGURE.equals(parameter);
        }
        LOGGER.log(Level.DEBUG, "visited: " + valid);
        return valid;
    }

    private boolean validateClientNote(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 3000).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "clientNote: " + valid);
        return valid;
    }

    private boolean validateTrainerNote(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 3000).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "trainerNote: " + valid);
        return valid;
    }
    private boolean validateOrderId(String parameter) {
        boolean valid = false;
        if (notEmptyValidator.validate(parameter)) {
            valid = new ChainIdValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "orderId: " + valid);
        return valid;
    }
}


