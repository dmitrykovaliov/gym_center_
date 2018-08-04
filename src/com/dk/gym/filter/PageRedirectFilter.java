/*
package com.dk.gym.filter;

import com.dk.gym.constant.ParamConstant;
import com.dk.gym.entity.Role;
import com.dk.gym.validator.EnumValidator;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/*"},
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectFilter implements Filter {

    private String indexPath;

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter("INDEX_PATH");
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute(ParamConstant.PARAM_ROLE);
        if (!new EnumValidator().validate(Role.class, role)) {
            RequestDispatcher dispatcher = req.getServletContext()
                    .getRequestDispatcher(indexPath);
            dispatcher.forward(req, resp);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}

*/
