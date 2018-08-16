package com.dk.gym.command;

import com.dk.gym.command.admin.*;
import com.dk.gym.command.client.*;
import com.dk.gym.command.general.LocaleEnCommand;
import com.dk.gym.command.general.LocaleRuCommand;
import com.dk.gym.command.general.LoginCommand;
import com.dk.gym.command.general.LogoutCommand;
import com.dk.gym.command.trainer.*;

/**
 * The Enum CommandType.
 */
public enum CommandType {

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),

    LOCALE_EN(new LocaleEnCommand()),
    LOCALE_RU(new LocaleRuCommand()),

    USER_CREATE(new UserCreateCommand()),
    USER_READ(new UserReadCommand()),
    USER_DELETE(new UserDeleteCommand()),

    CLIENT_CREATE(new ClientCreateCommand()),
    CLIENT_READ(new ClientReadCommand()),
    CLIENT_UPDATE(new ClientUpdateCommand()),
    CLIENT_DELETE(new ClientDeleteCommand()),
    CLIENT_READ_TRAINER(new ClientReadTrainerCommand()),
    CLIENT_READ_PROFILE(new ProfileReadClientCommand()),

    TRAINER_CREATE(new TrainerCreateCommand()),
    TRAINER_READ(new TrainerReadCommand()),
    TRAINER_UPDATE(new TrainerUpdateCommand()),
    TRAINER_DELETE(new TrainerDeleteCommand()),
    TRAINER_READ_PROFILE(new ProfileReadTrainerCommand()),

    ORDER_CREATE(new OrderCreateCommand()),
    ORDER_READ(new OrderReadCommand()),
    ORDER_UPDATE(new OrderUpdateCommand()),
    ORDER_DELETE(new OrderDeleteCommand()),
    ORDER_READ_TRAINER(new OrderReadTrainerCommand()),
    ORDER_READ_CLIENT(new OrderReadClientCommand()),
    ORDER_UPDATE_CLIENT(new OrderUpdateClientCommand()),

    PRESCRIPTION_CREATE(new PrescriptionCreateCommand()),
    PRESCRIPTION_READ(new PrescriptionReadCommand()),
    PRESCRIPTION_UPDATE(new PrescriptionUpdateCommand()),
    PRESCRIPTION_DELETE(new PrescriptionDeleteCommand()),
    PRESCRIPTION_CREATE_TRAINER(new PrescriptionCreateTrainerCommand()),
    PRESCRIPTION_READ_TRAINER(new PrescriptionReadTrainerCommand()),
    PRESCRIPTION_UPDATE_TRAINER(new PrescriptionUpdateTrainerCommand()),
    PRESCRIPTION_DELETE_TRAINER(new PrescriptionDeleteTrainerCommand()),
    PRESCRIPTION_READ_CLIENT(new PrescriptionReadClientCommand()),
    PRESCRIPTION_UPDATE_CLIENT(new PrescriptionUpdateClientCommand()),

    TRAINING_CREATE(new TrainingCreateCommand()),
    TRAINING_READ(new TrainingReadCommand()),
    TRAINING_UPDATE(new TrainingUpdateCommand()),
    TRAINING_DELETE(new TrainingDeleteCommand()),
    TRAINING_READ_TRAINER(new TrainingReadTrainerCommand()),
    TRAINING_UPDATE_TRAINER(new TrainingUpdateTrainerCommand()),
    TRAINING_READ_CLIENT(new TrainingReadClientCommand()),
    TRAINING_UPDATE_CLIENT(new TrainingUpdateClientCommand()),

    ACTIVITY_CREATE(new ActivityCreateCommand()),
    ACTIVITY_READ(new ActivityReadCommand()),
    ACTIVITY_UPDATE(new ActivityUpdateCommand()),
    ACTIVITY_DELETE(new ActivityDeleteCommand()),
    ACTIVITY_READ_GENERAL(new ActivityReadTrainerCommand());

    /** The command. */
    private ActionCommand command;

    /**
     * Instantiates a new command type.
     *
     * @param command the command
     */
    CommandType(ActionCommand command) {
        this.command = command;
    }

    /**
     * Gets the command.
     *
     * @return the command
     */
    public ActionCommand getCommand() {
        return command;
    }
}
