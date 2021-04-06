package com.epam.hr.domain.controller.filter;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.CommandType;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.model.User;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Checks if user can access particular command
 */
public class AccessFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        CommandType commandType = (CommandType) request.getAttribute(Attributes.COMMAND);
        User.Role role = user.getRole();

        if (user.isBanned() && commandType != CommandType.LOGOUT) {
            String path = Pages.BAN_PAGE;
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);
            return;
        }

        if (!user.isEnabled() && commandType != CommandType.VERIFICATION && commandType != CommandType.LOGOUT) {
            request.setAttribute(Attributes.COMMAND, CommandType.VERIFICATION_PAGE);
        }

        if (commandType.hasAccessToCommand(role)) {
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
