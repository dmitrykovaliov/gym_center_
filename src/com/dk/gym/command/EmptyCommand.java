package com.dk.gym.command;

import com.dk.gym.controller.SessionRequestContent;

public class EmptyCommand implements ActionCommand {

    @Override
    public RouterPage execute(SessionRequestContent content) {

        String pageUrl = PageConstant.PAGE_INDEX;

        return new RouterPage(Router.FORWARD, pageUrl);
    }
}
