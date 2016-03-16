package model.projects;

/**
 * This class represents a subsystem in a system.
 */
public class Subsystem extends System {

	private Project project;
	
	public Subsystem(String name, String description, System parent, Version version, Project project) {
		super(name, description, parent, version);
		setProject(project);
	}
	
	public Project getProject() {
		return project;
	}
	
	void setProject(Project project) {
		this.project = project;
	}
}
