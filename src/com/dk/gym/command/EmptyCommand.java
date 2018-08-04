package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.controller.RequestContent;

public class EmptyCommand implements ActionCommand {

    @Override
    public ContentPage execute(RequestContent content) {

        String pageUrl = PageConstant.PAGE_LOGIN;

        return new ContentPage(RequestMethod.FORWARD, pageUrl);
    }
}
