package com.dk.gym.filter;

import org.owasp.esapi.ESAPI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

public class XssRequestWrapper extends HttpServletRequestWrapper {

    private static final String EMPTY_CHARACTER = "";
    private static final String ANYTHING_BETWEEN_SCRIPT_TAGS = "<script>(.*?)</script>";
    private static final String ANYTHING_IN_SRC = "src[\r\n]*=[\r\n]*\'(.*?)\'";
    private static final String CLOSE_SCRIPT_TAG = "</script>";
    private static final String OPEN_SCRIPT_TAG = "<script(.*?)>";
    private static final String EVAL_EXPRESSION = "eval\\((.*?)\\)";
    private static final String EXPRESSION = "expression\\((.*?)\\)";
    private static final String EXPRESSION_FUNCTION = EXPRESSION;
    private static final String JAVASCRIPT_EXSPRESSION = "javascript:";
    private static final String VBSCRIPT_EXPRESSION = "vbscript:";
    private static final String ONLOAD_EXPRESSION = "onload(.*?)=";

    public XssRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

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

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);

        return stripXSS(value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return stripXSS(value);
    }

    private String stripXSS(String value) {
        if (value != null) {

            value = ESAPI.encoder().canonicalize(value);

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
