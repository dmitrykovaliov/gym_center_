package com.dk.gym.validator;

public class ChainTimeValidator extends BaseValidator {

    private static final String TIME_DELIMETER = ":";

    @Override
    public boolean validate(String message) {
        BaseValidator timeValidator = new TimeValidator();
        BaseValidator lengthValidator = new LengthValidator(5, 5);

        BaseValidator hoursValidator = new RangeValidator(0, 23);
        BaseValidator minutesValidator = new RangeValidator(0, 59);

        timeValidator.setNext(lengthValidator);

        String[] time = message.split(":");
        String hours = time[0];
        String minutes = time[1];

        return timeValidator.validate(message)
                && hoursValidator.validate(hours)
                && minutesValidator.validate(minutes);
    }

    public static void main(String[] args) {
        String message = "05:01";

        String[] time = message.split(TIME_DELIMETER);
        String hours = time[0];
        String minutes = time[1];

        System.out.println(hours);
        System.out.println(minutes);

        System.out.println(new ChainTimeValidator().validate("04:05"));

    }
}
