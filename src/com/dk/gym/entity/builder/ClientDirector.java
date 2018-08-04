package com.dk.gym.entity.builder;

import com.dk.gym.entity.Client;
import com.dk.gym.controller.RequestContent;

import static com.dk.gym.constant.ParamConstant.*;

public class ClientDirector {

    public Client buildClient(RequestContent content) {

        ClientBuilder builder = new ClientBuilder();

        buildFields(builder, content);

        return builder.getClient();
    }

    public Client buildClient(Client client, RequestContent content) {

        ClientBuilder builder = new ClientBuilder(client);

        buildFields(builder, content);

        return builder.getClient();
    }

    private void buildFields(ClientBuilder builder, RequestContent content) {

        builder.buildName(content.findParameter(PARAM_NAME));
        builder.buildLastName(content.findParameter(PARAM_LASTNAME));
        builder.buildPhone(content.findParameter(PARAM_PHONE));
        builder.buildEmail(content.findParameter(PARAM_EMAIL));
        builder.buildPersonalData(content.findParameter(PARAM_PERSONAL_DATA));
        builder.buildPicPath((String)content.findAttribute(PARAM_ICONPATH));
    }
}
