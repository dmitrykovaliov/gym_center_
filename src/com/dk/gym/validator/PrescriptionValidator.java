package com.dk.gym.validator;

import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.constant.ParamConstant.*;

public class PrescriptionValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public PrescriptionValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateDate(content.findParameter(PARAM_DATE));
        valid = valid && validateWeeks(content.findParameter(PARAM_WEEKS));
        valid = valid && validateTrainingsWeek(content.findParameter(PARAM_TRAININGS_WEEK));
        valid = valid && validateTrainerNote(content.findParameter(PARAM_TRAINER_NOTE));
        valid = valid && validateClientNote(content.findParameter(PARAM_CLIENT_NOTE));
        valid = valid && validateAgreedDate(content.findParameter(PARAM_AGREED));

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
    private boolean validateWeeks(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new RangeValidator(1, 48).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "weeks: " + valid);
        return valid;
    }
    private boolean validateTrainingsWeek(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new RangeValidator(1, 21).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "trainingsWeek: " + valid);
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
    private boolean validateClientNote(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 3000).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "clientNote: " + valid);
        return valid;
    }
    private boolean validateAgreedDate(String parameter) {
        boolean valid = true;
        if (notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainDateValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "agreedDate: " + valid);
        return valid;
    }
}


