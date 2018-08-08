package com.dk.gym.command.trainer;

import com.dk.gym.command.ActionCommand;
import com.dk.gym.command.LocaleCommand;
import com.dk.gym.command.LoginCommand;
import com.dk.gym.command.LogoutCommand;
import com.dk.gym.command.admin.*;
import com.dk.gym.command.client.*;

public enum CommandType {

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),

    LOCALE(new LocaleCommand()),

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
    TRAINER_READ_PROFILE(new TrainerReadProfileCommand()),

    ORDER_CREATE(new OrderCreateCommand()),
    ORDER_READ(new OrderReadCommand()),
    ORDER_UPDATE(new OrderUpdateCommand()),
    ORDER_DELETE(new OrderDeleteCommand()),
    ORDER_READ_TRAINER(new OrderReadTrainerCommand()),
    ORDER_READ_CLIENT(new OrderReadTrainerCommand()),
    ORDER_UPDATE_CLIENT(new OrderUpdateClientCommand()),

    PRESCRIPTION_CREATE(new PrescriptionCreateTrainerCommand()),
    PRESCRIPTION_READ(new PrescriptionReadCommand()),
    PRESCRIPTION_UPDATE(new PrescriptionUpdateTrainerCommand()),
    PRESCRIPTION_DELETE(new PrescriptionDeleteTrainerCommand()),
    PRESCRIPTION_CREATE_TRAINER(new PrescriptionCreateTrainerCommand()),
    PRESCRIPTION_READ_TRAINER(new PrescriptionReadTrainerCommand()),
    PRESCRIPTION_UPDATE_TRAINER(new PrescriptionUpdateTrainerCommand()),
    PRESCRIPTION_DELETE_TRAINER(new PrescriptionUpdateTrainerCommand()),

    TRAINING_CREATE(new TrainingCreateCommand()),
    TRAINING_READ(new TrainingReadCommand()),
    TRAINING_UPDATE(new TrainingUpdateCommand()),
    TRAINING_DELETE(new TrainingDeleteCommand()),

    ACTIVITY_CREATE(new ActivityCreateCommand()),
    ACTIVITY_READ(new ActivityReadCommand()),
    ACTIVITY_UPDATE(new ActivityUpdateCommand()),
    ACTIVITY_DELETE(new ActivityDeleteCommand()),
    ACTIVITY_READ_GENERAL(new ActivityReadGeneralCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }

    public static void main(String[] args) {
        System.out.println(CommandType.ORDER_READ_CLIENT);

    }
}
