package model.projects.forms;

import java.util.ArrayList;
import java.util.List;

import model.Form;
import model.bugreports.IBugReport;
import model.projects.ISubsystem;

/**
 * Form used to store temporary data to split a subsystem.
 */
public class SplitSubsystemForm implements Form {

	private ISubsystem subsystem;
	private String name1;
	private String description1;
	private String name2;
	private String description2;
	private List<IBugReport> bugReports1 = new ArrayList<>();
	private List<ISubsystem> subsystems1 = new ArrayList<>();
		
	public SplitSubsystemForm() {
		
	}
	
	public ISubsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(ISubsystem subsystem) {
		if (subsystem == null) throw new NullPointerException("subsystem is null");
		
		this.subsystem = subsystem;
	}
		
	public String getName1() {
		return name1;
	}

	public void setName1(String name) {
		if (name == null) throw new NullPointerException("Given name is null.");
		
		this.name1 = name;
	}

	public String getDescription1() {
		return description1;
	}

	public void setDescription1(String description) {
		if (description == null) throw new NullPointerException("Given description is null.");
		
		this.description1 = description;
	}
	
	public String getName2() {
		return name2;
	}

	public void setName2(String name) {
		if (name == null) throw new NullPointerException("Given name is null.");
		
		this.name2 = name;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description) {
		if (description == null) throw new NullPointerException("Given description is null.");
		
		this.description2 = description;
	}

	public List<IBugReport> getBugReports1() {
		return bugReports1;
	}

	public void setBugReports1(List<IBugReport> bugreports) {
		if (bugreports == null) throw new NullPointerException("bugreports is null");
		
		this.bugReports1 = bugreports;
	}
	
	public List<ISubsystem> getSubsystems1() {
		return subsystems1;
	}

	public void setSubsystems1(List<ISubsystem> subsystems1) {
		if (subsystems1 == null) throw new NullPointerException("subsystems1 is null");
		
		this.subsystems1 = subsystems1;
	}

	@Override
	public void allVarsFilledIn() {
		if (getSubsystem() == null) throw new NullPointerException("Subsystem is null");
		if (getName1() == null) throw new NullPointerException("Name1 is null");
		if (getDescription1() == null) throw new NullPointerException("Description1 is null");
		if (getName2() == null) throw new NullPointerException("Name2 is null");
		if (getDescription2() == null) throw new NullPointerException("Description2 is null");
		if (getBugReports1() == null) throw new NullPointerException("BugReports1 is null");
		if (getSubsystems1() == null) throw new NullPointerException("Subsystems1 is null");
	}

}
