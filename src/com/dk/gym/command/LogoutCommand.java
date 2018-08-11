package com.dk.gym.command;

import com.dk.gym.controller.SessionRequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) {

        String pageUrl = PageConstant.PAGE_INDEX;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.REDIRECT, pageUrl);
    }
}
