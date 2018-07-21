package com.dk.gym.validator;

public abstract class BaseValidator {

   private BaseValidator next;

    public BaseValidator() {
    }

    public void setNext(BaseValidator next) {
        this.next = next;
    }

    public BaseValidator getNext() {
        return next;
    }

    public abstract boolean validate(String message);

}
