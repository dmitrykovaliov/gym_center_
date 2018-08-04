package com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.PriceValidator;

public class ChainPriceValidator {

    public boolean validate(String message) {

        AbstractValidator lengthValidator = new LengthValidator(0, 20);
        AbstractValidator priceValidator = new PriceValidator();

        lengthValidator.setNext(priceValidator);

        return lengthValidator.validate(message);
    }
}
