package com.dk.gym.command;

import com.dk.gym.constant.PageConstant;
import com.dk.gym.entity.Role;
import com.dk.gym.exception.CommandException;
import com.dk.gym.exception.ServiceException;
import com.dk.gym.page.ContentPage;
import com.dk.gym.page.PageType;
import com.dk.gym.service.UserService;
import com.dk.gym.service.impl.UserServiceImpl;
import com.dk.gym.servlet.RequestContent;
import com.dk.gym.validator.ChainPassValidator;
import com.dk.gym.validator.BaseValidator;
import com.dk.gym.validator.LengthValidator;
import com.dk.gym.validator.NotEmptyValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resource.LocaleManager;

import static com.dk.gym.constant.ParamConstant.*;


public class LoginCommand implements ActionCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public ContentPage execute(RequestContent content) throws CommandException {

        String pageUrl;
        Role role;
        UserService service = UserServiceImpl.getInstance();

        //withdraw login and password
        String login = content.findParameter(PARAM_LOGIN);
        String pass = content.findParameter(PARAM_PASS);

        LOGGER.log(Level.DEBUG, String.format("Login: %s, pass: %s", login, pass));

        BaseValidator notEmptyValidator = new NotEmptyValidator();

        if (notEmptyValidator.validate(login) && notEmptyValidator.validate(pass)) {
            if (new LengthValidator(4, 15).validate(login) && new ChainPassValidator().validate(pass)) {
                try {
                    role = service.checkLogin(login, pass);
                    LOGGER.log(Level.DEBUG, "User's role: " + role);
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }

                if (role != null) {

                    content.insertSessionAttribute(PARAM_USER, role.toString());

                    pageUrl = PageConstant.PAGE_MAIN;

                } else {

                    content.insertSessionAttribute(PARAM_ERROR,
                            LocaleManager.getProperty("message.info.noUser"));
                    pageUrl = PageConstant.PAGE_INDEX;
                }
            } else {
                content.insertSessionAttribute(PARAM_ERROR,
                        LocaleManager.getProperty("message.error.invalid"));
                pageUrl = PageConstant.PAGE_INDEX;
            }
        } else {
            content.insertSessionAttribute(PARAM_ERROR,
                    LocaleManager.getProperty("message.info.emptyLine"));

            pageUrl = PageConstant.PAGE_INDEX;
        }

//        content.insertSessionAttribute(ParamConstant.ATTR_URL_QUERY, pageUrl);

        return new ContentPage(PageType.REDIRECT, pageUrl);
    }
}
