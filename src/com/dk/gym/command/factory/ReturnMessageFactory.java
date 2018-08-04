package com.dk.gym.command.factory;

import com.dk.gym.command.ReturnMessageType;

public class ReturnMessageFactory {

    public String defineMessage(ReturnMessageType message) {

        String current = "message.error";

        if (message == null) {
            return current;
        }

        current = message.getProperty();

        return current;
    }
}

