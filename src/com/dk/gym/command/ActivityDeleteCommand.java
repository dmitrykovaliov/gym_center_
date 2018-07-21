package com.dk.gym.command;

import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.impl.ActivityServiceImpl;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.LineNotEmptyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;

import static com.dk.gym.constant.CommonConstant.MESSAGE_FALSE;
import static com.dk.gym.constant.PageConstant.*;
import static com.dk.gym.constant.ParamConstant.*;

public class ActivityDeleteCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        String id = content.findParameter(PARAM_ID);

        if (new LineNotEmptyValidator().validate(id)) {
            try {
                String message = ActivityServiceImpl.getInstance().deleteItem(id);
                if (LocaleManager.getProperty("message.error.invalid").equals(message)) {
                    content.insertAttribute(PARAM_ERROR, message);
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotDeleted"));
                } else if(MESSAGE_FALSE.equals(message)) {
                    content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.error.database"));
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotDeleted"));
                } else {
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemDeleted"));
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.info.emptyLine"));
        }

        String pageUrl = PAGE_ACTIVITY_READ;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.FORWARD, pageUrl);
    }
}
