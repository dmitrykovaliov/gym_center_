package com.dk.gym.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"},

        initParams = {
        @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")})

public class EncodingFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    private String code;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

        code = fConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
           // LOGGER.log(Level.INFO, CommonConstant.MESSAGE_INFO_ENCODING);
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        code = null;
    }
}
