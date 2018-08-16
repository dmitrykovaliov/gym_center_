package com.dk.gym.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * The Class XssFilter. The protection from Xss attacks.
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})

public class XssFilter implements Filter {

    /**
     * Inits the.
     *
     * @param fConfig the f config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        //empty
    }

    /**
     * Do filter.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        //empty
    }
}