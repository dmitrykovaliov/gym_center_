package com.dk.gym.command;

public enum CommandType {

    LOGIN (new LoginCommand()),
    LOGOUT (new LogoutCommand()),

    ACTIVITY_CREATE(new ActivityCreateCommand()),
    ACTIVITY_READ(new ActivityReadCommand()),
    ACTIVITY_UPDATE(new ActivityUpdateCommand()),
    ACTIVITY_DELETE(new ActivityDeleteCommand()),

    EN(new EnCommand()),
    RU(new RuCommand());

    ActionCommand command;

    CommandType(ActionCommand command) {
        this.command = command;
    }

    public ActionCommand getCommand() {
        return command;
    }
}
