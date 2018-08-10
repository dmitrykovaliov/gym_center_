package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.command.PageConstant;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;
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

public class PrescriptionReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<Prescription> prescriptionList;
        List<Trainer> trainerList;

        List<Order> orderAllList;
        List<Trainer> trainerAllList;

        try {
            prescriptionList = PrescriptionService.getInstance().findAllPrescription();
            trainerList = PrescriptionService.getInstance().findAllTrainer();

            orderAllList = OrderService.getInstance().findAllOrder();
            trainerAllList = TrainerService.getInstance().findAllTrainer();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, prescriptionList);
        content.insertAttribute(PARAM_READ_TRAINER, trainerList);

        content.insertAttribute(PARAM_READ_ALL_ORDER, orderAllList);
        content.insertAttribute(PARAM_READ_ALL_TRAINER, trainerAllList);

        LOGGER.log(Level.DEBUG, prescriptionList);
        LOGGER.log(Level.DEBUG, trainerList);

        LOGGER.log(Level.DEBUG, orderAllList);
        LOGGER.log(Level.DEBUG, trainerAllList);

        String pageUrl = PageConstant.PAGE_ADMIN_PRESCRIPTION;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
