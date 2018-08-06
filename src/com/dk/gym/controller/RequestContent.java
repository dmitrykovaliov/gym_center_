package com.dk.gym.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

import static com.dk.gym.service.ParamConstant.*;

public class RequestContent {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String MULTIPART = "multipart/form-data";

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> requestSessionAttributes;
    private Collection<Part> requestParts;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestSessionAttributes = new HashMap<>();
    }

    public void extractValues(HttpServletRequest request) {
        extractAttributes(request);
        extractParameters(request);
        extractSessionAttributes(request);
        extractParts(request);
        LOGGER.log(Level.INFO, "attributes/parameters extracted");
    }

    public void insertValues(HttpServletRequest request) {
        insertAttributes(request);
        insertSessionAttributes(request);
        LOGGER.log(Level.INFO, "attributes inserted");
    }

    private void extractAttributes(HttpServletRequest request) {

        Enumeration<String> attributeNames = request.getAttributeNames();

        while (attributeNames.hasMoreElements()) {
            String key = attributeNames.nextElement();

            requestAttributes.put(key, request.getAttribute(key));
        }
    }

    private void extractParameters(HttpServletRequest request) {

        Enumeration<String> parameterNames = request.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();

            requestParameters.put(key, request.getParameterValues(key));
        }
    }


    private void extractSessionAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession();

        Enumeration<String> sessionAttributeNames = session.getAttributeNames();

        while (sessionAttributeNames.hasMoreElements()) {
            String key = sessionAttributeNames.nextElement();

            requestSessionAttributes.put(key, request.getSession().getAttribute(key));
        }
    }

    private void extractParts(HttpServletRequest request) {
        try {
            if (request.getContentType() != null && request.getContentType().toLowerCase().contains(MULTIPART)) {
                requestParts = request.getParts();
                requestParameters.put(PARAM_UPLOAD, new String[]{request.getServletContext().getInitParameter(PARAM_DIR_UPLOAD)});
            }
        } catch (ServletException | IOException e) {
            LOGGER.log(Level.ERROR, "Can't get multipart from request", e);
        }
    }

    private void insertAttributes(HttpServletRequest request) {

        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    private void insertSessionAttributes(HttpServletRequest request) {

        for (Map.Entry<String, Object> entry : requestSessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public void insertAttribute(String key, Object value) {
        requestAttributes.put(key,value);
    }

    public void insertSessionAttribute(String key, Object value) {
        requestSessionAttributes.put(key,value);
    }

    public Object findAttribute(String key) {
        return requestAttributes.get(key);
    }

    public Object findSessionAttribute(String key) {
        return requestSessionAttributes.get(key);
    }

    public String[] findParameters(String key) {
        return requestParameters.get(key);
    }

    public String findParameter(String key) {

        String param = null;
        String[] paramList = requestParameters.get(key);
        if(paramList != null) {
           param = paramList[0];
        }
        return param;
    }

    public Part findPart(String partName) {
        Part partItem = null;
        if(requestParts != null) {
            for (Part part : requestParts) {
                if (part.getName().equals(partName)) {
                    partItem = part;
                    break;
                }
            }
        }
        return partItem;
    }
}