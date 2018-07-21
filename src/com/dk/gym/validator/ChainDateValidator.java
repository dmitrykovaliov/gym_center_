package com.dk.gym.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DateTimeException;
import java.time.LocalDate;

public class ChainDateValidator extends BaseValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DATE_DELIMETER = "[-./]";

    @Override
    public boolean validate(String message) {

        boolean valid;

        BaseValidator dateValidator = new DateValidator();
        BaseValidator lengthValidator = new LengthValidator(10, 10);

        BaseValidator yearValidator = new RangeValidator(1950, 2025);
        BaseValidator monthValidator = new RangeValidator(1, 12);
        BaseValidator dayValidator = new RangeValidator(1, 31);

        dateValidator.setNext(lengthValidator);

        String[] date = message.split(DATE_DELIMETER);

        String year = date[0];
        String month = date[1];
        String day = date[2];

        valid = dateValidator.validate(message)
                && yearValidator.validate(year)
                && monthValidator.validate(month)
                && dayValidator.validate(day);

        if (valid) {
            try {
                LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            } catch (DateTimeException e) {
                LOGGER.log(Level.ERROR, e);
                valid = false;
            }
        }
        return valid;
    }

    public static void main(String[] args) {
        String message = "2023/02/29";

        System.out.println(new ChainDateValidator().validate(message));

    }
}
