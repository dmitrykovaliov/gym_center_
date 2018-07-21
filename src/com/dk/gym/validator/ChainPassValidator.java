package com.dk.gym.validator;


public class ChainPassValidator extends BaseValidator {

    @Override
    public boolean validate(String pass) {

        BaseValidator passValidator = new LengthValidator(8, 20);
        BaseValidator containsDigit = new ContainsDigitValidator();
        passValidator.setNext(containsDigit);
        BaseValidator containsCapitalLetter = new ContainsCapitalValidator();
        containsDigit.setNext(containsCapitalLetter);

        return passValidator.validate(pass);
    }

    public static void main(String[] args) {//todo
        String pass = "123123121R";

        System.out.println(new ChainPassValidator().validate(pass));
    }

}
