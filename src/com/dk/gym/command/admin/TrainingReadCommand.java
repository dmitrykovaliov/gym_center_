package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.constant.PageConstant;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.join.JoinTraining;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import com.dk.gym.service.TrainerService;
import com.dk.gym.service.TrainingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_ORDER;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_TRAINER;

public class TrainingReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<JoinTraining> trainingList;
        List<Order> orderList;
        List<Trainer> trainerList;

        try {
            trainingList = TrainingService.getInstance().findJoinItems();
            orderList = OrderService.getInstance().findItems();
            trainerList = TrainerService.getInstance().findItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, trainingList);
        content.insertAttribute(PARAM_READ_ALL_ORDER, orderList);
        content.insertAttribute(PARAM_READ_ALL_TRAINER, trainerList);

        LOGGER.log(Level.DEBUG, trainingList);
        LOGGER.log(Level.DEBUG, orderList);
        LOGGER.log(Level.DEBUG, trainerList);

        String pageUrl = PageConstant.PAGE_TRAINING;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
