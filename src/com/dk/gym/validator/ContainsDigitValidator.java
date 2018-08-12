package com.dk.gym.validator;

public class ContainsDigitValidator extends AbstractValidator {

    private static final String DIGIT_REGEX = ".*\\d.*";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(DIGIT_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);

        }

        return isValid;
    }
}
