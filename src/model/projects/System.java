package model.projects;

import java.util.ArrayList;

public abstract class System {

	private String name;
	private String description;
	private System parent;
	private ArrayList<Subsystem> subsystems;
	private Version version;
	
	System(String name, String description, System parent, Version version) {
		this.subsystems = new ArrayList<Subsystem>();
		
		setName(name);
		setDescription(description);
		setVersion(version);
		setParent(parent);

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
	
	void addSubsystem(Subsystem sub) throws UnsupportedOperationException {
		// Travel the path to the root. The subsystem being added should not be one of the parents
		System parent = this.getParent();
		boolean canAdd = true;
		while (parent != null && canAdd) {
			if (parent == sub) 
				canAdd = false;
			parent = parent.getParent();
		}
		
		if (canAdd)
			this.subsystems.add(sub);
		else
			throw new UnsupportedOperationException("the given subsystem is parent of this subsystem");
	}
	
	public ArrayList<Subsystem> getAllDirectOrIndirectSubsystems() {
		ArrayList<Subsystem> subs = new ArrayList<Subsystem>();
		for (Subsystem s : subsystems) {
			subs.add(s);
			for (Subsystem ss : s.getSubsystems())
				subs.add(ss);
		}
		return subs;
	}
}