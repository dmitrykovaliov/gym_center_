package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.servlet.RequestContent;

public class EmptyCommand implements ActionCommand {
    @Override
    public ContentPage execute(RequestContent content) {

        String pageUrl = PageConstant.PAGE_LOGIN;

        return new ContentPage(PageType.FORWARD, pageUrl);
    }
}
