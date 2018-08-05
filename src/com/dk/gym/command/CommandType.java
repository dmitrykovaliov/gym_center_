package com.dk.gym.command;

import com.dk.gym.command.admin.*;

public enum CommandType {

    LOGIN (new LoginCommand()),
    LOGOUT (new LogoutCommand()),

    LOCALE(new LocaleCommand()),

    USER_CREATE(new UserCreateCommand()),
    USER_READ(new UserReadCommand()),
    USER_DELETE(new UserDeleteCommand()),

    CLIENT_CREATE(new ClientCreateCommand()),
    CLIENT_READ(new ClientReadCommand()),
    CLIENT_UPDATE(new ClientUpdateCommand()),
    CLIENT_DELETE(new ClientDeleteCommand()),

    TRAINER_CREATE(new TrainerCreateCommand()),
    TRAINER_READ(new TrainerReadCommand()),
    TRAINER_UPDATE(new TrainerUpdateCommand()),
    TRAINER_DELETE(new TrainerDeleteCommand()),

    ORDER_CREATE(new OrderCreateCommand()),
    ORDER_READ(new OrderReadCommand()),
    ORDER_UPDATE(new OrderUpdateCommand()),
    ORDER_DELETE(new OrderDeleteCommand()),

    PRESCRIPTION_CREATE(new PrescriptionCreateCommand()),
    PRESCRIPTION_READ(new PrescriptionReadCommand()),
    PRESCRIPTION_UPDATE(new PrescriptionUpdateCommand()),
    PRESCRIPTION_DELETE(new PrescriptionDeleteCommand()),

    TRAINING_CREATE(new TrainingCreateCommand()),
    TRAINING_READ(new TrainingReadCommand()),
    TRAINING_UPDATE(new TrainingUpdateCommand()),
    TRAINING_DELETE(new TrainingDeleteCommand()),

    ACTIVITY_CREATE(new ActivityCreateCommand()),
    ACTIVITY_READ(new ActivityReadCommand()),
    ACTIVITY_UPDATE(new ActivityUpdateCommand()),
    ACTIVITY_DELETE(new ActivityDeleteCommand());

    private ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
