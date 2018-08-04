package com.dk.gym.entity.builder;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Training;

import static com.dk.gym.constant.ParamConstant.*;

public class TrainingDirector {

    public Training buildTraining(RequestContent content) {

        TrainingBuilder builder = new TrainingBuilder();

        buildFields(builder, content);

        return builder.getTraining();
    }

    public Training buildTraining(Training training, RequestContent content) {

        TrainingBuilder builder = new TrainingBuilder(training);

        buildFields(builder, content);

        return builder.getTraining();
    }

    private void buildFields(TrainingBuilder builder, RequestContent content) {

        builder.buildDate(content.findParameter(PARAM_DATE));
        builder.buildStartTime(content.findParameter(PARAM_START_TIME));
        builder.buildEndTime(content.findParameter(PARAM_END_TIME));
        builder.buildVisited(content.findParameter(PARAM_VISITED));
        builder.buildClientNote(content.findParameter(PARAM_CLIENT_NOTE));
        builder.buildTrainerNote(content.findParameter(PARAM_TRAINER_NOTE));
        builder.buildIdOrder(content.findParameter(PARAM_ORDER_ID));
        builder.buildIdTrainer(content.findParameter(PARAM_TRAINER_ID));
    }
}
