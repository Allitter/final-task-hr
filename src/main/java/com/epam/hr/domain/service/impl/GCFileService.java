package com.epam.hr.domain.service.impl;

import com.epam.hr.domain.service.FileService;
import com.epam.hr.domain.service.resource.BlobResource;
import com.epam.hr.domain.service.resource.Resource;
import com.epam.hr.exception.ServiceException;
import com.epam.hr.exception.ServiceRuntimeException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import java.io.IOException;
import java.io.InputStream;

public class GCFileService implements FileService {
    private static final String BUCKET_NAME = "hr-final";
    private static final String PROJECT_NAME = "hr-final";
    private static final String GOOGLE_AUTH_FILE = "hr-final-7de32c2a3e56.json";
    private final Storage storage;

    public GCFileService() {
        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream authFileStream = classLoader.getResourceAsStream(GOOGLE_AUTH_FILE);
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(authFileStream);
            StorageOptions storageOptions = StorageOptions.newBuilder()
                    .setProjectId(PROJECT_NAME).setCredentials(googleCredentials).build();
            storage = storageOptions.getService();
        } catch (IOException e) {
            throw new ServiceRuntimeException(e);
        }
    }

    @Override
    public Resource getResource(String name) {
        BlobId blobId = BlobId.of(BUCKET_NAME, name);
        Blob blob = storage.get(blobId);
        return new BlobResource(blob);
    }

    @Override
    public void save(Resource resource) throws ServiceException {
        try {
            BlobId blobId = BlobId.of(BUCKET_NAME, resource.getFileName());
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(resource.getContentType()).build();
            storage.createFrom(blobInfo, resource.getInputStream());
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
