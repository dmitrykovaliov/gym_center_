package com.dk.gym.validator;


public class ChainIdValidator extends BaseValidator {

    @Override
    public boolean validate(String message) {

        BaseValidator notEmptyValidator = new NotEmptyValidator();
        BaseValidator idValidator = new OnlyDigitsValidator();
        BaseValidator rangeValidator = new RangeValidator(0, Integer.MAX_VALUE);

        notEmptyValidator.setNext(idValidator);
        idValidator.setNext(rangeValidator);

        return notEmptyValidator.validate(message);
    }

    public static void main(String[] args) {
        String login = "root";

        System.out.println(new ChainIdValidator().validate(login));
    }

}
