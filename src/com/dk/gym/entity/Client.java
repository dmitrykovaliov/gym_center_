package com.dk.gym.entity;

public class Client extends Entity {

    private int idClient;
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String personalData;
    private String iconPath;
    private Integer idUser;

    public Client() {
    }

    public Client(int idClient, String name, String lastName, String phone, String email, String personalData,
                  String iconPath, Integer idUser) {
        this.idClient = idClient;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.personalData = personalData;
        this.iconPath = iconPath;
        this.idUser = idUser;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (idClient != client.idClient) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (lastName != null ? !lastName.equals(client.lastName) : client.lastName != null) return false;
        if (phone != null ? !phone.equals(client.phone) : client.phone != null) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (personalData != null ? !personalData.equals(client.personalData) : client.personalData != null)
            return false;
        if (iconPath != null ? !iconPath.equals(client.iconPath) : client.iconPath != null) return false;
        return idUser != null ? idUser.equals(client.idUser) : client.idUser == null;
    }

    @Override
    public int hashCode() {
        int result = idClient;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (personalData != null ? personalData.hashCode() : 0);
        result = 31 * result + (iconPath != null ? iconPath.hashCode() : 0);
        result = 31 * result + (idUser != null ? idUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", personalData='" + personalData + '\'' +
                ", iconPath='" + iconPath + '\'' +
                ", idUser=" + idUser +
                "} " + super.toString();
    }
}
