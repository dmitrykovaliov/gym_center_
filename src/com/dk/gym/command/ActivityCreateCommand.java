package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.impl.ActivityServiceImpl;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.LineNotEmptyValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;

import static com.dk.gym.constant.ParamConstant.*;
import static com.dk.gym.constant.ParamConstant.PARAM_DONE;


public class ActivityCreateCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        String name = content.findParameter(PARAM_NAME);
        String price = content.findParameter(PARAM_PRICE);
        String note = content.findParameter(PARAM_NOTE);

        if(new LineNotEmptyValidator().validate(name, price, note)) {
            try {
                Activity activity = new Activity();
                boolean initialized = ActivityServiceImpl.getInstance().initItem(name, price, note, activity);
                String message = ActivityServiceImpl.getInstance().createItem(initialized, activity);

                if(LocaleManager.getProperty("message.error.invalid").equals(message)) {
                    content.insertAttribute(PARAM_ERROR, message);
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotCreated"));
                } else if("-1".equals(message)) {
                    content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.error.database"));
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotCreated"));
                } else {
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemCreated") + message);
                }

            } catch (ServiceException e) {
               throw new CommandException(e);
            }
        } else {
            content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.info.emptyLine"));
        }
        String pageUrl = PageConstant.PAGE_ACTIVITY_READ;
        return new ContentPage(PageType.FORWARD, pageUrl);
    }
}
