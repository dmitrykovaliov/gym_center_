package com.dk.gym.command;

public enum ReturnMessageType {
    DONE("message.info.done"),
    ENTER_ERROR("message.error.enter"),
    USER_NOT_EXIST("message.info.userNotExist"),
    USER_EXIST("message.info.userExist"),
    INVALID("message.error.invalid");

    private String property;

    ReturnMessageType(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }
}
