package com.epam.hr.domain.controller.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class ParameterFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        Enumeration parameters  = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterKey = (String)parameters.nextElement();
            String[] parameterValues = request.getParameterValues(parameterKey);

            if (parameterValues.length == 1) {
                request.setAttribute(parameterKey, parameterValues[0]);
            } else if (parameterValues.length > 1) {
                request.setAttribute(parameterKey, new ArrayList<>(Arrays.asList(parameterValues)));
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
