package projects;

import java.util.ArrayList;

public abstract class System {

	private String name;
	private String description;
	private System parent;
	private ArrayList<Subsystem> subsystems;
	private Version version;

}