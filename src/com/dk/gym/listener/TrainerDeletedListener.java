package com.dk.gym.listener;

import com.dk.gym.command.CommandType;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.mail.EmailSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dk.gym.resource.LocaleManager;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import static com.dk.gym.constant.ParamConstant.*;

@WebListener
public class TrainerDeletedListener implements ServletRequestListener {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();

        String id = req.getParameter(PARAM_ID);
        String command = req.getParameter(PARAM_COMMAND);
        String error = (String)req.getAttribute(PARAM_ERROR);

        if (error != null && command != null) {
            if(error.equals(LocaleManager.getProperty(ReturnMessageType.DONE.getProperty()))
                    && command.equalsIgnoreCase(CommandType.TRAINER_DELETE.toString())) {
                new Thread(() -> EmailSender.sendMail("dmi.kovaliov@gmail.com", "User deleted", "Trainer: " + id)).start();
            }
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
    }
}
