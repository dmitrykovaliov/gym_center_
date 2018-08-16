package com.dk.gym.controller;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.Router;
import com.dk.gym.command.factory.ActionFactory;
import com.dk.gym.exception.CommandException;
import com.dk.gym.command.RouterPage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.dk.gym.command.PageConstant.*;

/**
 * The Class GymServlet. Main controller in application.
 */
@WebServlet(name = "GymServlet", urlPatterns = {"/controller"})
@MultipartConfig(location = "/home/dk", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5)

public class GymServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Do get.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
    
    /**
     * Do post.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Process request.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RouterPage routerPage;

        SessionRequestContent content = new SessionRequestContent();

        content.extractValues(request);

        ActionCommand command = new ActionFactory().defineCommand(content);

        try {
            routerPage = command.execute(content);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);
            e.printStackTrace();
            routerPage = new RouterPage(Router.FORWARD, PAGE_ERROR);
        }

        content.insertValues(request);

        if (routerPage != null) {
            switch (routerPage.getRouter()) {
                case FORWARD:
                   getServletContext().getRequestDispatcher(routerPage.getPageURL()).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(routerPage.getPageURL());
                    break;
                default:
                    getServletContext().getRequestDispatcher(PAGE_BASE_ERROR).forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher(PAGE_BASE_ERROR).forward(request, response);
        }
    }

}
