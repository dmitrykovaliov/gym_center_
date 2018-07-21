package com.dk.gym.command;

import com.dk.gym.exception.CommandException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.servlet.RequestContent;

public interface ActionCommand {
    ContentPage execute(RequestContent content) throws CommandException;
}
