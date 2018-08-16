package com.dk.gym.filter;

import com.dk.gym.entity.Role;
import com.dk.gym.validation.atomic.EnumValidator;
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

import static com.dk.gym.service.ParamConstant.*;

/**
 * The Class ServletSecurityFilter. Forward to index page, when not authorized user trying to activate controller.
 * commands via browser address bar.
 */
@WebFilter(urlPatterns = {"/controller"}, servletNames = {"GymServlet"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})

public class ServletSecurityFilter implements Filter {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** The index path. */
    private String indexPath;

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     */
    @Override
    public void init(FilterConfig filterConfig) {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    /**
     * Do filter.
     *
     * @param servletRequest the servlet request
     * @param servletResponse the servlet response
     * @param filterChain the filter chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(PARAM_ROLE);
        String command = req.getParameter(PARAM_COMMAND);

        if (!(new EnumValidator().validate(Role.class, role) || PARAM_LOGIN.equals(command))) {
            req.getServletContext().getRequestDispatcher(indexPath).forward(req, resp);
            LOGGER.log(Level.INFO, "ServletSecurity filtered");
            return;
        }
        filterChain.doFilter(req, resp);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        indexPath = null;
    }
}
