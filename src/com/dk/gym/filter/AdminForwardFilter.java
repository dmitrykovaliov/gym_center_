package com.dk.gym.filter;

import com.dk.gym.constant.ParamConstant;
import com.dk.gym.entity.Role;
import com.dk.gym.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//@WebFilter(dispatcherTypes = {DispatcherType.FORWARD}, urlPatterns = {"/jsp/admin/*"})
public class AdminForwardFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        User user = (User) httpRequest.getSession().getAttribute(ParamConstant.PARAM_USER);
        if (user == null || !user.getRole().equals(Role.ADMIN)) {
            RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
