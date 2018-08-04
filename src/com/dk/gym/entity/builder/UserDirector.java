package com.dk.gym.entity.builder;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.User;

import static com.dk.gym.constant.ParamConstant.*;

public class UserDirector {

    public User buildUser(RequestContent content) {

        UserBuilder builder = new UserBuilder();

        buildFields(builder, content);

        return builder.getUser();
    }

    public User buildUser(User user, RequestContent content) {

        UserBuilder builder = new UserBuilder(user);

        buildFields(builder, content);

        return builder.getUser();
    }

    private void buildFields(UserBuilder builder, RequestContent content) {

        builder.buildLogin(content.findParameter(PARAM_LOGIN));
        builder.buildPass(content.findParameter(PARAM_PASS));
        builder.buildRole(content.findParameter(PARAM_ROLE));
    }


}
