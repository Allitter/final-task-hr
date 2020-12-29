package com.epam.hr.domain.logic.filter;

import com.epam.hr.domain.logic.command.Attributes;
import com.epam.hr.domain.logic.command.CommandType;
import com.epam.hr.domain.logic.command.Pages;
import com.epam.hr.domain.model.User;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute(Attributes.USER);
        CommandType command = (CommandType)request.getAttribute(Attributes.COMMAND);

        if (command.hasAccessToCommand(user)) {
            chain.doFilter(request, response);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(Pages.ACCESS_DENIED);
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
