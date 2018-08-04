package com.dk.gym.entity.builder;

import com.dk.gym.controller.RequestContent;
import com.dk.gym.entity.Prescription;

import static com.dk.gym.constant.ParamConstant.*;

public class PrescriptionDirector {

    public Prescription buildPrescription(RequestContent content) {

        PrescriptionBuilder builder = new PrescriptionBuilder();

        buildFields(builder, content);

        return builder.getPrescription();
    }

    public Prescription buildPrescription(Prescription prescription, RequestContent content) {

        PrescriptionBuilder builder = new PrescriptionBuilder(prescription);

        buildFields(builder, content);

        return builder.getPrescription();
    }

    private void buildFields(PrescriptionBuilder builder, RequestContent content) {

        builder.buildDate(content.findParameter(PARAM_DATE));
        builder.buildWeekQuantity(content.findParameter(PARAM_WEEKS));
        builder.buildTrainingsWeek(content.findParameter(PARAM_TRAININGS_WEEK));
        builder.buildTrainerNote(content.findParameter(PARAM_TRAINER_NOTE));
        builder.buildClientNote(content.findParameter(PARAM_CLIENT_NOTE));
        builder.buildAgreedDate(content.findParameter(PARAM_AGREED));

    }
}
