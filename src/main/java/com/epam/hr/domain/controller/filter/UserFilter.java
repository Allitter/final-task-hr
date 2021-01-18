package com.epam.hr.domain.controller.filter;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Attributes.USER);

        if (user == null) {
            session.setAttribute(Attributes.USER, User.DEFAULT);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
