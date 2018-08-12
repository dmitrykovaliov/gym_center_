package com.dk.gym.command.client;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.RouterPage;
import com.dk.gym.command.PageConstant;
import com.dk.gym.command.Router;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.Training;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.TrainingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.service.ParamConstant.*;

public class TrainingReadClientCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) throws CommandException {

        List<Training> trainingList;
        List<Trainer> trainerList;

        try {
            trainingList = TrainingService.getInstance().findAllTrainingByClient(content);
            trainerList = TrainingService.getInstance().findRelatedAllTrainerByClient(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, trainingList);
        content.insertAttribute(PARAM_READ_TRAINER, trainerList);

        LOGGER.log(Level.DEBUG, trainingList);

        String pageUrl = PageConstant.PAGE_CLIENT_TRAINING;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
