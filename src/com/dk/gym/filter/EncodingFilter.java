package com.dk.gym.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * The Class EncodingFilter. Filter encoding to UTF-8.
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})

public class EncodingFilter implements Filter {

    /** The code. */
    private String code;

    /**
     * Inits the.
     *
     * @param fConfig the f config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        code = fConfig.getInitParameter("encoding");
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
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        code = null;
    }
}
