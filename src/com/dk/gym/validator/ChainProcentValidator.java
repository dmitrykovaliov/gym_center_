package com.dk.gym.validator;

public class ChainProcentValidator extends BaseValidator {
    @Override
    public boolean validate(String message) {
        BaseValidator lengthValidator = new LengthValidator(1, 3);
        BaseValidator onlyDigitsValidator = new OnlyDigitsValidator();
        lengthValidator.setNext(onlyDigitsValidator);

        return lengthValidator.validate(message);
    }
}
