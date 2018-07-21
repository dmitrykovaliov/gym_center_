package com.dk.gym.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * The class provides configuration of email session.
 *
 * @author Pashchuk Ksenia
 */

public class EmailSessionCreator {

    private final String USER_NAME = "ina.pashchuk@gmail.com";
    private final String USER_PASSWORD = "tomoe127";
    private Properties sessionProperties;

    public EmailSessionCreator() {
        sessionProperties = new Properties();
        sessionProperties.setProperty("mail.transport.protocol", "smtp");
        sessionProperties.setProperty("mail.host", "smtp.gmail.com");
        sessionProperties.put("mail.smtp.auth", "true");
        sessionProperties.put("mail.smtp.port", "465");
        sessionProperties.put("mail.smtp.socketFactory.port", "465");
        sessionProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProperties.put("mail.smtp.socketFactory.fallback", "false");
    }

    public Session createSession() {
        return Session.getDefaultInstance(sessionProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, USER_PASSWORD);
                    }
                });
    }
}
