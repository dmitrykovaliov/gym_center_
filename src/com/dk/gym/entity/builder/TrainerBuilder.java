package com.dk.gym.entity.builder;

import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Role;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.NotEmptyValidator;

class TrainerBuilder {

    private Trainer trainer;

    public TrainerBuilder() {
        trainer = new Trainer();
    }

    public TrainerBuilder(Trainer trainer) {
        this.trainer = trainer;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void buildName(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            trainer.setName(parameter);
        }
    }
    public void buildLastName(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            trainer.setLastName(parameter);
        }
    }
    public void buildPhone(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            trainer.setPhone(parameter);
        }
    }
    public void buildPersonalData(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            trainer.setPersonalData(parameter);
        }
    }
    public void buildPicPath(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            trainer.setIconPath(parameter);
        }
    }
}

