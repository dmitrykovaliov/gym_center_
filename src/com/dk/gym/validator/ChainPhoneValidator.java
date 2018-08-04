package com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.OnlyDigitsValidator;

public class ChainPhoneValidator {

    public boolean validate(String message) {

        AbstractValidator lengthValidator = new LengthValidator(11, 15);
        AbstractValidator onlyDigits = new OnlyDigitsValidator();
        lengthValidator.setNext(onlyDigits);

        return lengthValidator.validate(message);
    }
}
