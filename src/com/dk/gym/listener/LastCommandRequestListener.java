package com.dk.gym.listener;

import com.dk.gym.command.PageConstant;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dk.gym.command.PageConstant.*;
import static com.dk.gym.service.ParamConstant.*;

@WebListener
public class LastCommandRequestListener implements ServletRequestListener {

    private static final String CONTROLLER = "/controller";
    private static final String COMMAND_LOCALE_RU = "command=locale_ru";
    private static final String COMMAND_LOCALE_EN = "command=locale_en";

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();

        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        String query = req.getQueryString();

        if(query != null && (CONTROLLER.equals(uri) || PAGE_MAIN.equals(uri))) {
           if(COMMAND_LOCALE_RU.equals(query) || COMMAND_LOCALE_EN.equals(query)) {
               session.setAttribute(PARAM_URL_QUERY, PAGE_MAIN);
           } else {
               session.setAttribute(PARAM_URL_QUERY, uri + "?" + query);
           }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    }
}
