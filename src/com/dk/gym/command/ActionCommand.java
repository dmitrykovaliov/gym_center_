package com.dk.gym.command;

import com.dk.gym.exception.CommandException;
import com.dk.gym.controller.SessionRequestContent;

/**
 * The Interface ActionCommand.
 */
public interface ActionCommand {
    
    /**
     * Execute.
     *
     * @param content the content
     * @return the router page
     * @throws CommandException the command exception
     */
    RouterPage execute(SessionRequestContent content) throws CommandException;
}
