package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.servlet.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutCommand implements ActionCommand {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) {

        String pageUrl = PageConstant.PAGE_INDEX;

        LOGGER.log(Level.DEBUG, pageUrl);

        return new ContentPage(PageType.REDIRECT, pageUrl);
    }
}
