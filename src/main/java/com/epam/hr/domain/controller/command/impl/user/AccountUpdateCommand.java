package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.controller.command.Command;
import com.epam.hr.domain.controller.command.Pages;
import com.epam.hr.domain.service.ResumeService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.model.Resume;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.model.UserRole;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AccountUpdateCommand implements Command {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final boolean DEFAULT_IS_BANNED = false;
    private final UserService service;
    private final ResumeService resumeService;

    public AccountUpdateCommand(UserService service, ResumeService resumeService) {
        this.service = service;
        this.resumeService = resumeService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        UserRole role = user.getRole();
        boolean enabled = user.isEnabled();
        String login = (String) request.getAttribute(Attributes.LOGIN);
        String name = (String) request.getAttribute(Attributes.NAME);
        String lastName = (String) request.getAttribute(Attributes.LAST_NAME);
        String patronymic = (String) request.getAttribute(Attributes.PATRONYMIC);
        String birthDateString = (String) request.getAttribute(Attributes.BIRTH_DATE);
        String phone = (String) request.getAttribute(Attributes.PHONE);
        String email = (String) request.getAttribute(Attributes.EMAIL);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        // todo move to separate method
        Date birthDate;
        try {
            birthDate = dateFormat.parse(birthDateString);
        } catch (ParseException e) {
            throw new ServiceException("Invalid date format");
        }

        User updatedUser = new User.Builder(user)
                .setRole(role)
                .setLogin(login)
                .setName(name)
                .setLastName(lastName)
                .setPatronymic(patronymic)
                .setEmail(email)
                .setPhone(phone)
                .setBirthDate(birthDate)
                .setBanned(DEFAULT_IS_BANNED)
                .setEnabled(enabled)
                .build();

        try {
            user = service.saveUser(updatedUser);
            session.setAttribute(Attributes.USER, user);

            String path = request.getHeader(Attributes.REFERER);
            return Router.redirect(path);
        } catch (ValidationException e) {
            List<String> fails = e.getValidationFails();
            List<Resume> resumes = resumeService.findUserResumes(user.getId());
            request.setAttribute(Attributes.RESUMES, resumes);
            request.setAttribute(Attributes.FAILS, fails);
            return Router.forward(Pages.ACCOUNT);
        }
    }
}
