package com.dk.gym.validator;

import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.service.ParamConstant.*;

public class OrderValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private NotEmptyValidator notEmptyValidator;

    private boolean lineNotEmpty;

    public OrderValidator() {
        this.notEmptyValidator = new NotEmptyValidator();
    }

    public boolean validate(RequestContent content) {
        boolean valid;

        valid = validateDate(content.findParameter(PARAM_DATE));
        valid = valid && validatePrice(content.findParameter(PARAM_PRICE));
        valid = valid && validateDiscount(content.findParameter(PARAM_DISCOUNT));
        valid = valid && validateClosureDate(content.findParameter(PARAM_CLOSURE));
        valid = valid && validateFeedback(content.findParameter(PARAM_FEEDBACK));

        return lineNotEmpty && valid;
    }

    private boolean validateDate(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainDateValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "date: " + valid);
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
    private boolean validateDiscount(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new RangeValidator(0, 100).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "discount: " + valid);
        return valid;
    }
    private boolean validateClosureDate(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new ChainDateValidator().validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "closureDate: " + valid);
        return valid;
    }
    private boolean validateFeedback(String parameter) {
        boolean valid = true;
        if(notEmptyValidator.validate(parameter)) {
            lineNotEmpty = true;
            valid = new LengthValidator(1, 5000).validate(parameter);
        }
        LOGGER.log(Level.DEBUG, "feedback: " + valid);
        return valid;
    }
}


