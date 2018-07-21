package com.dk.gym.entity;

import java.time.LocalDate;

public class Prescription extends Entity {

    private int idOrder;
    private int idTrainer;
    private LocalDate date;
    private int weekQuantity;
    private int trainingsWeek;
    private String trainerNote;
    private String clientNote;
    private LocalDate agreedDate;

    public Prescription() {
    }

    public Prescription(int idOrder, int idTrainer, LocalDate date, int weekQuantity, int trainingsWeek, String trainerNote, String clientNote, LocalDate agreedDate) {
        this.idOrder = idOrder;
        this.idTrainer = idTrainer;
        this.date = date;
        this.weekQuantity = weekQuantity;
        this.trainingsWeek = trainingsWeek;
        this.trainerNote = trainerNote;
        this.clientNote = clientNote;
        this.agreedDate = agreedDate;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdTrainer() {
        return idTrainer;
    }

    public void setIdTrainer(int idTrainer) {
        this.idTrainer = idTrainer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getWeekQuantity() {
        return weekQuantity;
    }

    public void setWeekQuantity(int weekQuantity) {
        this.weekQuantity = weekQuantity;
    }

    public int getTrainingsWeek() {
        return trainingsWeek;
    }

    public void setTrainingsWeek(int trainingsWeek) {
        this.trainingsWeek = trainingsWeek;
    }

    public String getTrainerNote() {
        return trainerNote;
    }

    public void setTrainerNote(String trainerNote) {
        this.trainerNote = trainerNote;
    }

    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }

    public LocalDate getAgreedDate() {
        return agreedDate;
    }

    public void setAgreedDate(LocalDate agreedDate) {
        this.agreedDate = agreedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prescription that = (Prescription) o;

        if (idOrder != that.idOrder) return false;
        if (idTrainer != that.idTrainer) return false;
        if (weekQuantity != that.weekQuantity) return false;
        if (trainingsWeek != that.trainingsWeek) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (trainerNote != null ? !trainerNote.equals(that.trainerNote) : that.trainerNote != null) return false;
        if (clientNote != null ? !clientNote.equals(that.clientNote) : that.clientNote != null) return false;
        return agreedDate != null ? agreedDate.equals(that.agreedDate) : that.agreedDate == null;
    }

    @Override
    public int hashCode() {
        int result = idOrder;
        result = 31 * result + idTrainer;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + weekQuantity;
        result = 31 * result + trainingsWeek;
        result = 31 * result + (trainerNote != null ? trainerNote.hashCode() : 0);
        result = 31 * result + (clientNote != null ? clientNote.hashCode() : 0);
        result = 31 * result + (agreedDate != null ? agreedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "idOrder=" + idOrder +
                ", idTrainer=" + idTrainer +
                ", date=" + date +
                ", weekQuantity=" + weekQuantity +
                ", trainingsWeek=" + trainingsWeek +
                ", trainerNote='" + trainerNote + '\'' +
                ", clientNote='" + clientNote + '\'' +
                ", agreedDate=" + agreedDate +
                "} " + super.toString();
    }
}
