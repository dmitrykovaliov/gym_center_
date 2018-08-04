package com.dk.gym.entity;

public class Trainer extends Entity {

    private int idTrainer;
    private String name;
    private String lastName;
    private String phone;
    private String personalData;
    private String iconPath;
    private int idUser;

    public Trainer() {
    }

    public Trainer(int idTrainer, String name, String lastName, String phone,
                   String personalData, String iconPath, int idUser) {
        this.idTrainer = idTrainer;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.personalData = personalData;
        this.iconPath = iconPath;
        this.idUser = idUser;
    }

    public int getIdTrainer() {
        return idTrainer;
    }

    public void setIdTrainer(int idTrainer) {
        this.idTrainer = idTrainer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPersonalData() {
        return personalData;
    }

    public void setPersonalData(String personalData) {
        this.personalData = personalData;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Trainer trainer = (Trainer) o;

        if (idTrainer != trainer.idTrainer) return false;
        if (idUser != trainer.idUser) return false;
        if (name != null ? !name.equals(trainer.name) : trainer.name != null) return false;
        if (lastName != null ? !lastName.equals(trainer.lastName) : trainer.lastName != null) return false;
        if (phone != null ? !phone.equals(trainer.phone) : trainer.phone != null) return false;
        if (personalData != null ? !personalData.equals(trainer.personalData) : trainer.personalData != null)
            return false;
        return iconPath != null ? iconPath.equals(trainer.iconPath) : trainer.iconPath == null;
    }

    @Override
    public int hashCode() {
        int result = idTrainer;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (personalData != null ? personalData.hashCode() : 0);
        result = 31 * result + (iconPath != null ? iconPath.hashCode() : 0);
        result = 31 * result + idUser;
        return result;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "idTrainer=" + idTrainer +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", personalData='" + personalData + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", idUser=" + idUser +
                "} " + super.toString();
    }
}