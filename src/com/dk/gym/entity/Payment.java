package com.dk.gym.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment extends Entity {

    private int idPayment;
    private LocalDate date;
    private BigDecimal amount;
    private String note;
    private int idOrder;

    public Payment() {
    }

    public Payment(int idPayment, LocalDate date, BigDecimal amount, String note, int idOrder) {
        this.idPayment = idPayment;
        this.date = date;
        this.amount = amount;
        this.note = note;
        this.idOrder = idOrder;
    }

    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (idPayment != payment.idPayment) return false;
        if (idOrder != payment.idOrder) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        if (amount != null ? !amount.equals(payment.amount) : payment.amount != null) return false;
        return note != null ? note.equals(payment.note) : payment.note == null;
    }

    @Override
    public int hashCode() {
        int result = idPayment;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + idOrder;
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "idPayment=" + idPayment +
                ", date=" + date +
                ", amount=" + amount +
                ", note='" + note + '\'' +
                ", idOrder=" + idOrder +
                "} " + super.toString();
    }
}
