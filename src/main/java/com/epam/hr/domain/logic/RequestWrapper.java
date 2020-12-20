package com.epam.hr.domain.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestWrapper extends HttpServletRequestWrapper {
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Object getAttribute(String name) {
        Object attribute = super.getAttribute(name);
        attribute = attribute == null ? super.getParameter(name) : attribute;
        return attribute;
    }


}
