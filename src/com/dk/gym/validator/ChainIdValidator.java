package com.dk.gym.validator;


public class ChainIdValidator {

    public boolean validate(String message) {

        AbstractValidator notEmptyValidator = new NotEmptyValidator(); //includes notEmptyValidator
        AbstractValidator idValidator = new OnlyDigitsValidator();
        AbstractValidator rangeValidator = new RangeValidator(1, Integer.MAX_VALUE); //SQL range

        notEmptyValidator.setNext(idValidator);
        idValidator.setNext(rangeValidator);

        return notEmptyValidator.validate(message);
    }

    public static void main(String[] args) {
        String login = "root";

        System.out.println(new ChainIdValidator().validate(login));
    }

}
