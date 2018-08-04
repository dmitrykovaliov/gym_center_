package com.dk.gym.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static com.dk.gym.constant.ParamConstant.PARAM_DIR_UPLOAD;


@WebServlet(name = "PictureServlet", urlPatterns = {"/picture/*"})

public class PictureServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.log(Level.INFO, "ENTERED");
        String fileName = req.getPathInfo();
        File filePath = new File(getServletContext().getInitParameter(PARAM_DIR_UPLOAD), fileName);
        resp.setHeader("Content-Type", getServletContext().getMimeType(fileName));
        resp.setHeader("Content-Length", String.valueOf(filePath.length()));

        LOGGER.log(Level.INFO, fileName);
        LOGGER.log(Level.INFO, filePath);

        Files.copy(filePath.toPath(), resp.getOutputStream());
    }
}



