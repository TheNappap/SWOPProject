package model.projects;

import java.util.List;

/**
 * This class represents a subsystem in a system.
 */
public class Subsystem extends System implements ISubsystem {

	private final Project project;
	
	public Subsystem(String name, String description, System parent, List<Subsystem> subsystems, Project project) {
		super(name, description, parent, subsystems);
		
		this.project = project;
		
		parent.subsystems.add(this);
	}

	@Override
	public IProject getProject() {
		return project;
	}
}
