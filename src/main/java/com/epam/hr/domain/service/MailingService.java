package com.epam.hr.domain.service;

import com.epam.hr.exception.ServiceException;

/**
 * Provides ability to work with mail
 */
public interface MailingService {

    /**
     * Sends message to recipient
     *
     * @param subject     subject of the message
     * @param messageText message's text
     * @param recipient   recipient's e-mail address
     * @throws ServiceException if error occurs
     */
    void sendMessageTo(String subject, String messageText, String recipient) throws ServiceException;

}
