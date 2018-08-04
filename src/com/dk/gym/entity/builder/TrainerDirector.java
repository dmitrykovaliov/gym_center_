package com.dk.gym.entity.builder;

import com.dk.gym.entity.Trainer;
import com.dk.gym.controller.RequestContent;

import static com.dk.gym.constant.ParamConstant.*;

public class TrainerDirector {

    public Trainer buildTrainer(RequestContent content) {

        TrainerBuilder builder = new TrainerBuilder();

        buildFields(builder, content);

        return builder.getTrainer();
    }

    public Trainer buildTrainer(Trainer trainer, RequestContent content) {

        TrainerBuilder builder = new TrainerBuilder(trainer);

        buildFields(builder, content);

        return builder.getTrainer();
    }

    private void buildFields(TrainerBuilder builder, RequestContent content) {

        builder.buildName(content.findParameter(PARAM_NAME));
        builder.buildLastName(content.findParameter(PARAM_LASTNAME));
        builder.buildPhone(content.findParameter(PARAM_PHONE));
        builder.buildPersonalData(content.findParameter(PARAM_PERSONAL_DATA));
        builder.buildPicPath((String) content.findAttribute(PARAM_ICONPATH));
    }
}
