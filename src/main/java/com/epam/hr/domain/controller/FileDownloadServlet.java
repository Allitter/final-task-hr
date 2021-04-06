package com.epam.hr.domain.controller;

import com.epam.hr.domain.service.FileService;
import com.epam.hr.domain.service.impl.GCFileService;
import com.epam.hr.domain.service.resource.Resource;
import com.epam.hr.domain.util.IOUtils;
import com.epam.hr.exception.ControllerException;
import com.epam.hr.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * Servlet used to download programs dynamic files such as users' pictures
 */
public class FileDownloadServlet extends HttpServlet {
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
    private static final Logger LOGGER = LogManager.getLogger();
    private static final FileService fileService = new GCFileService();
    private static final String HEADER_NAME = "Content-Disposition";
    private static final String HEADER_VALUE_FORMAT = "attachment; filename=\"%s\"";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String fileName = getFileName(request);
        try {
            Resource resource = fileService.getResource(fileName);

            setMimeType(response, fileName);
            setHeader(response, fileName);

            if (resource.getContentSize() > Integer.MAX_VALUE) {
                throw new ControllerException("Download file is too big");
            }

            response.setContentLength((int) resource.getContentSize());
            try (OutputStream outStream = response.getOutputStream();
                 InputStream inStream = resource.getInputStream()) {
                IOUtils.write(inStream, outStream);
            }

        } catch (IOException | ServiceException | ControllerException e) {
            LOGGER.error(e);
        }
    }

    private String getFileName(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String servletPath = request.getServletPath();
        int index = url.lastIndexOf(servletPath);
        if (index != -1) {
            index += servletPath.length() + 1;
        }
        return url.substring(index);
    }

    private void setMimeType(HttpServletResponse response, String filePath) {
        String mimeType = URLConnection.guessContentTypeFromName(filePath);
        if (mimeType == null) {
            mimeType = DEFAULT_CONTENT_TYPE;
        }

        response.setContentType(mimeType);
    }

    private void setHeader(HttpServletResponse response, String fileName) {
        String headerValue = String.format(HEADER_VALUE_FORMAT, fileName);
        response.setHeader(HEADER_NAME, headerValue);
    }
}
