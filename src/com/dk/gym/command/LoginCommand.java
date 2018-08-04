package com.dk.gym.command;

import com.dk.gym.command.factory.ReturnMessageFactory;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.UserService;
import com.dk.gym.controller.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dk.gym.resource.LocaleManager;

import static com.dk.gym.constant.PageConstant.*;
import static com.dk.gym.constant.ParamConstant.*;

public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        ReturnMessageType message;
        String pageUrl = PAGE_INDEX;


        try {
            message = UserService.getInstance().checkUser(content);
            LOGGER.log(Level.DEBUG, message);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertSessionAttribute(PARAM_ERROR, LocaleManager
                .getProperty(new ReturnMessageFactory().defineMessage(message)));

        if(content.findSessionAttribute(PARAM_ROLE)!= null) {
            pageUrl = PAGE_MAIN;
        }

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.REDIRECT, pageUrl);
    }
}
