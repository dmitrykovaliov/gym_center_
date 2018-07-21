package com.dk.gym.validator;

public class TimeValidator extends BaseValidator {

    private static final String TIME_REGEX = "^\\d{1,2}[:]\\d{2}$";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(TIME_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);
        }

        return isValid;
    }
}
