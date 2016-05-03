package model.projects;

import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a subsystem in a system.
 */
public class Subsystem extends System implements ISubsystem {

	private Project project;

	private final List<BugReport> bugReports;

	/**
	 * Constructor.
	 * @param bugTrap The BugTrap in which this project lives
	 * @param name Name of the Subsystem.
	 * @param description Description of the Subsystem.
	 * @param parent Parent of the Subsystem.
	 * @param subsystems Subsystems of the Subsystems.
	 * @param project Project of the Subsystem.
	 * @param achievedMilestone Achieved Milestone of the Subsystem.
	 */
	public Subsystem(BugTrap bugTrap, String name, String description, System parent, List<Subsystem> subsystems, Project project, AchievedMilestone achievedMilestone) {
		super(bugTrap, name, description, parent, subsystems, achievedMilestone);
		
		this.project = project;
		this.bugReports = new ArrayList<>();
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

	public void addBugReport(BugReport report) {
		this.bugReports.add(report);
	}

	public void removeBugReport(BugReport report) {
		this.bugReports.remove(report);
	}

	@Override
	public void terminate() {
		bugTrap.getBugReportManager().deleteBugReportsForSystem(this);
		bugTrap.getNotificationManager().deleteRegistrationsForObservable(this);

		super.terminate();
		project = null;
	}

	@Override
	public List<IBugReport> getBugReports() {
		List<IBugReport> reports = new ArrayList<>();
		reports.addAll(this.bugReports);
		for (ISubsystem s : subsystems)
			reports.addAll(s.getBugReports());
		return reports;
	}
}
