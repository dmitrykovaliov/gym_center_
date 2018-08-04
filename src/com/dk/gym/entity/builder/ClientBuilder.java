package com.dk.gym.entity.builder;

import com.dk.gym.entity.Client;
import com.dk.gym.entity.Role;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.NotEmptyValidator;

class ClientBuilder {

    private Client client;

    public ClientBuilder() {
        client = new Client();
    }

    public ClientBuilder(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void buildName(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setName(parameter);
        }
    }
    public void buildLastName(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setLastName(parameter);
        }
    }
    public void buildPhone(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setPhone(parameter);
        }
    }
    public void buildEmail(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setEmail(parameter);
        }
    }
    public void buildPersonalData(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setPersonalData(parameter);
        }
    }
    public void buildPicPath(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            client.setIconPath(parameter);
        }
    }
}

