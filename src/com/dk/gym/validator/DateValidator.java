package com.dk.gym.validator;

public class DateValidator extends AbstractValidator {

    private static final String DATE_REGEX = "^\\d{4}[-./]\\d{2}[-./]\\d{2}$";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(DATE_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);
        }

        return isValid;
    }
}
