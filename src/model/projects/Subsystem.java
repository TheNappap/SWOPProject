package model.projects;

import java.util.List;

/**
 * This class represents a subsystem in a system.
 */
public class Subsystem extends System implements ISubsystem {

	private final Project project;
	
	public Subsystem(String name, String description, System parent, List<Subsystem> subsystems, Project project, AchievedMilestone achievedMilestone) {
		super(name, description, parent, subsystems, achievedMilestone);
		
		this.project = project;
		
		parent.subsystems.add(this);
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;

		// System.equals compares parents until the root.
		// If same root, then same project.
		// No need to compare here.
		return true;
	}
}
