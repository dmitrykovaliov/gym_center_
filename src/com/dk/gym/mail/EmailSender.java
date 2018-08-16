package com.dk.gym.mail;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The Class EmailSender. Creates and send message.
 */
public class EmailSender {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Instantiates a new email sender.
     */
    private EmailSender() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Send mail.
     *
     * @param sendToEmail the send to email
     * @param mailSubject the mail subject
     * @param mailText the mail text
     * @return true, if successful
     */
    public static boolean sendMail(String sendToEmail, String mailSubject, String mailText) {
        Session mailSession = new EmailSessionCreator().createSession();
        mailSession.setDebug(true);

        MimeMessage message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setText(mailText);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            LOGGER.log(Level.ERROR, "Email not sent: ", e);
        }
        return false;
    }

}
