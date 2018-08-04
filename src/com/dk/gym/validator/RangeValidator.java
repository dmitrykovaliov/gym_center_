package com.dk.gym.validator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RangeValidator extends AbstractValidator {

    private static final Logger LOGGER = LogManager.getLogger();

    private int min;
    private int max;

    public RangeValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        int number = 0;
        try {
            number = Integer.parseInt(message);
            LOGGER.log(Level.INFO, number);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, e);
        }

        if(number>=min && number <= max) {
            isValid = getNext() == null || getNext().validate(message);
        }

        return isValid;
    }

    public static void main(String[] args) { //todo delete main
        String message = "015";
        System.out.println(new RangeValidator(15, 20).validate(message));
    }
}
