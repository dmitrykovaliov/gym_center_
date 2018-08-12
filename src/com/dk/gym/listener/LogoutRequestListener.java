package com.dk.gym.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import static com.dk.gym.service.ParamConstant.*;

@WebListener
public class LogoutRequestListener implements ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();
        if (PARAM_LOGOUT.equals(req.getParameter(PARAM_COMMAND))) {
            req.getSession().invalidate();
            LOGGER.log(Level.INFO, "Session invalidated");
        }
    }
}
