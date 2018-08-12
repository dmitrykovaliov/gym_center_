package com.dk.gym.listener;

import com.dk.gym.service.ParamConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebListener
public class LoginRequestListener implements ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();
        HttpSession session = req.getSession();
        if (session.getAttribute(ParamConstant.PARAM_ERROR) != null
                && session.getAttribute(ParamConstant.PARAM_ROLE) != null) {
            session.setAttribute(ParamConstant.PARAM_ERROR, null);
            LOGGER.log(Level.INFO, "Destroyed sessionParamError");
        }
    }
}
