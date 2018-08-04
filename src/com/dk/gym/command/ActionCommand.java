package com.dk.gym.command;

import com.dk.gym.exception.CommandException;
import com.dk.gym.controller.RequestContent;

public interface ActionCommand {
    ContentPage execute(RequestContent content) throws CommandException;
}
