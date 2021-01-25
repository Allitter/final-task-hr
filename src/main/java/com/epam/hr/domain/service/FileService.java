package com.epam.hr.domain.service;

import com.epam.hr.domain.service.resource.Resource;
import com.epam.hr.exception.ServiceException;

/**
 * Saves and provides resources
 */
public interface FileService {

    /**
     * Provides resource by local name
     *
     * @param name local resource name that is stored in database for example 'den/img.png'
     * @return {@link Resource}
     * @throws ServiceException if init or i/o error occurs
     */
    Resource getResource(String name) throws ServiceException;

    /**
     * Saves resource to storage place
     *
     * @param resource resource to store
     * @throws ServiceException if init or i/o error occurs
     */
    void save(Resource resource) throws ServiceException;
}
