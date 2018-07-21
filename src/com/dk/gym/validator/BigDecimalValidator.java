package com.dk.gym.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class BigDecimalValidator extends BaseValidator {

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

    public static void main(String[] args) { //  todo delete this method

        String str = "25";

        System.out.println(new BigDecimalValidator().validate(str));

        System.out.println(System.getProperty("java.class.path"));
    }
}


