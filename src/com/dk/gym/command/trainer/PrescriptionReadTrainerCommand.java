package com.dk.gym.command.trainer;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.PageConstant;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Order;
import com.dk.gym.entity.Prescription;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.OrderService;
import com.dk.gym.service.PrescriptionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.service.ParamConstant.*;

public class PrescriptionReadTrainerCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<Prescription> prescriptionList;

        List<Order> orderAllList;

        try {
            prescriptionList = PrescriptionService.getInstance().findAllPrescriptionByTrainer(content);

            orderAllList = OrderService.getInstance().findAllOrder();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, prescriptionList);

        content.insertAttribute(PARAM_READ_ALL_ORDER, orderAllList);

        LOGGER.log(Level.DEBUG, prescriptionList);
        LOGGER.log(Level.DEBUG, orderAllList);

        String pageUrl = PageConstant.PAGE_TRAINER_PRESCRIPTION;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
