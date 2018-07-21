package com.dk.gym.validator;

public class NotEmptyValidator extends BaseValidator {


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message != null && !message.trim().isEmpty()) {

            isValid = getNext() == null || getNext().validate(message);
        }

        return isValid;
    }
}
