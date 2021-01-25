package com.epam.hr.domain.service.resource;

import com.epam.hr.domain.util.IOUtils;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class PartResource implements Resource {
    private final Part part;
    private final String name;

    public PartResource(Part part, String fileDir) {
        this.part = part;
        String extension = IOUtils.getFileExtension(part.getSubmittedFileName());
        this.name = String.format("%s/%s.%s", fileDir, UUID.randomUUID(), extension);
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return part.getInputStream();
    }

    @Override
    public String getContentType() {
        return part.getContentType();
    }

    @Override
    public long getContentSize() {
        return part.getSize();
    }
}
