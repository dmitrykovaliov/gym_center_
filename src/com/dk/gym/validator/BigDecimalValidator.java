package com.dk.gym.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BigDecimalValidator extends AbstractValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        BigDecimal bigDecimal;

        try {
            bigDecimal = new BigDecimal(message);

            int signum = bigDecimal.signum();

            isValid = signum != -1;

        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, e);
        }

        return isValid;
    }
}


