package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.impl.ActivityServiceImpl;
import com.dk.gym.servlet.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.dk.gym.constant.ParamConstant.*;

public class ActivityReadCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        List itemList;

        try {
            itemList = ActivityServiceImpl.getInstance().revertItems();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        content.insertAttribute(PARAM_READ_ALL, itemList);

        String pageUrl = PageConstant.PAGE_ACTIVITY;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.FORWARD, pageUrl);
    }
}
