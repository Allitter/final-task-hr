package com.epam.hr.domain.controller.command.impl;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConfirmationPageCommand extends AbstractCommand {

    public static final int FIRST_ELEMENT = 0;

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

            String value = ((String[]) map.get(key))[FIRST_ELEMENT];
            parameters.put(keyValue, value);
        }

        request.setAttribute(Attributes.PARAMETERS, parameters);
        return Router.forward(Pages.CONFIRMATION);
    }
}
