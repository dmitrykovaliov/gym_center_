package com.dk.gym.listener;

import com.dk.gym.command.CommandType;
import com.dk.gym.command.ReturnMessageType;
import com.dk.gym.mail.EmailSender;
import com.dk.gym.resource.LocaleManager;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import static com.dk.gym.service.ParamConstant.*;

@WebListener
public class UserDeletedListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest req = (HttpServletRequest) servletRequestEvent.getServletRequest();

        String id = req.getParameter(PARAM_ID);
        String command = req.getParameter(PARAM_COMMAND);
        String error = (String) req.getAttribute(PARAM_ERROR);

        if (command != null
                && error != null
                && command.equalsIgnoreCase(CommandType.USER_DELETE.toString())
                && error.equals(LocaleManager.getProperty(ReturnMessageType.DONE.getProperty()))) {

            new Thread(() -> EmailSender.sendMail("dmi.kovaliov@gmail.com",
                    "User deleted", "User: " + id)).start();
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        //empty
    }
}
