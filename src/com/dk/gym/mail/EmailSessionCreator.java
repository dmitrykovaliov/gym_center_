package com.dk.gym.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class EmailSessionCreator {

    private static final String USER_NAME = "gym.test.2018@gmail.com";
    private static final String USER_PASS = "12345A6789B";
    private Properties sessionProperties;

    EmailSessionCreator() {
        sessionProperties = new Properties();
        sessionProperties.setProperty("mail.transport.protocol", "smtp");
        sessionProperties.setProperty("mail.host", "smtp.gmail.com");
        sessionProperties.setProperty("mail.smtp.auth", "true");
        sessionProperties.setProperty("mail.smtp.port", "465");
        sessionProperties.setProperty("mail.smtp.socketFactory.port", "465");
        sessionProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
    }

    Session createSession() {
        return Session.getDefaultInstance(sessionProperties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USER_NAME, USER_PASS);
                    }
                });
    }
}
