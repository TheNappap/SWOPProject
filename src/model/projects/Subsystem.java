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
	
	/**
	 * Sets parent
	 * @param parent
	 */
	private void setParent(System parent) {
		this.parent = parent;
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
		
		//split subsystems
		for (Subsystem subsystem : this.subsystems) {	
			if(subsystemsFor1.contains(subsystem)){
				//subsystem for first new subsystem
				sub1.subsystems.add(subsystem); 
			}else{
				//subsystem for second new subsystem
				sub2.subsystems.add(subsystem); 
			}
			this.subsystems.remove(subsystem);
		}
		
		//split bug reports
		for (BugReport bugReport : this.bugReports) {	
			if(bugReportsFor1.contains(bugReport)){
				//bug report for first new subsystem
				sub1.bugReports.add(bugReport);
			}else{
				//bug report for second new subsystem
				sub2.bugReports.add(bugReport);
			}
			this.bugReports.remove(bugReport);	
		}
		
		//add new subsystems
		parent.subsystems.add(sub1);
		parent.subsystems.add(sub2);
		
		this.terminate();
		parent.subsystems.remove(this);
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
		
		//new milestone is smallest achieved milestone
		AchievedMilestone achievedMilestone = this.getAchievedMilestone();
		if(iSubsystem.getAchievedMilestone().compareTo(achievedMilestone) < 0){
			achievedMilestone = iSubsystem.getAchievedMilestone();
		}
		
		Subsystem newSubsystem = new Subsystem(bugTrap, name, description, parent, null, project, achievedMilestone);
		
		Subsystem subsystem = ((Subsystem) iSubsystem);
		//Move all subsystems to the new merged subsystem
		this.moveSubsystemsAndBugReportsTo(newSubsystem, subsystem);
		subsystem.moveSubsystemsAndBugReportsTo(newSubsystem, this);
		
		//delete given subsystem (=child or sibling)
		System parent = subsystem.parent;
		subsystem.terminate();
		parent.subsystems.remove(subsystem);
		//delete this subsystem (=parent or sibling)
		this.terminate();
		this.parent.subsystems.remove(this);
	}

	/**
	 * Moves all subsystems and bug reports to a given new subsystem as part of merging two subsystems.
	 * If the other subsystem in the merge is a child of this system, it isn't moved.
	 * @param newSubsystem The new subsystem resulting in a merge
	 * @param otherSubsystem The other subsystem merging with this subsystem
	 */
	private void moveSubsystemsAndBugReportsTo(Subsystem newSubsystem, Subsystem otherSubsystem) {
		for (Subsystem sub : this.subsystems) {
			if(!sub.equals(otherSubsystem)){
				sub.moveToNewParent(newSubsystem);
			}
		}
		for (BugReport bugReport : this.bugReports) {
			newSubsystem.bugReports.add(bugReport);
			this.bugReports.remove(bugReport);
		}
	}

	/**
	 * Moves this subsystem to a new parent.
	 * The subsystem lists are updated.
	 * @param parent
	 */
	private void moveToNewParent(Subsystem parent) {
		parent.subsystems.add(this);
		this.parent.subsystems.remove(this);
		setParent(parent);
	}

	@Override
	public double getBugImpact() {
		double bugImpact = 0;
		
		for (IBugReport iBugReport : bugReports) {
			double impactProduct = ((BugReport) iBugReport).getImpactProduct();
			bugImpact += impactProduct;
		}
		
		return bugImpact;
	}
}
