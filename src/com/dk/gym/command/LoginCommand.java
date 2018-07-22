package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.impl.UserServiceImpl;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;
import static com.dk.gym.constant.CommonConstant.*;
import static com.dk.gym.constant.ParamConstant.*;

public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        String pageUrl = PageConstant.PAGE_INDEX;
        String message;

        String login = content.findParameter(PARAM_LOGIN);
        String pass = content.findParameter(PARAM_PASS);

        LOGGER.log(Level.DEBUG, String.format("Login: %s, pass: %s", login, pass));

        if (new LineNotEmptyValidator().validate(login, pass)) {

            try {
                message = UserServiceImpl.getInstance().checkLogin(login, pass);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            if (LocaleManager.getProperty("message.error.invalid").equals(message)) {
                content.insertSessionAttribute(PARAM_ERROR,
                        LocaleManager.getProperty("message.error.invalid"));
            } else if (EMPTY_MESSAGE.equals(message)) {
                content.insertSessionAttribute(PARAM_ERROR,
                        LocaleManager.getProperty("message.info.noUser"));
            } else {
                content.insertSessionAttribute(PARAM_USER, message);
                pageUrl = PageConstant.PAGE_MAIN;
            }
        } else {
            content.insertSessionAttribute(PARAM_ERROR,
                    LocaleManager.getProperty("message.info.emptyLine"));
        }

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.REDIRECT, pageUrl);
    }
}
