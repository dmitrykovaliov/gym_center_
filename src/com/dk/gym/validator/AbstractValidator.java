package com.dk.gym.validator;

public abstract class AbstractValidator {

   private AbstractValidator next;

    public AbstractValidator() {
    }

    public void setNext(AbstractValidator next) {
        this.next = next;
    }

    public AbstractValidator getNext() {
        return next;
    }

    public abstract boolean validate(String message);

}
