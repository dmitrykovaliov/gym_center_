package com.dk.gym.validator;

public class EnumValidator {

    public boolean validate(Class<? extends Enum<?>> aEnum, String parameter) {
        boolean valid = false;

        Enum[] list = aEnum.getEnumConstants();

        if(parameter != null) {
            for (Enum e : list) {
                if (e.toString().equalsIgnoreCase(parameter.trim())) {
                    valid = true;
                    break;
                }
            }
        }
        return valid;
    }
}
