package com.dk.gym.command;

import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.resource.LocaleManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;

import static com.dk.gym.service.ParamConstant.PARAM_LOCALE;
import static com.dk.gym.service.ParamConstant.PARAM_URL_QUERY;

public class LocaleRuCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) {

        content.insertSessionAttribute(PARAM_LOCALE, "ru_RU");
        LocaleManager.changeResource(new Locale("ru", "RU"));


        String pageUrl = (String) content.findSessionAttribute(PARAM_URL_QUERY);
        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.REDIRECT, pageUrl);
    }
}
