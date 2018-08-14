package com.dk.gym.command;

import com.dk.gym.controller.SessionRequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dk.gym.resource.LocaleManager;

import java.util.Locale;

import static com.dk.gym.service.ParamConstant.*;

public class LocaleEnCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String EMPTY_PARAMETER = "";

    @Override
    public RouterPage execute(SessionRequestContent content) {

        content.insertSessionAttribute(PARAM_LOCALE, EMPTY_PARAMETER);
        LocaleManager.changeResource(Locale.getDefault());


        String pageUrl = (String) content.findSessionAttribute(PARAM_URL_QUERY);
        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.REDIRECT, pageUrl);
    }
}
