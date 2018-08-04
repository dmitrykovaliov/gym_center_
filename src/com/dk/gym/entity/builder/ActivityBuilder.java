package com.dk.gym.entity.builder;

import com.dk.gym.entity.Activity;
import com.dk.gym.validator.NotEmptyValidator;

import java.math.BigDecimal;

class ActivityBuilder {

    private Activity activity;

    public ActivityBuilder() {
        activity = new Activity();
    }
    public ActivityBuilder(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public void buildName(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            activity.setName(parameter);
        }
    }
    public void buildPrice(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            activity.setPrice(new BigDecimal(parameter));
        }
    }
    public void buildNote(String parameter) {
        if (new NotEmptyValidator().validate(parameter)) {
            activity.setNote(parameter);
        }
    }
}

