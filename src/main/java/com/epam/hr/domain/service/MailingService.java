package com.epam.hr.domain.service;

import com.epam.hr.exception.LogicRuntimeException;
import com.epam.hr.exception.ServiceException;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class MailingService {
    private static final String MAIL_PROPERTIES_PATH = "properties/mail.properties";
    private static final String HOST_MAIL_ADDRESS_PROPERTY = "mail.host.address";
    private static final String HOST_MAIL_USER_NAME_PROPERTY = "mail.host.user.name";
    private static final String HOST_MAIL_USER_PASSWORD_PROPERTY = "mail.host.user.password";
    private final InternetAddress hostAddress;
    private final Properties mailProperties;
    private final String userName;
    private final String password;


    public MailingService() {
        mailProperties = new Properties();
        try {
            mailProperties.load(getClass()
                    .getClassLoader()
                    .getResourceAsStream(MAIL_PROPERTIES_PATH));
        } catch (IOException e) {
            throw new LogicRuntimeException(e);
        }

        try {
            hostAddress = new InternetAddress(mailProperties.getProperty(HOST_MAIL_ADDRESS_PROPERTY));
        } catch (AddressException e) {
            throw new LogicRuntimeException(e);
        }

        userName = mailProperties.getProperty(HOST_MAIL_USER_NAME_PROPERTY);
        password = mailProperties.getProperty(HOST_MAIL_USER_PASSWORD_PROPERTY);
    }


    public void sendMessageTo(String subject, String messageText, String recipient) throws ServiceException {
        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(hostAddress);

            InternetAddress recipientAddress = new InternetAddress(recipient);
            message.setRecipient(Message.RecipientType.TO, recipientAddress);

            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new ServiceException(e);
        }
    }

}
