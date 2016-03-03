package model.projects;

import java.util.ArrayList;

public abstract class System {

	private String name;
	private String description;
	private System parent;
	private ArrayList<Subsystem> subsystems;
	private Version version;
	
	System(String name, String description, System parent, ArrayList<Subsystem> subsystems, Version version) {
		subsystems = new ArrayList<Subsystem>();
		
		setName(name);
		setDescription(description);
		setVersion(version);
		setParent(parent);
		
		if (subsystems != null) {
			for (Subsystem sub : subsystems)
				addSubsystem(sub);
		}
	}
	
	public String getName() {
		return name;
	}
	
	void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	void setDescription(String description) {
		this.description = description;
	}
	
	public System getParent() {
		return parent;
	}
	
	void setParent(System parent) {
		this.parent = parent;
	}
	
	public ArrayList<Subsystem> getSubsystems() {
		return subsystems;
	}
	
	public Version getVersion() {
		return version;
	}
	
	void setVersion(Version version) {
		this.version = version;
	}
	
	void addSubsystem(Subsystem sub) {
		this.subsystems.add(sub);
	}
}