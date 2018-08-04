package com.dk.gym.entity.builder;

import com.dk.gym.entity.User;
import com.dk.gym.entity.Role;
import com.dk.gym.util.CryptPass;
import com.dk.gym.validator.NotEmptyValidator;

class UserBuilder {

    private User user;

    public UserBuilder() {
        user = new User();
    }

    public UserBuilder(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void buildLogin(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            user.setLogin(parameter);
        }
    }

    public void buildPass(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            user.setPass(CryptPass.cryptSha(parameter));
        }
    }

    public void buildRole(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            user.setRole(Role.valueOf(parameter.trim().toUpperCase()));
        }
    }
}

