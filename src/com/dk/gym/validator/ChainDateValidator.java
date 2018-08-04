package com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.DateValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.RangeValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;

public class ChainDateValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATE_DELIMETER = "[-./]";

    public boolean validate(String message) {

        boolean valid;

        AbstractValidator dateValidator = new DateValidator();
        AbstractValidator lengthValidator = new LengthValidator(10, 10);
        AbstractValidator yearValidator = new RangeValidator(1950, 2025);

        dateValidator.setNext(lengthValidator);

        String[] date = message.split(DATE_DELIMETER);

        String year = null;
        String month = null;
        String day = null;

        try {
            year = date[0];
            month = date[1];
            day = date[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.ERROR, e);
        }

        valid = dateValidator.validate(message)
                && yearValidator.validate(year);

        if (valid) {
            try {
                LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            } catch (NumberFormatException | DateTimeException e) {
                LOGGER.log(Level.ERROR, e);
                valid = false;
            }
        }
        return valid;
    }
}
