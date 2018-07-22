package com.dk.gym.command;

import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.servlet.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;
import java.util.Locale;
import static com.dk.gym.constant.ParamConstant.*;

public class RuCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) {

        content.insertSessionAttribute(PARAM_LOCALE, "ru_RU");
        LocaleManager.changeResource(new Locale("ru", "RU"));

        String pageUrl = (String) content.findSessionAttribute(ATTR_URL_QUERY);
        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.REDIRECT, pageUrl);
    }
}
