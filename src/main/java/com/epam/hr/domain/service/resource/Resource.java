package com.epam.hr.domain.service.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Represents the resource that can be read, used in {@link com.epam.hr.domain.service.FileService}
 * and {@link com.epam.hr.domain.controller.FileDownloadServlet}
 */
public interface Resource {
    /**
     * @return the name of file
     */
    String getFileName();

    /**
     * @return the MIME type of file
     */
    String getContentType();

    /**
     * @return files size
     */
    long getContentSize();

    /**
     * @return {@link InputStream} to read the file
     * @throws IOException if i/o error occurs
     */
    InputStream getInputStream() throws IOException;
}
