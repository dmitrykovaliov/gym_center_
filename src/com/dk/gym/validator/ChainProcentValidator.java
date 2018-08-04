package com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.OnlyDigitsValidator;
import com.dk.gym.validator.RangeValidator;

public class ChainProcentValidator {

    public boolean validate(String message) {

        AbstractValidator onlyDigitsValidator = new OnlyDigitsValidator();
        AbstractValidator rangeValidator = new RangeValidator(0, 100);

        onlyDigitsValidator.setNext(rangeValidator);

        return onlyDigitsValidator.validate(message);
    }
}
