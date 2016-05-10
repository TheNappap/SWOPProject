package model;

import controllers.exceptions.UnauthorizedAccessException;

import java.util.IllegalFormatException;

public abstract class Command {
	
    private BugTrap bugTrap; //BugTrap system

    /**
     * Command to do this with the System.
     * @param bugTrap BugTrap system.
     */
    public Command(BugTrap bugTrap, Form form) {
        this.bugTrap = bugTrap;
        if (form == null)
            throw new IllegalArgumentException("Form cannot be null");
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
