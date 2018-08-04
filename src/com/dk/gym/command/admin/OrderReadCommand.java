package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Activity;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.join.JoinOrder;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ActivityService;
import com.dk.gym.service.ClientService;
import com.dk.gym.service.OrderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.PageConstant.*;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_ACTIVITY;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_CLIENT;

public class OrderReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<JoinOrder> orderList;
        List<Client> clientList;
        List<Activity> activityList;

        try {
            orderList = OrderService.getInstance().findJoinItems();
            clientList = ClientService.getInstance().findItems();
            activityList = ActivityService.getInstance().findItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, orderList);
        content.insertAttribute(PARAM_READ_ALL_CLIENT, clientList);
        content.insertAttribute(PARAM_READ_ALL_ACTIVITY, activityList);

        LOGGER.log(Level.DEBUG, orderList);
        LOGGER.log(Level.DEBUG, clientList);
        LOGGER.log(Level.DEBUG, activityList);

        String pageUrl = PAGE_ORDER;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
