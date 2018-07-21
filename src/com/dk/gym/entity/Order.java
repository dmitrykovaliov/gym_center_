package com.dk.gym.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order extends Entity {

    private int idOrder;
    private LocalDate date;
    private BigDecimal price;
    private int discount;
    private LocalDate closureDate;
    private String feedback;
    private int idClient;
    private int idActivity;

    public Order() {
    }

    public Order(int idOrder, LocalDate date, BigDecimal price, int discount, LocalDate closureDate, String feedback, int idClient, int idActivity) {
        this.idOrder = idOrder;
        this.date = date;
        this.price = price;
        this.discount = discount;
        this.closureDate = closureDate;
        this.feedback = feedback;
        this.idClient = idClient;
        this.idActivity = idActivity;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public LocalDate getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDate closureDate) {
        this.closureDate = closureDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (idOrder != order.idOrder) return false;
        if (discount != order.discount) return false;
        if (idClient != order.idClient) return false;
        if (idActivity != order.idActivity) return false;
        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (closureDate != null ? !closureDate.equals(order.closureDate) : order.closureDate != null) return false;
        return feedback != null ? feedback.equals(order.feedback) : order.feedback == null;
    }

    @Override
    public int hashCode() {
        int result = idOrder;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + discount;
        result = 31 * result + (closureDate != null ? closureDate.hashCode() : 0);
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + idClient;
        result = 31 * result + idActivity;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder=" + idOrder +
                ", date=" + date +
                ", price=" + price +
                ", discount=" + discount +
                ", closureDate=" + closureDate +
                ", feedback='" + feedback + '\'' +
                ", idClient=" + idClient +
                ", idActivity=" + idActivity +
                "} " + super.toString();
    }
}
