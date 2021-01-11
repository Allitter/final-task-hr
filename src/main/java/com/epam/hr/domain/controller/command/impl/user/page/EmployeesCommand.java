package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.model.User;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EmployeesCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
    private final UserService userService;

    public EmployeesCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int page = pageParameter != null ? Integer.parseInt(pageParameter) : 0;
        page = Math.max(page, 0);

        int totalQuantity = userService.findEmployeesQuantity();
        int numberOfPages = totalQuantity / NUMBER_OF_RECORDS_PER_PAGE;
        numberOfPages = totalQuantity % NUMBER_OF_RECORDS_PER_PAGE == 0 ? numberOfPages : numberOfPages + 1;

        if (numberOfPages != 0 && page >= numberOfPages) {
            page--;
        }

        int from = page * NUMBER_OF_RECORDS_PER_PAGE;
        List<User> employees = userService.findEmployees(from, NUMBER_OF_RECORDS_PER_PAGE);
        request.setAttribute(Attributes.USERS, employees);
        request.setAttribute(Attributes.NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.EMPLOYEES);
    }
}
