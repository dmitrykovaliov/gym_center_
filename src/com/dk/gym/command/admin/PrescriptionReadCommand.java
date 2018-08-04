package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.constant.PageConstant;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Prescription;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.join.JoinPrescription;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import com.dk.gym.service.PrescriptionService;
import com.dk.gym.service.TrainerService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_ORDER;
import static com.dk.gym.constant.ParamConstant.PARAM_READ_ALL_TRAINER;

public class PrescriptionReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<JoinPrescription> prescriptionList;
        List<Order> orderList;
        List<Trainer> trainerList;

        try {
            prescriptionList = PrescriptionService.getInstance().findJoinItems();
            orderList = OrderService.getInstance().findItems();
            trainerList = TrainerService.getInstance().findItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, prescriptionList);
        content.insertAttribute(PARAM_READ_ALL_ORDER, orderList);
        content.insertAttribute(PARAM_READ_ALL_TRAINER, trainerList);

        LOGGER.log(Level.DEBUG, prescriptionList);
        LOGGER.log(Level.DEBUG, orderList);
        LOGGER.log(Level.DEBUG, trainerList);

        String pageUrl = PageConstant.PAGE_PRESCRIPTION;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
