package com.dk.gym.command.admin;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
import com.dk.gym.constant.PageConstant;
import com.dk.gym.controller.RequestContent;
import com.dk.gym.dao.impl.UserDaoImpl;
import com.dk.gym.entity.Client;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.Trainer;
import com.dk.gym.entity.User;
import com.dk.gym.entity.join.JoinUser;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.service.ClientService;
import com.dk.gym.service.TrainerService;
import com.dk.gym.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.dk.gym.constant.ParamConstant.*;

public class UserReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List<JoinUser> userList;
        List<Client> clientList;
        List<Trainer> trainerList;
        List<Role> roleList = Arrays.asList(Role.class.getEnumConstants());

        try {
            userList = UserService.getInstance().findJoinItems();
            clientList = ClientService.getInstance().findItems();
            trainerList = TrainerService.getInstance().findItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, userList);
        content.insertAttribute(PARAM_READ_ALL_CLIENT, clientList);
        content.insertAttribute(PARAM_READ_ALL_TRAINER, trainerList);
        content.insertAttribute(PARAM_READ_ALL_ROLE, roleList);

        LOGGER.log(Level.DEBUG, userList);
        LOGGER.log(Level.DEBUG, clientList);
        LOGGER.log(Level.DEBUG, trainerList);
        LOGGER.log(Level.DEBUG, roleList);

        String pageUrl = PageConstant.PAGE_USER;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
