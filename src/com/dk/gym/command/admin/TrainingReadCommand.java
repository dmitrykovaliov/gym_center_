package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.command.PageConstant;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import com.dk.gym.service.TrainerService;
import com.dk.gym.service.TrainingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.service.ParamConstant.*;

public class TrainingReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<Training> trainingList;
        List<Trainer> trainerList;

        List<Order> orderAllList;
        List<Trainer> trainerAllList;

        try {
            trainingList = TrainingService.getInstance().findItems();
            trainerList = TrainingService.getInstance().findTrainerItems();

            orderAllList = OrderService.getInstance().findItems();
            trainerAllList = TrainerService.getInstance().findItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, trainingList);
        content.insertAttribute(PARAM_READ_TRAINER, trainerList);

        content.insertAttribute(PARAM_READ_ALL_ORDER, orderAllList);
        content.insertAttribute(PARAM_READ_ALL_TRAINER, trainerAllList);

        LOGGER.log(Level.DEBUG, trainingList);
        LOGGER.log(Level.DEBUG, orderAllList);
        LOGGER.log(Level.DEBUG, trainerAllList);

        String pageUrl = PageConstant.PAGE_TRAINING;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
