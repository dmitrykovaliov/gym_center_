package com.dk.gym.entity;

import java.math.BigDecimal;

public class Activity extends Entity {

    private int idActivity;
    private String name;
    private BigDecimal price;
    private String note;

    public Activity() {
    }

    public Activity(int idActivity, String name, BigDecimal price, String note) {
        this.idActivity = idActivity;
        this.name = name;
        this.price = price;
        this.note = note;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (idActivity != activity.idActivity) return false;
        if (name != null ? !name.equals(activity.name) : activity.name != null) return false;
        if (price != null ? !price.equals(activity.price) : activity.price != null) return false;
        return note != null ? note.equals(activity.note) : activity.note == null;
    }

    @Override
    public int hashCode() {
        int result = idActivity;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "idActivity=" + idActivity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", note='" + note + '\'' +
                "} " + super.toString();
    }
}
