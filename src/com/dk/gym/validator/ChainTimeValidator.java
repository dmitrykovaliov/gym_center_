package com.dk.gym.validator;

import com.dk.gym.validator.AbstractValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.RangeValidator;
import com.dk.gym.validator.TimeValidator;

public class ChainTimeValidator {

    private static final String TIME_DELIMETER = ":";

    public boolean validate(String message) {
        AbstractValidator timeValidator = new TimeValidator();
        AbstractValidator lengthValidator = new LengthValidator(4, 5);

        AbstractValidator hoursValidator = new RangeValidator(0, 23);
        AbstractValidator minutesValidator = new RangeValidator(0, 59);

        timeValidator.setNext(lengthValidator);

        String[] time = message.split(":");
        String hours = time[0];
        String minutes = time[1];

        return timeValidator.validate(message)
                && hoursValidator.validate(hours)
                && minutesValidator.validate(minutes);
    }
}
