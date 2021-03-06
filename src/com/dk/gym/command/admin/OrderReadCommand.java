package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.RouterPage;
import com.dk.gym.command.Router;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ActivityService;
import com.dk.gym.service.ClientService;
import com.dk.gym.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.PageConstant.*;
import static com.dk.gym.service.ParamConstant.*;

/**
 * The Class OrderReadCommand.
 */
public class OrderReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) throws CommandException {

        List<Order> orderList;
        List<Client> clientList;
        List<Activity> activityList;

        List<Client> clientAllList;
        List<Activity> activityAllList;

        try {
            orderList = OrderService.getInstance().findAllOrder();
            clientList = OrderService.getInstance().findRelatedAllClient();
            activityList = OrderService.getInstance().findRelatedAllActivity();

            clientAllList = ClientService.getInstance().findAllClient();
            activityAllList = ActivityService.getInstance().findAllActivity();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, orderList);
        content.insertAttribute(PARAM_READ_CLIENT, clientList);
        content.insertAttribute(PARAM_READ_ACTIVITY, activityList);
        content.insertAttribute(PARAM_READ_ALL_CLIENT, clientAllList);
        content.insertAttribute(PARAM_READ_ALL_ACTIVITY, activityAllList);

        LOGGER.log(Level.DEBUG, orderList);
        LOGGER.log(Level.DEBUG, clientList);
        LOGGER.log(Level.DEBUG, activityList);

        String pageUrl = PAGE_ORDER;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
