package model;

import controllers.exceptions.UnauthorizedAccessException;

public abstract class Command {
    private BugTrap bugTrap;

    /**
     * Command to do this with the System.
     * @param bugTrap BugTrap system.
     */
    public Command(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
    }

    /**
     * Execute the command.
     * @throws UnauthorizedAccessException if the logged in User is not allowed to do execute this command.
     */
    public abstract void execute() throws UnauthorizedAccessException;

    /**
     * 
     * @return The BugTrap system.
     */
    public BugTrap getBugTrap() {
        return this.bugTrap;
    }
}
