package com.epam.hr.domain.service.resource;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;

import java.io.InputStream;
import java.nio.channels.Channels;


public class BlobResource implements Resource {
    private final Blob blob;

    public BlobResource(Blob blob) {
        this.blob = blob;
    }

    @Override
    public String getFileName() {
        return blob.getName();
    }

    @Override
    public InputStream getInputStream() {
        ReadChannel reader = blob.reader();
        return Channels.newInputStream(reader);
    }

    @Override
    public String getContentType() {
        return blob.getContentType();
    }

    @Override
    public long getContentSize() {
        return blob.getSize();
    }

}
