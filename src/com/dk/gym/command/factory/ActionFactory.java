package com.dk.gym.command.factory;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.CommandType;
import com.dk.gym.command.EmptyCommand;
import com.dk.gym.servlet.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    public ActionCommand defineCommand(RequestContent content) {

        ActionCommand current = new EmptyCommand();
        String action = content.findParameter("command");

        LOGGER.log(Level.DEBUG, "Command: " + action);

        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
            current = currentEnum.getCommand();

            LOGGER.log(Level.DEBUG, "enum: " + currentEnum);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.ERROR, "Can't define command: " + e);
        }
        return current;
    }
}

