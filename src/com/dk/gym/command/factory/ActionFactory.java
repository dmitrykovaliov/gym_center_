package com.dk.gym.command.factory;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.CommandType;
import com.dk.gym.command.general.EmptyCommand;
import com.dk.gym.controller.SessionRequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A factory for creating Action objects.
 */
public class ActionFactory {
    
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Define command.
     *
     * @param content the content
     * @return the action command
     */
    public ActionCommand defineCommand(SessionRequestContent content) {

        String action = content.findParameter("command");
        LOGGER.log(Level.DEBUG, "Command: " + action);

        if (action != null && !action.isEmpty()) {
            try {
                CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
                LOGGER.log(Level.DEBUG, "enum: " + currentEnum);

                return currentEnum.getCommand();

            } catch (IllegalArgumentException e) {
                LOGGER.log(Level.ERROR, "Command not defined: " + e);
            }
        }
        return new EmptyCommand();
    }
}

