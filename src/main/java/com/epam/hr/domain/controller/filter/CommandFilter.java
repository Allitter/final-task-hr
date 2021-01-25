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

/**
 * Resets request's command attribute string value with connected {@link CommandType command type}
 */
public class CommandFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        String command = (String) request.getAttribute(Attributes.COMMAND);

        Optional<CommandType> optional = CommandType.getCommand(command);
        CommandType commandType = optional.orElse(CommandType.DEFAULT_COMMAND);
        request.setAttribute(Attributes.COMMAND, commandType);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
