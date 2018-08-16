package com.dk.gym.listener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import static com.dk.gym.service.ParamConstant.*;

/**
 * The listener which invalidate session when logout command activated.
 */
@WebListener
public class LogoutRequestListener implements ServletRequestListener {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Request destroyed.
     *
     * @param servletRequestEvent the servlet request event
     */
    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        //empty
    }

    /**
     * Request initialized.
     *
     * @param servletRequestEvent the servlet request event
     */
    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();
        if (PARAM_LOGOUT.equals(req.getParameter(PARAM_COMMAND))) {
            req.getSession().invalidate();
            LOGGER.log(Level.INFO, "Session invalidated");
        }
    }
}
