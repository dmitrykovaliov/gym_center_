package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.command.factory.ReturnMessageFactory;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.resource.LocaleManager;
import com.dk.gym.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.command.PageConstant.PAGE_USER_READ;
import static com.dk.gym.service.ParamConstant.PARAM_ERROR;

public class UserDeleteCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        ReturnMessageType message;

        try {
            message = UserService.getInstance().deleteItem(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_ERROR, LocaleManager
                .getProperty(new ReturnMessageFactory().defineMessage(message)));

        String pageUrl = PAGE_USER_READ;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
