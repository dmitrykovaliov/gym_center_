package com.dk.gym.validator;


public class ChainPassValidator {

    public boolean validate(String pass) {

        AbstractValidator lengthValidator = new LengthValidator(8, 20);
        AbstractValidator containsDigit = new ContainsDigitValidator();
        AbstractValidator containsCapitalLetter = new ContainsCapitalValidator();

        lengthValidator.setNext(containsDigit);
        containsDigit.setNext(containsCapitalLetter);

        return lengthValidator.validate(pass);
    }

    public static void main(String[] args) {//todo
        String pass = "123123121R";

        System.out.println(new ChainPassValidator().validate(pass));
    }

}
