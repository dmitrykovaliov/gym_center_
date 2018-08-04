package com.dk.gym.entity.join;

import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;

public class JoinPrescription {
    private Prescription prescription;
    private Trainer trainer;

    public JoinPrescription() {
        prescription = new Prescription();
        trainer = new Trainer();
    }

    public JoinPrescription(Prescription prescription, Trainer trainer) {
        this.prescription = prescription;
        this.trainer = trainer;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JoinPrescription that = (JoinPrescription) o;

        if (prescription != null ? !prescription.equals(that.prescription) : that.prescription != null) return false;
        return trainer != null ? trainer.equals(that.trainer) : that.trainer == null;
    }

    @Override
    public int hashCode() {
        int result = prescription != null ? prescription.hashCode() : 0;
        result = 31 * result + (trainer != null ? trainer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JoinPrescription{" +
                "prescription=" + prescription +
                ", trainer=" + trainer +
                '}';
    }
}
