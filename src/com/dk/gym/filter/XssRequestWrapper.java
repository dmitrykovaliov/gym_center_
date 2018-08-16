package com.dk.gym.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * The Class XssRequestWrapper. Decorating request with replacing malicious code to empty one.
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    /** The Constant EMPTY_CHARACTER. */
    private static final String EMPTY_CHARACTER = "";
    
    /** The Constant ANYTHING_BETWEEN_SCRIPT_TAGS. */
    private static final String ANYTHING_BETWEEN_SCRIPT_TAGS = "<script>(.*?)</script>";
    
    /** The Constant ANYTHING_IN_SRC. */
    private static final String ANYTHING_IN_SRC = "src[\r\n]*=[\r\n]*\'(.*?)\'";
    
    /** The Constant CLOSE_SCRIPT_TAG. */
    private static final String CLOSE_SCRIPT_TAG = "</script>";
    
    /** The Constant OPEN_SCRIPT_TAG. */
    private static final String OPEN_SCRIPT_TAG = "<script(.*?)>";
    
    /** The Constant EVAL_EXPRESSION. */
    private static final String EVAL_EXPRESSION = "eval\\((.*?)\\)";
    
    /** The Constant EXPRESSION. */
    private static final String EXPRESSION = "expression\\((.*?)\\)";
    
    /** The Constant EXPRESSION_FUNCTION. */
    private static final String EXPRESSION_FUNCTION = EXPRESSION;
    
    /** The Constant JAVASCRIPT_EXSPRESSION. */
    private static final String JAVASCRIPT_EXSPRESSION = "javascript:";
    
    /** The Constant VBSCRIPT_EXPRESSION. */
    private static final String VBSCRIPT_EXPRESSION = "vbscript:";
    
    /** The Constant ONLOAD_EXPRESSION. */
    private static final String ONLOAD_EXPRESSION = "onload(.*?)=";

    /**
     * Instantiates a new xss request wrapper.
     *
     * @param servletRequest the servlet request
     */
    public XssRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    /**
     * Gets the parameter values.
     *
     * @param parameter the parameter
     * @return the parameter values
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return new String[0];
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = stripXSS(values[i]);
        }

        return encodedValues;
    }

    /**
     * Gets the parameter.
     *
     * @param parameter the parameter
     * @return the parameter
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        return stripXSS(value);
    }

    /**
     * Gets the header.
     *
     * @param name the name
     * @return the header
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    /**
     * Strip XSS.
     *
     * @param value the value
     * @return the string
     */
    private String stripXSS(String value) {
        if (value != null) {

            Pattern scriptPattern = Pattern.compile(ANYTHING_BETWEEN_SCRIPT_TAGS, Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(ANYTHING_IN_SRC, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(CLOSE_SCRIPT_TAG, Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(OPEN_SCRIPT_TAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(EVAL_EXPRESSION, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(EXPRESSION_FUNCTION, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(JAVASCRIPT_EXSPRESSION, Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(VBSCRIPT_EXPRESSION, Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);

            scriptPattern = Pattern.compile(ONLOAD_EXPRESSION, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                    | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll(EMPTY_CHARACTER);
        }
        return value;
    }
}
