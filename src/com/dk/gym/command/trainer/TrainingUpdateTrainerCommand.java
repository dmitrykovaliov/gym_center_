package com.dk.gym.command.trainer;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.command.factory.ReturnMessageFactory;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.resource.LocaleManager;
import com.dk.gym.service.TrainingService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.dk.gym.command.PageConstant.PAGE_TRAINER_READ_TRAINING;
import static com.dk.gym.service.ParamConstant.PARAM_ERROR;

public class TrainingUpdateTrainerCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        ReturnMessageType message;
        try {
            message = TrainingService.getInstance().updateItem(content);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_ERROR, LocaleManager
                .getProperty(new ReturnMessageFactory().defineMessage(message)));

        String pageUrl = PAGE_TRAINER_READ_TRAINING;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
