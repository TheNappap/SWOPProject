package controllers;

import model.BugTrap;

/**
 * Base class for every controller.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public abstract class Controller {

	private final BugTrap bugTrap;
	
	public Controller(BugTrap bugTrap) {
		this.bugTrap = bugTrap;
	}

	public BugTrap getBugTrap() {
		return bugTrap;
	}
}
