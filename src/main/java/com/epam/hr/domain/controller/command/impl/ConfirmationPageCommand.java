package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ConfirmationPageCommand extends AbstractCommand {
    private final boolean hasMessage;

    public ConfirmationPageCommand(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String previousPage = request.getHeader(Attributes.REFERER);
        request.setAttribute(Attributes.PREVIOUS_PAGE, previousPage);

        Map<String, String> parameters = new HashMap<>();
        Map map = request.getParameterMap();
        for (Object key : map.keySet()) {
            String keyValue = (String) key;
            if (Attributes.COMMAND.equalsIgnoreCase(keyValue)) {
                continue;
            }

            String value = request.getParameter(keyValue);
            parameters.put(keyValue, value);
        }

        request.setAttribute(Attributes.PARAMETERS, parameters);
        request.setAttribute(Attributes.SHOW_MESSAGE, hasMessage);
        return Router.forward(Pages.CONFIRMATION);
    }
}
