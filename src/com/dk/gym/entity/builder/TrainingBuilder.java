package com.dk.gym.entity.builder;

import com.dk.gym.entity.Training;
import com.dk.gym.validator.NotEmptyValidator;

import java.time.LocalDate;
import java.time.LocalTime;

class TrainingBuilder {

    private static final String DATE_DELIMETER = "[-./]";

    private Training training;

    public TrainingBuilder() {
        training = new Training();
    }
    public TrainingBuilder(Training training) {
        this.training = training;
    }

    public Training getTraining() {
        return training;
    }

    public void buildDate(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            String parameter1 = parameter.replaceAll(DATE_DELIMETER, "-");
            System.out.println(parameter1);
            training.setDate(LocalDate.parse(parameter1));
        }
    }
    public void buildStartTime(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setStartTime(LocalTime.parse(parameter));
        }
    }
    public void buildEndTime(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setEndTime(LocalTime.parse(parameter));
        }
    }
    public void buildVisited(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setVisited(Integer.parseInt(parameter) == 1);
        }
    }
    public void buildClientNote(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setClientNote(parameter);
        }
    }
    public void buildTrainerNote(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setTrainerNote(parameter);
        }
    }
    public void buildIdOrder(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setIdOrder(Integer.parseInt(parameter));
        }
    }
    public void buildIdTrainer(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            training.setIdTrainer(Integer.parseInt(parameter));
        }
    }

}

