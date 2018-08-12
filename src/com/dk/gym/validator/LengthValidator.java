package com.dk.gym.validator;

public class LengthValidator extends AbstractValidator {

    private int minLength;
    private int maxLength;

    public LengthValidator(int minLength, int maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        int length = message.length();

        if(length>=minLength && length <= maxLength) {
            isValid = getNext() == null || getNext().validate(message);
        }

        return isValid;
    }
}
