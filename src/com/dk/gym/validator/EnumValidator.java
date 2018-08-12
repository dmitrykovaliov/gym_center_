package com.dk.gym.validator;

import java.util.Arrays;

import static java.util.stream.Collectors.toSet;

public class EnumValidator {

    public boolean validate(Class<? extends Enum<?>> aEnum, String parameter) {

        if(parameter != null && !parameter.trim().isEmpty()) {

            Enum[] list = aEnum.getEnumConstants();

            return Arrays.stream(list)
                    .map(Enum::toString)
                    .collect(toSet())
                    .contains(parameter.trim().toUpperCase());
        }

        return false;
    }
}
