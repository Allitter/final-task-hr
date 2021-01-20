package com.epam.hr.domain.service;

import com.epam.hr.exception.ServiceException;

public interface MailingService {
    void sendMessageTo(String subject, String messageText, String recipient) throws ServiceException;
}
