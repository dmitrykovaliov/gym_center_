package com.dk.gym.command.trainer;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.RouterPage;
import com.dk.gym.command.PageConstant;
import com.dk.gym.command.Router;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ActivityService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.service.ParamConstant.PARAM_READ_ALL;

/**
 * The Class ActivityReadTrainerCommand.
 */
public class ActivityReadTrainerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) throws CommandException {

        List<Activity> itemList;

        try {
            itemList = ActivityService.getInstance().findAllActivity();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, itemList);

        LOGGER.log(Level.DEBUG, itemList);

        String pageUrl = PageConstant.PAGE_GENERAL_ACTIVITY;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
