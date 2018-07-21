package com.dk.gym.command;

import com.dk.gym.constant.ParamConstant;
import com.dk.gym.exception.CommandException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.servlet.RequestContent;
import resource.LocaleManager;

import java.util.Locale;

public class EnCommand implements ActionCommand {
    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        content.insertSessionAttribute(ParamConstant.PARAM_LOCALE, "");
        LocaleManager.changeResource(Locale.getDefault());


        String pageUrl = (String)content.findSessionAttribute(ParamConstant.ATTR_URL_QUERY);

        return new ContentPage(PageType.REDIRECT, pageUrl);
    }
}
