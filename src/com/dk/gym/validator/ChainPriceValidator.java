package com.dk.gym.validator;

public class ChainPriceValidator extends BaseValidator {
    @Override
    public boolean validate(String message) {

        BaseValidator lengthValidator = new LengthValidator(0, 20);
        BaseValidator priceValidator = new PriceValidator();

        lengthValidator.setNext(priceValidator);

        return lengthValidator.validate(message);
    }
}
