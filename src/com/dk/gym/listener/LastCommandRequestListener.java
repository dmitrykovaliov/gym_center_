package com.dk.gym.listener;

import com.dk.gym.service.ParamConstant;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

@WebListener
public class LastCommandRequestListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();

        String uri = req.getRequestURI();
        String query = req.getQueryString();

        String lastCommand = uri + (query != null ? ("?" + query) : "");

        req.getSession().setAttribute(ParamConstant.ATTR_URL_QUERY, lastCommand);
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    }
}
