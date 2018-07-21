package com.dk.gym.validator;

public class ChainPhoneValidator extends BaseValidator {
    @Override
    public boolean validate(String message) {

        BaseValidator lengthValidator = new LengthValidator(11, 15);
        BaseValidator onlyDigits = new OnlyDigitsValidator();
        return new MailValidator().validate(message);
    }
}
