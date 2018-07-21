package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.constant.ParamConstant;
import com.dk.gym.entity.Activity;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.impl.ActivityServiceImpl;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.ChainIdValidator;
import com.dk.gym.validator.LineNotEmptyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;

import static com.dk.gym.constant.CommonConstant.*;
import static com.dk.gym.constant.ParamConstant.PARAM_DONE;
import static com.dk.gym.constant.ParamConstant.PARAM_ERROR;

public class ActivityUpdateCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        String id = content.findParameter(ParamConstant.PARAM_ID);
        String name = content.findParameter(ParamConstant.PARAM_NAME);
        String price = content.findParameter(ParamConstant.PARAM_PRICE);
        String note = content.findParameter(ParamConstant.PARAM_NOTE);

        if(new ChainIdValidator().validate(id)
                && new LineNotEmptyValidator().validate(id, name, price, note)) {
            try {
                Activity activity = ActivityServiceImpl.getInstance().findItemById(id);
                boolean initialized = ActivityServiceImpl.getInstance().initItem(name, price, note, activity);
                String message = ActivityServiceImpl.getInstance().updateItem(initialized, activity);

                if(LocaleManager.getProperty("message.error.invalid").equals(message)) {
                    content.insertAttribute(PARAM_ERROR, message);
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotUpdated"));
                } else if(MESSAGE_FALSE.equals(message)) {
                    content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.error.database"));
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemNotUpdated"));
                } else {
                    content.insertAttribute(PARAM_DONE, LocaleManager.getProperty("message.info.itemUpdated")+ message);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            content.insertAttribute(PARAM_ERROR, LocaleManager.getProperty("message.info.emptyLine"));
        }

        String pageUrl = PageConstant.PAGE_ACTIVITY_READ;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.FORWARD, pageUrl);
    }
}
