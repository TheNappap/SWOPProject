package model.projects;

import java.util.ArrayList;
import java.util.List;

import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;

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
	
	/**
	 * Splits a subsystem into two new subsystems with given names and descriptions.
	 * The first new subsystem receives the given bug reports and subsystems.
	 * The second new subsystem receives the remaining bug reports and subsystems.
	 * @param nameFor1 The name for the first new subsystem
	 * @param nameFor2 The name for the second new subsystem
	 * @param descriptionFor1 The description for the first new subsystem
	 * @param descriptionFor2 The description for the second new subsystem
	 * @param bugReportsFor1 The bug reports for the first new subsystem
	 * @param subsystemsFor1 The subsystems for the first new subsystem
	 */
	public void splitSubsystem(String nameFor1, String nameFor2, String descriptionFor1, String descriptionFor2,
			List<IBugReport> bugReportsFor1, List<ISubsystem> subsystemsFor1){
		Subsystem sub1 = new Subsystem(bugTrap, nameFor1, descriptionFor1, parent, null, project, getAchievedMilestone());
		Subsystem sub2 = new Subsystem(bugTrap, nameFor2, descriptionFor2, parent, null, project, getAchievedMilestone());
		
		for (Subsystem subsystem : this.subsystems) {	
			if(subsystemsFor1.contains(subsystem)){
				//subsystem for first new subsystem
				sub1.subsystems.add(subsystem); 
				this.subsystems.remove(subsystem);
			}else{
				//subsystem for second new subsystem
				sub2.subsystems.add(subsystem); 
				this.subsystems.remove(subsystem);	
			}
		}
		
		//TODO BUGREPORTS!
		
		parent.subsystems.add(sub1);
		parent.subsystems.add(sub2);
		
		terminate();
		parent.subsystems.remove(this);
	}
	

	@Override
	public List<IBugReport> getBugReports() {
		List<IBugReport> reports = new ArrayList<>();
		reports.addAll(this.bugReports);
		for (ISubsystem s : subsystems)
			reports.addAll(s.getBugReports());
		return reports;
	}
	
	/**
	 * Merges this subsystem with a given subsystem that is a child, parent or sibling of this subsystem.
	 * The new merged subsystem gets a given name and description.
	 * @param name
	 * @param description
	 * @param iSubsystem
	 */
	public void mergeSubsystem(String name, String description, ISubsystem iSubsystem){
		//if parent and child merge, the parent has the responsibility
		if(this.getParent().equals(iSubsystem)){
			((Subsystem) iSubsystem).mergeSubsystem(name, description, this);
			return;
		}
		
		AchievedMilestone achievedMilestone = this.getAchievedMilestone();
		if(iSubsystem.getAchievedMilestone().compareTo(achievedMilestone) < 0){
			achievedMilestone = iSubsystem.getAchievedMilestone();
		}
		
		Subsystem newSubsystem = new Subsystem(bugTrap, name, description, parent, null, project, achievedMilestone);
		
		//move subsystems from given subsystem (=sibling or child)
		Subsystem subsystem = ((Subsystem) iSubsystem);
		for (Subsystem sub : subsystem.subsystems) {
			newSubsystem.subsystems.add(sub);
			subsystem.subsystems.remove(sub);
		}
		//move subsystems from this subsystem (=sibling or parent)
		for (Subsystem sub : this.subsystems) {
			if(!sub.equals(subsystem)){
				newSubsystem.subsystems.add(sub);
				subsystem.subsystems.remove(sub);
			}
		}
		
		//TODO BUGREPORTS!
		
		//delete given subsystem (=child or sibling)
		System parent = subsystem.parent;
		subsystem.terminate();
		parent.subsystems.remove(subsystem);
		//delete this subsystem (=parent or sibling)
		terminate();
		this.parent.subsystems.remove(this);
	}
}
