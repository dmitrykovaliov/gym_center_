package com.dk.gym.validator;

public class PriceValidator extends BaseValidator {

    private static final String PRICE_REGEX = "(^\\d+[,.]\\d{2}$)|(^\\d*[,.]\\d$)|(^\\d+$)";


    @Override
    public boolean validate(String message) {
        boolean isValid = false;

        if(message.matches(PRICE_REGEX)) {

            isValid = getNext() == null || getNext().validate(message);

        }

        return isValid;
    }
}
