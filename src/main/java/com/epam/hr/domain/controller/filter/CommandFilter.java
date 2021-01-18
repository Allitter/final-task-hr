package com.epam.hr.domain.controller.filter;

import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.CommandType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class CommandFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        String command = (String) request.getAttribute(Attributes.COMMAND);
        Optional<CommandType> optional = CommandType.getCommand(command);

        if (optional.isPresent()) {
            CommandType commandType = optional.get();
            request.setAttribute(Attributes.COMMAND, commandType);
        } else {
            request.setAttribute(Attributes.COMMAND, CommandType.DEFAULT_COMMAND);
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
