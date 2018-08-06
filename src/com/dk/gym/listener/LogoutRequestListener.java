package com.dk.gym.listener;

import com.dk.gym.service.ParamConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class LogoutRequestListener implements ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();
        if (ParamConstant.PARAM_LOGOUT.equals(req.getParameter(ParamConstant.PARAM_COMMAND))) {
            req.getSession().invalidate();
            LOGGER.log(Level.INFO, "Session invalidated");
        }
    }
}
