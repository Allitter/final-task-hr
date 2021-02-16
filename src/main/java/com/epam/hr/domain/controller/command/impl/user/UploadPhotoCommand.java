package com.epam.hr.domain.controller.command.impl.user;

import com.epam.hr.domain.controller.Router;
import com.epam.hr.domain.controller.command.Attributes;
import com.epam.hr.domain.model.User;
import com.epam.hr.domain.service.FileService;
import com.epam.hr.domain.service.UserService;
import com.epam.hr.domain.service.resource.PartResource;
import com.epam.hr.domain.service.resource.Resource;
import com.epam.hr.domain.util.IOUtils;
import com.epam.hr.exception.ControllerException;
import com.epam.hr.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;

public class UploadPhotoCommand extends AbstractUserCommand {
    private final UserService userService;
    private final FileService fileService;

    public UploadPhotoCommand(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    public Router execute(HttpServletRequest request) throws ServiceException, ControllerException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Attributes.USER);
        long id = user.getId();


        String fileName;
        try {
            Part part = request.getPart(Attributes.FILE);
            Resource resource = new PartResource(part, user.getLogin());
            fileName = resource.getFileName();

            if (!IOUtils.isFileFormatSupported(fileName)) {
                throw new ControllerException("Unsupported file format");
            }

            fileService.save(resource);
        } catch (ServletException | IOException e) {
            throw new ControllerException(e);
        }

        user = userService.setAvatar(id, fileName);
        session.setAttribute(Attributes.USER, user);

        String path = request.getHeader(Attributes.REFERER);
        return Router.redirect(path);
    }
}
