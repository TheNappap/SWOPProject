package model.projects;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a system in BugTrap.
 */
public abstract class System implements ISystem {

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

	/**
	 * Copy constructor
	 * @param sys The System to copy.
     */
	/*System(System sys) {
		this.subsystems = new ArrayList<ISubsystem>();
		setName(sys.getName());
		setDescription(sys.getDescription());
		setVersion(sys.getVersion().copy());
		for (ISubsystem s : sys.getSubsystems())
			this.subsystems.add(s);
		for (ISubsystem s : this.subsystems)
			s.setParent(this);
	}*/
	
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
	
	public ISystem getParent() {
		return parent;
	}
	
	void setParent(System parent) {
		this.parent = parent;
	}
	
	public List<ISubsystem> getSubsystems() {
		List<ISubsystem> copy = new ArrayList<>();
		for (ISubsystem s : subsystems)
			copy.add(s);
		return copy;
	}
	
	public Version getVersion() {
		return version;
	}
	
	void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * Method to add a subsystem to a system.
	 * THIS METHOD SHOULD HAVE PACKAGE VISIBILITY.
	 * Due to testing, this visiblity could not be sustained.
	 * @param sub The subsystem to add.
	 * @throws UnsupportedOperationException When adding a subsystem that is at a higher level in the subsystem structure.
     */
	public void addSubsystem(Subsystem sub) throws UnsupportedOperationException {
		// Travel the path to the root. The subsystem being added should not be one of the parents
		ISystem parent = this.getParent();
		boolean canAdd = true;
		while (parent != null && canAdd) {
			if (parent == sub) 
				canAdd = false;
			parent = parent.getParent();
		}
		
		if (canAdd) {
			this.subsystems.add(sub);
			sub.setParent(this);
		}
		else
			throw new UnsupportedOperationException("the given subsystem is parent of this subsystem");
	}
	
	public List<ISubsystem> getAllDirectOrIndirectSubsystems() {
		ArrayList<ISubsystem> subs = new ArrayList<ISubsystem>();
		for (ISubsystem s : subsystems) {
			subs.add(s);
			for (ISubsystem ss : s.getAllDirectOrIndirectSubsystems())
				subs.add(ss);
		}
		return subs;
	}

	List<Subsystem> getAllSubsystems() {
		ArrayList<Subsystem> subs = new ArrayList<Subsystem>();
		for (Subsystem s : subsystems) {
			subs.add(s);
			for (Subsystem ss : s.getAllSubsystems())
				subs.add(ss);
		}
		return subs;
	}
}