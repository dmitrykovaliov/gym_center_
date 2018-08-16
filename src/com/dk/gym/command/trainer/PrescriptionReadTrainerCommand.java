package com.dk.gym.command.trainer;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.RouterPage;
import com.dk.gym.command.PageConstant;
import com.dk.gym.command.Router;
import com.dk.gym.controller.SessionRequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Prescription;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import com.dk.gym.service.PrescriptionService;
import com.dk.gym.service.TrainerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.service.ParamConstant.*;

/**
 * The Class PrescriptionReadTrainerCommand.
 */
public class PrescriptionReadTrainerCommand implements ActionCommand {
    
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public RouterPage execute(SessionRequestContent content) throws CommandException {

        List<Prescription> prescriptionList;

        int idTrainer;

        List<Order> orderAllList;

        try {
            prescriptionList = PrescriptionService.getInstance().findAllPrescriptionByTrainer(content);

            idTrainer = TrainerService.getInstance().findTrainerByUserId(content).getIdTrainer();

            orderAllList = OrderService.getInstance().findAllOrder();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, prescriptionList);
        content.insertAttribute(PARAM_TRAINER_ID, idTrainer);

        content.insertAttribute(PARAM_READ_ALL_ORDER, orderAllList);

        LOGGER.log(Level.DEBUG, prescriptionList);
        LOGGER.log(Level.DEBUG, orderAllList);

        String pageUrl = PageConstant.PAGE_TRAINER_PRESCRIPTION;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
