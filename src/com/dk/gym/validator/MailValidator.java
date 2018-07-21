package com.dk.gym.validator;

public class MailValidator extends BaseValidator {

    private static final String EMAIL_REGEX = "^.+@.+\\.\\w{2,6}$";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(EMAIL_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);

        }

        return isValid;
    }
}
