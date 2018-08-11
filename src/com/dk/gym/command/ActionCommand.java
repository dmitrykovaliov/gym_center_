package com.dk.gym.command;

import com.dk.gym.exception.CommandException;
import com.dk.gym.controller.SessionRequestContent;

public interface ActionCommand {
    RouterPage execute(SessionRequestContent content) throws CommandException;
}
