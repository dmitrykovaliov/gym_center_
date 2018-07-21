package com.dk.gym.servlet;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestContent {

    private static final Logger LOGGER = LogManager.getLogger();

    private Map<String, Object> requestAttributes;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> requestSessionAttributes;

    private String query;
    private String url;

    public RequestContent() {
        requestAttributes = new HashMap<>();
        requestParameters = new HashMap<>();
        requestSessionAttributes = new HashMap<>();
    }

    public String getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }

    public void extractValues(HttpServletRequest request) {
        extractAttributes(request);
        extractParameters(request);
        extractSessionAttributes(request);
        extractQuery(request);
        extractUrl(request);
        LOGGER.log(Level.INFO, "attributes/parameters extracted");
    }



    public void insertValues(HttpServletRequest request) {
        insertAttributes(request);
        insertSessionAttributes(request);
        LOGGER.log(Level.INFO, "attributes inserted");
    }

    private void extractQuery(HttpServletRequest request) {
        query =request.getQueryString();
        LOGGER.log(Level.DEBUG, query);


    } private void extractUrl(HttpServletRequest request) {
        url =request.getRequestURI();
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


}