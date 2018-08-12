package com.dk.gym.entity;

public class User implements Entity {
    private int idUser;
    private String login;
    private String pass;
    private Role role;

    public User() {
    }

    public User(int idUser, String login, String pass, Role role) {
        this.idUser = idUser;
        this.login = login;
        this.pass = pass;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (idUser != user.idUser) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (pass != null ? !pass.equals(user.pass) : user.pass != null) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = idUser;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", pass='" + pass + '\'' +
                ", role=" + role +
                '}';
    }
}
