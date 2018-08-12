package com.dk.gym.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dk.gym.command.CommandType.*;
import static com.dk.gym.command.PageConstant.*;
import static com.dk.gym.service.ParamConstant.*;

@WebListener
public class LastCommandRequestListener implements ServletRequestListener {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String CONTROLLER = "/controller";

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();

        HttpSession session = req.getSession();
        String uri = req.getRequestURI();
        String query = req.getQueryString();
        String command = req.getParameter(PARAM_COMMAND);

        if(query != null && CONTROLLER.equals(uri)) {
            String lastCommand;
            if (!(command == null
                    || command.equalsIgnoreCase(LOCALE_EN.toString())
                    || command.equalsIgnoreCase(LOCALE_RU.toString()))) {
                lastCommand = uri + "?" + query;
            } else {
                lastCommand = PAGE_MAIN;
            }
            session.setAttribute(PARAM_URL_QUERY, lastCommand);
            LOGGER.log(Level.DEBUG, "LastCommand: " + lastCommand);
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    }
}
