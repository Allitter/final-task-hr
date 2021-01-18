package com.epam.hr.domain.controller.command.impl.user.page;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.controller.command.impl.user.AbstractUserCommand;
import com.epam.hr.domain.model.Page;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class JobSeekersCommand extends AbstractUserCommand {
    private static final int NUMBER_OF_RECORDS_PER_PAGE = 10;
    private final UserService userService;

    public JobSeekersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        String pageParameter = (String) request.getAttribute(Attributes.PAGE);
        int queriedPage = pageParameter != null ? Integer.parseInt(pageParameter) : 0;

        int totalRecordsCount = userService.findJobSeekersQuantity();
        Page page = getClosestExistingPage(queriedPage, totalRecordsCount);

        int from = page.getCurrentPage() * NUMBER_OF_RECORDS_PER_PAGE;
        List<User> jobSeekers = userService.findJobSeekers(from, NUMBER_OF_RECORDS_PER_PAGE);
        request.setAttribute(Attributes.USERS, jobSeekers);
        request.setAttribute(Attributes.PAGE, page);

        return Router.forward(Pages.JOB_SEEKERS);
    }
}
