package com.dk.gym.command;

/**
 * The Enum ReturnMessageType.
 */
public enum ReturnMessageType {

    DONE("message.info.done"),
    ENTER_ERROR("message.error.enter"),
    USER_NOT_EXIST("message.info.userNotExist"),
    USER_EXIST("message.info.userExist"),
    INVALID("message.error.invalid");

    /** The property. */
    private String property;

    /**
     * Instantiates a new return message type.
     *
     * @param property the property
     */
    ReturnMessageType(String property) {
        this.property = property;
    }

    /**
     * Gets the property.
     *
     * @return the property
     */
    public String getProperty() {
        return property;
    }
}
