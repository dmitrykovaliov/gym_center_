package com.dk.gym.command.factory;

import com.dk.gym.command.ReturnMessageType;

public class ReturnMessageFactory {

    public String defineMessage(ReturnMessageType message) {

        if (message != null) {
            return message.getProperty();
        }

        return "message.error";
    }
}

