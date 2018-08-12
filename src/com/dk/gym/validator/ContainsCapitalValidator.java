package com.dk.gym.validator;

public class ContainsCapitalValidator extends AbstractValidator {

    private static final String CAPITAL_REGEX = ".*[A-Z].*";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(CAPITAL_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);

        }

        return isValid;
    }
}
