package model.projects.forms;

import model.Form;
import model.projects.ISubsystem;

/**
 * Form used to store temporary data to create a subsystem.
 */
public class MergeSubsystemForm implements Form {

	private String name;
	private String description;
	private ISubsystem subsystem1;
	private ISubsystem subsystem2;
		
	public MergeSubsystemForm() {
		
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) throw new NullPointerException("Given name is null.");
		
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) throw new NullPointerException("Given description is null.");
		
		this.description = description;
	}

	public ISubsystem getSubsystem1() {
		return subsystem1;
	}

	public void setSubsystem1(ISubsystem subsystem1) {
		if (subsystem1 == null) throw new NullPointerException("subsystem1 is null");
		
		this.subsystem1 = subsystem1;
	}

	public ISubsystem getSubsystem2() {
		return subsystem2;
	}

	public void setSubsystem2(ISubsystem subsystem2) {
		if (subsystem2 == null) throw new NullPointerException("subsystem2 is null");
		
		this.subsystem2 = subsystem2;
	}

	@Override
	public void allVarsFilledIn() {
		if (getName() == null) throw new NullPointerException("Name is null");
		if (getDescription() == null) throw new NullPointerException("Description is null");
		if (getSubsystem1() == null) throw new NullPointerException("Subsystem1 is null");
		if (getSubsystem2() == null) throw new NullPointerException("Subsystem2 is null");
	}

}
