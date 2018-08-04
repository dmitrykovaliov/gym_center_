package com.dk.gym.controller;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.factory.ActionFactory;
import com.dk.gym.constant.PageConstant;
import com.dk.gym.constant.ParamConstant;
import com.dk.gym.exception.CommandException;
import com.dk.gym.command.ContentPage;
import com.dk.gym.command.RequestMethod;
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


@WebServlet(name = "GymServlet", urlPatterns = {"/controller"})
@MultipartConfig(location = "/home/dk", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)

public class GymServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        ContentPage contentPage;


        RequestContent content = new RequestContent();

        content.extractValues(request);

        ActionCommand command = new ActionFactory().defineCommand(content);

        try {
            contentPage = command.execute(content);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, e);
            e.printStackTrace();
            request.setAttribute(ParamConstant.PARAM_ERROR, e.getMessage());
            contentPage = new ContentPage(RequestMethod.FORWARD, PageConstant.PAGE_ERROR);
        }

        content.insertValues(request);

        if (contentPage != null) {
            switch (contentPage.getRequestMethod()) {
                case FORWARD:
                   getServletContext().getRequestDispatcher(contentPage.getPageURL()).forward(request, response);
                    break;
                case REDIRECT:
                    response.sendRedirect(contentPage.getPageURL());
                    break;
                default:
                    getServletContext().getRequestDispatcher(PageConstant.PAGE_BASE_ERROR).forward(request, response);
            }
        } else {
            getServletContext().getRequestDispatcher(PageConstant.PAGE_BASE_ERROR).forward(request, response);
        }
    }

}
