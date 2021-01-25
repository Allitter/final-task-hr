package com.epam.hr.domain.service.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public class LocalResource implements Resource {
    private final File file;

    public LocalResource(File file) {
        this.file = file;
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public String getContentType() {
        return URLConnection.guessContentTypeFromName(file.getName());
    }

    @Override
    public long getContentSize() {
        return file.length();
    }
}
