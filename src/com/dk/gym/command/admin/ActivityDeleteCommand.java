package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.RouterPage;
import com.dk.gym.command.Router;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.command.factory.ReturnMessageFactory;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ActivityService;
import com.dk.gym.controller.SessionRequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dk.gym.resource.LocaleManager;

import static com.dk.gym.command.PageConstant.PAGE_READ_ACTIVITY;
import static com.dk.gym.service.ParamConstant.PARAM_ERROR;

/**
 * The Class ActivityDeleteCommand.
 */
public class ActivityDeleteCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) throws CommandException {

        ReturnMessageType message;

        try {
            message = ActivityService.getInstance().deleteActivity(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_ERROR, LocaleManager
                .getProperty(new ReturnMessageFactory().defineMessage(message)));

        String pageUrl = PAGE_READ_ACTIVITY;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
