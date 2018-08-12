package com.dk.gym.validator;

public class LineNotEmptyValidator {

    public boolean validate(String ... message) {

        AbstractValidator notEmptyValidator = new NotEmptyValidator();

        for (String s : message) {

            if(notEmptyValidator.validate(s)) {
                return true;
            }
        }
        return false;
    }
}
