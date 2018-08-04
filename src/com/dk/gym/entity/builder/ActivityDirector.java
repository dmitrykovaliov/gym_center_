package com.dk.gym.entity.builder;

import com.dk.gym.entity.Activity;
import com.dk.gym.controller.RequestContent;

import static com.dk.gym.constant.ParamConstant.*;

public class ActivityDirector {

    public Activity buildActivity(RequestContent content) {

        ActivityBuilder builder = new ActivityBuilder();

        buildFields(builder, content);

        return builder.getActivity();
    }

    public Activity buildActivity(Activity activity, RequestContent content) {

        ActivityBuilder builder = new ActivityBuilder(activity);

        buildFields(builder, content);

        return builder.getActivity();
    }

    private void buildFields(ActivityBuilder builder, RequestContent content) {

        builder.buildName(content.findParameter(PARAM_NAME));
        builder.buildPrice(content.findParameter(PARAM_PRICE));
        builder.buildNote(content.findParameter(PARAM_NOTE));
    }
}
