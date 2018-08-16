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

/**
 * The listener which destroys error session attribute after authorization.
 */
@WebListener
public class LoginErrorRequestListener implements ServletRequestListener {
    
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
        HttpSession session = req.getSession();
        if (session.getAttribute(ParamConstant.PARAM_ERROR) != null
                && session.getAttribute(ParamConstant.PARAM_ROLE) != null) {
            session.setAttribute(ParamConstant.PARAM_ERROR, null);
            LOGGER.log(Level.INFO, "Destroyed sessionParamError");
        }
    }
}
