package com.dk.gym.command.client;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Order;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.command.PageConstant.PAGE_CLIENT_ORDER;
import static com.dk.gym.service.ParamConstant.*;

public class OrderReadClientCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<Order> orderList;
        List<Activity> activityList;

        try {
            orderList = OrderService.getInstance().findClientOrders(content);
            activityList = OrderService.getInstance().findActivitiesByClient(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        content.insertAttribute(PARAM_READ_ALL, orderList);
        content.insertAttribute(PARAM_READ_ACTIVITY, activityList);

        LOGGER.log(Level.DEBUG, orderList);
        LOGGER.log(Level.DEBUG, activityList);

        String pageUrl = PAGE_CLIENT_ORDER;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}