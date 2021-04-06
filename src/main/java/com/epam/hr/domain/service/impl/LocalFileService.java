package com.epam.hr.domain.service.impl;

import com.epam.hr.domain.service.FileService;
import com.epam.hr.domain.service.resource.LocalResource;
import com.epam.hr.domain.service.resource.Resource;
import com.epam.hr.domain.util.IOUtils;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ServiceRuntimeException;

import java.io.*;
import java.util.Properties;

public class LocalFileService implements FileService {
    private static final String APP_PROPERTIES_PATH = "properties/app.properties";
    private static final String FILES_WORK_PATH_PROPERTY = "files.work.path";
    private final String filesWorkPath;

    public LocalFileService() {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(APP_PROPERTIES_PATH));
            filesWorkPath = properties.getProperty(FILES_WORK_PATH_PROPERTY);
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }
    }

    @Override
    public Resource getResource(String name) {
        String filePath = String.format("%s/%s", filesWorkPath, name);
        File downloadFile = new File(filePath);

        return new LocalResource(downloadFile);
    }

    @Override
    public void save(Resource resource) throws ServiceException {
        try {
            String filePath = String.format("%s/%s", filesWorkPath, resource.getFileName());
            File file = new File(filePath);
            file.getParentFile().mkdirs();

            try (OutputStream outStream = new FileOutputStream(file);
                 InputStream inStream = resource.getInputStream()) {

                IOUtils.write(inStream, outStream);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
