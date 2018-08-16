package com.dk.gym.command.factory;

import com.dk.gym.command.ReturnMessageType;

/**
 * A factory for creating ReturnMessage objects.
 */
public class ReturnMessageFactory {

    /**
     * Define message.
     *
     * @param message the message
     * @return the string
     */
    public String defineMessage(ReturnMessageType message) {

        if (message != null) {
            return message.getProperty();
        }
        return "message.error";
    }
}

