package com.dk.gym.filter;

import com.dk.gym.command.PageConstant;
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
import java.util.UUID;

import static com.dk.gym.service.ParamConstant.*;
import static com.dk.gym.service.ParamConstant.PARAM_FORM_SESSION_ID;

@WebFilter(urlPatterns = {"/controller"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})

public class RepeatFormRequestFilter implements Filter {

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

        String lastFormAttribute = (String)session.getAttribute(PARAM_FORM_SESSION_ID);

        String currentFormAttribute = req.getParameter(PARAM_FORM_ID);

        session.setAttribute(PARAM_FORM_SESSION_ID, Long.toHexString(UUID.randomUUID().getLeastSignificantBits()));

        if(lastFormAttribute != null
                && currentFormAttribute != null
                && !lastFormAttribute.equals(currentFormAttribute)) {
                resp.sendRedirect(indexPath);
                LOGGER.log(Level.INFO, "RepeatFormSecurity filtered");
                return;
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
        indexPath = null;
    }
}

