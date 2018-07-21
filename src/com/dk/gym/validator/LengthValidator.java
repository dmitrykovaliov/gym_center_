package com.dk.gym.validator;

public class LengthValidator extends BaseValidator {

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

    public static void main(String[] args) {
        String message = "sdf453=-0";
        System.out.println(new LengthValidator(10, 20).validate(message));
    }
}
