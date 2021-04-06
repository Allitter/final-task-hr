package com.epam.hr.domain.util;

import com.epam.hr.exception.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides convenient i/o methods
 */
public final class IOUtils {
    private static final int BUFFER_SIZE = 4096;
    private static final int FILE_EXTENSION_BIAS = 1;

    private static final List<String> supportedExtensions = new ArrayList<>();

    static {
        supportedExtensions.add("png");
        supportedExtensions.add("jpg");
        supportedExtensions.add("jpeg");
    }

    private IOUtils() {
    }

    /**
     * Writes bytes from input stream to output stream
     *
     * @param from input stream
     * @param to   output stream
     * @throws ServiceException if i/o error occurs
     */
    public static void write(InputStream from, OutputStream to) throws ServiceException {
        try {
            int bytesRead;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = from.read(buffer)) != -1) {
                to.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * @param fileName name of file
     * @return extension in string format for example 'png', 'jpg', 'exe' etc
     */
    public static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }

        return fileName.substring(fileName.lastIndexOf('.') + FILE_EXTENSION_BIAS);
    }

    /**
     * @param fileName name of file
     * @return true if file format is supported
     */
    public static boolean isFileFormatSupported(String fileName) {
        String extension = getFileExtension(fileName);
        return supportedExtensions.contains(extension);
    }
}
