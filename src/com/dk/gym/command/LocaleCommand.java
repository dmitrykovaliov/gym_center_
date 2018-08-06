package com.dk.gym.command;

import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dk.gym.resource.LocaleManager;
import java.util.Locale;
import static com.dk.gym.service.ParamConstant.*;

public class LocaleCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) {

        if(content.findSessionAttribute(PARAM_LOCALE) == "ru_RU") {
            content.insertSessionAttribute(PARAM_LOCALE, "");
            LocaleManager.changeResource(Locale.getDefault());
        } else {
            content.insertSessionAttribute(PARAM_LOCALE, "ru_RU");
            LocaleManager.changeResource(new Locale("ru", "RU"));
        }

        String pageUrl = (String) content.findSessionAttribute(ATTR_URL_QUERY);
        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.REDIRECT, pageUrl);
    }
}
