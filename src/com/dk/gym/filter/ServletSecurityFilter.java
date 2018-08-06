/*
package com.dk.gym.filter;


import com.dk.gym.entity.Role;
import com.dk.gym.validator.EnumValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.dk.gym.service.ParamConstant.*;

@WebFilter(urlPatterns = {"/controller"}, servletNames = {"GymServlet"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})


public class ServletSecurityFilter implements Filter {

    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(PARAM_ROLE);
        String command = req.getParameter(PARAM_COMMAND);

        if (!(new EnumValidator().validate(Role.class, role)
                || PARAM_LOGIN.equals(command))) {

            RequestDispatcher dispatcher = req.getServletContext()
                    .getRequestDispatcher(indexPath);

            dispatcher.forward(req, resp);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }
}
*/
