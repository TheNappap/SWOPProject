package model.projects;

import java.util.ArrayList;

public abstract class System {

	private String name;
	private String description;
	private System parent;
	private ArrayList<String> subsystems;
	private Version version;

}