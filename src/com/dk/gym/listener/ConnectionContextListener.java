package com.dk.gym.listener;

import com.dk.gym.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * The listener which initialize pool of connections at the start of application and
 * close pool of connections at the finishing of application.
 */
@WebListener
public class ConnectionContextListener implements ServletContextListener {

    /**
     * Context initialized.
     *
     * @param servletContextEvent the servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().initPool();
    }

    /**
     * Context destroyed.
     *
     * @param servletContextEvent the servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().closePool();
    }
}
