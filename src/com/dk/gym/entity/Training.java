package com.dk.gym.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Training extends Entity {

    private int idTraining;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean visited;
    private String clientNote;
    private String trainerNote;
    private int idOrder;
    private int idTrainer; //can be null;

    public Training() {
    }

    public Training(int idTraining, LocalDate date, LocalTime startTime, LocalTime endTime, boolean visited, String clientNote, String trainerNote, int idOrder, int idTrainer) {

        this.idTraining = idTraining;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.visited = visited;
        this.clientNote = clientNote;
        this.trainerNote = trainerNote;
        this.idOrder = idOrder;
        this.idTrainer = idTrainer;
    }

    public int getIdTraining() {
        return idTraining;
    }

    public void setIdTraining(int idTraining) {
        this.idTraining = idTraining;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getClientNote() {
        return clientNote;
    }

    public void setClientNote(String clientNote) {
        this.clientNote = clientNote;
    }

    public String getTrainerNote() {
        return trainerNote;
    }

    public void setTrainerNote(String trainerNote) {
        this.trainerNote = trainerNote;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Training training = (Training) o;

        if (idTraining != training.idTraining) return false;
        if (visited != training.visited) return false;
        if (idOrder != training.idOrder) return false;
        if (idTrainer != training.idTrainer) return false;
        if (date != null ? !date.equals(training.date) : training.date != null) return false;
        if (startTime != null ? !startTime.equals(training.startTime) : training.startTime != null) return false;
        if (endTime != null ? !endTime.equals(training.endTime) : training.endTime != null) return false;
        if (clientNote != null ? !clientNote.equals(training.clientNote) : training.clientNote != null) return false;
        return trainerNote != null ? trainerNote.equals(training.trainerNote) : training.trainerNote == null;
    }

    @Override
    public int hashCode() {
        int result = idTraining;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (visited ? 1 : 0);
        result = 31 * result + (clientNote != null ? clientNote.hashCode() : 0);
        result = 31 * result + (trainerNote != null ? trainerNote.hashCode() : 0);
        result = 31 * result + idOrder;
        result = 31 * result + idTrainer;
        return result;
    }

    @Override
    public String
    toString() {
        return "Training{" +
                "idTraining=" + idTraining +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", visited=" + visited +
                ", clientNote='" + clientNote + '\'' +
                ", trainerNote='" + trainerNote + '\'' +
                ", idOrder=" + idOrder +
                ", idTrainer=" + idTrainer +
                "} " + super.toString();
    }
}
