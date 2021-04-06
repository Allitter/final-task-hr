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

/**
 * Resets all request parameters to request attributes to provide ability
 * to change them during request handling
 */
public class ParameterFilter extends HttpFilter {
    private static final String LEFT_CHEVRON = "<";
    private static final String LEFT_CHEVRON_REPLACEMENT = "&lt";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        Enumeration parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterKey = (String) parameters.nextElement();
            request.getParameter(parameterKey);
            String[] parameterValues = request.getParameterValues(parameterKey);

            if (parameterValues.length == 1) {
                String parameter = parameterValues[0];
                parameter = parameter.replace(LEFT_CHEVRON, LEFT_CHEVRON_REPLACEMENT);
                request.setAttribute(parameterKey, parameter);
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
