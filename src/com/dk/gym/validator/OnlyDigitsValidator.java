package com.dk.gym.validator;

public class OnlyDigitsValidator extends BaseValidator {

    private static final String ONLY_DIGITS_REGEX = "^\\d*$";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(ONLY_DIGITS_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);

        }

        return isValid;
    }
}
