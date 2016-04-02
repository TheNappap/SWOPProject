package model;

import controllers.exceptions.UnauthorizedAccessException;

public abstract class Command {
    private BugTrap bugTrap;

    public Command(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
    }

    public abstract void execute() throws UnauthorizedAccessException;

    public BugTrap getBugTrap() {
        return this.bugTrap;
    }
}
