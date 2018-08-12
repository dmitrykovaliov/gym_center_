package com.dk.gym.filter;

import com.dk.gym.entity.Role;
import com.dk.gym.service.ParamConstant;
import com.dk.gym.validator.EnumValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})

public class PageForwardFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger();

    private String indexPath;

    public void init(FilterConfig fConfig) {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(ParamConstant.PARAM_ROLE);

        if (!new EnumValidator().validate(Role.class, role)) {

            req.getServletContext().getRequestDispatcher(indexPath).forward(req, resp);
            LOGGER.log(Level.INFO, "PageForward filtered");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
        indexPath = null;
    }
}

