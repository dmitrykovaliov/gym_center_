package com.dk.gym.entity.builder;

import com.dk.gym.entity.Prescription;
import com.dk.gym.validator.NotEmptyValidator;

import java.time.LocalDate;

class PrescriptionBuilder {

    private Prescription prescription;

    public PrescriptionBuilder() {
        prescription = new Prescription();
    }
    public PrescriptionBuilder(Prescription prescription) {
        this.prescription = prescription;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void buildDate(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setDate(LocalDate.parse(parameter));
        }
    }
    public void buildWeekQuantity(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setWeekQuantity(Integer.parseInt(parameter));
        }
    }
    public void buildTrainingsWeek(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setTrainingsWeek(Integer.parseInt(parameter));
        }
    }
    public void buildTrainerNote(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setTrainerNote(parameter);
        }
    }
    public void buildClientNote(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setClientNote(parameter);
        }
    }
    public void buildAgreedDate(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            prescription.setAgreedDate(LocalDate.parse(parameter));
        }
    }

}

