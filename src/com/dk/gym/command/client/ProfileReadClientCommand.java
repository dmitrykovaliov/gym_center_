package com.dk.gym.command.client;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.PageConstant;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Client;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ClientService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.service.ParamConstant.PARAM_READ;

public class ProfileReadClientCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        Client client;

        try {
            client = ClientService.getInstance().findClientItem(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ, client);

        LOGGER.log(Level.DEBUG, client);

        String pageUrl = PageConstant.PAGE_CLIENT_PROFILE;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
