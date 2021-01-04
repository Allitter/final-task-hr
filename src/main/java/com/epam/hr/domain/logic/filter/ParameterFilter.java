package com.epam.hr.domain.logic.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class ParameterFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Enumeration parameters  = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterKey = (String)parameters.nextElement();
            String parameter = request.getParameter(parameterKey);
            request.setAttribute(parameterKey, parameter);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
