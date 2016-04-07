package model.projects;

import model.notifications.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a system in BugTrap.
 */
public abstract class System extends Observable implements ISystem {

	protected String name;		//System name.
	protected String description;	//System description.
	protected final System parent;		//Parent System, if any.
	protected final List<Subsystem> subsystems;	//Subsystems.

	
	public System(String name, String description, System parent, List<Subsystem> subsystems) {
		this.name 			= name;
		this.description 	= description;
		this.parent 		= parent;
		this.subsystems		= subsystems;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public ISystem getParent() {
		return parent;
	}
	
	@Override
	public List<ISubsystem> getSubsystems() {
		List<ISubsystem> copy = new ArrayList<>(); copy.addAll(subsystems);
		return copy;
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

	public List<Subsystem> getAllSubsystems() {
		ArrayList<Subsystem> subs = new ArrayList<Subsystem>();
		for (Subsystem s : subsystems) {
			subs.add(s);
			for (Subsystem ss : s.getAllSubsystems())
				subs.add(ss);
		}
		return subs;
	}

	@Override
	public String getInfo() {
		return getName();
	}
}