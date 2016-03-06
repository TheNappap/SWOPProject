package controllers;

import model.BugTrap;

public abstract class Controller {

	private final BugTrap bugTrap;
	
	public Controller(BugTrap bugTrap) {
		this.bugTrap = bugTrap;
	}

	public BugTrap getBugTrap() {
		return bugTrap;
	}
}
