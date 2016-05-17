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
	 * @param achievedMilestone Achieved Milestone of the Subsystem.
	 */
	public Subsystem(BugTrap bugTrap, String name, String description, System parent, List<Subsystem> subsystems, AchievedMilestone achievedMilestone) {
		super(bugTrap, name, description, parent, subsystems, achievedMilestone);
		
		System system = parent;
		while(system.getParent() != null){
			system = (System) system.getParent();
		}
		this.project = (Project) system;
		this.bugReports = new ArrayList<>();
		parent.subsystems.add(this);
	}
	
	/**********************************************
	 * GETTERS AND SETTERS
	 **********************************************/

	@Override
	public IProject getProject() {
		return project;
	}
	
	@Override
	public List<IBugReport> getBugReports() {
		List<IBugReport> reports = new ArrayList<>();
		reports.addAll(this.bugReports);
		return reports;
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

	@Override
	public Version getVersion() {
		return ((System) getParent()).getVersion();
	}

	/**
	 * Sets parent
	 * @param parent
	 */
	private void setParent(System parent) {
		this.parent = parent;
	}
	
	/**********************************************
	 * BUGREPORTS
	 **********************************************/

	/**
	 * adds a bug report to the subsystem
	 * @param report
	 */
	public void addBugReport(BugReport report) {
		this.bugReports.add(report);
		report.setSubsystem(this);
	}

	/**
	 * removes a bug report from the subsystem
	 * @param report
	 */
	public void removeBugReport(BugReport report) {
		this.bugReports.remove(report);
	}
	
	/**********************************************
	 * SPLIT AND MERGE
	 **********************************************/

	@Override
	public void split(String nameFor1, String nameFor2, String descriptionFor1, String descriptionFor2,
			List<IBugReport> bugReportsFor1, List<ISubsystem> subsystemsFor1){
		Subsystem sub1 = new Subsystem(bugTrap, nameFor1, descriptionFor1, parent, null, getAchievedMilestone());
		Subsystem sub2 = new Subsystem(bugTrap, nameFor2, descriptionFor2, parent, null, getAchievedMilestone());
		
		//split subsystems
		for (int i = 0; i < this.subsystems.size(); i++) {
			Subsystem subsystem = this.subsystems.get(i);
			if(subsystemsFor1.contains(subsystem)){
				//subsystem for first new subsystem
				sub1.subsystems.add(subsystem); 
			}else{
				//subsystem for second new subsystem
				sub2.subsystems.add(subsystem); 
			}
			this.subsystems.remove(subsystem);
			i--;
		}
		
		//split bug reports
		for (int i = 0; i < this.bugReports.size(); i++) {
			BugReport bugReport = this.bugReports.get(i);
			if(bugReportsFor1.contains(bugReport)){
				//bug report for first new subsystem
				sub1.addBugReport(bugReport);
			}else{
				//bug report for second new subsystem
				sub2.addBugReport(bugReport);
			}
			this.removeBugReport(bugReport);
			i--;
		}
		
		//add new subsystems
		parent.subsystems.add(sub1);
		parent.subsystems.add(sub2);
		
		parent.subsystems.remove(this);
		this.terminate();
	}
	
	@Override
	public List<ISubsystem> mergeableWith() {
		List<ISubsystem> merge = new ArrayList<>();
		merge.addAll(parent.getSiblings(this));
		merge.addAll(subsystems);
		if (parent.getParent() != null) // Our parent is not a project
			merge.add((ISubsystem)parent);
		return merge;
	}

	@Override
	public void merge(String name, String description, ISubsystem iSubsystem){
		//if parent and child merge, the parent has the responsibility
		if(this.getParent().equals(iSubsystem)){
			((Subsystem) iSubsystem).merge(name, description, this);
			return;
		}
		
		//new milestone is smallest achieved milestone
		AchievedMilestone achievedMilestone = this.getAchievedMilestone();
		if(iSubsystem.getAchievedMilestone().compareTo(achievedMilestone) < 0){
			achievedMilestone = iSubsystem.getAchievedMilestone();
		}
		
		Subsystem newSubsystem = new Subsystem(bugTrap, name, description, parent, null, achievedMilestone);
		
		Subsystem subsystem = ((Subsystem) iSubsystem);
		//Move all subsystems to the new merged subsystem
		this.moveSubsystemsAndBugReportsTo(newSubsystem, subsystem);
		subsystem.moveSubsystemsAndBugReportsTo(newSubsystem, this);
		
		//delete given subsystem (=child or sibling)
		System parent = subsystem.parent;
		parent.subsystems.remove(subsystem);
		subsystem.terminate();
		//delete this subsystem (=parent or sibling)
		this.parent.subsystems.remove(this);
		this.terminate();
	}

	/**
	 * Moves all subsystems and bug reports to a given new subsystem as part of merging two subsystems.
	 * If the other subsystem in the merge is a child of this system, it isn't moved.
	 * @param newSubsystem The new subsystem resulting in a merge
	 * @param otherSubsystem The other subsystem merging with this subsystem
	 */
	private void moveSubsystemsAndBugReportsTo(Subsystem newSubsystem, Subsystem otherSubsystem) {
		List<Subsystem> subs = new ArrayList<>();
		subs.addAll(this.subsystems);
		for (Subsystem sub : subs) {
			if(!sub.equals(otherSubsystem)){
				sub.moveToNewParent(newSubsystem);
			}
		}
		List<BugReport> reports = new ArrayList<>();
		reports.addAll(this.bugReports);
		for (BugReport bugReport : reports) {
			newSubsystem.addBugReport(bugReport);
			this.removeBugReport(bugReport);
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
	
	/**********************************************
	 * OTHER
	 **********************************************/

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
	
		// System.equals compares parents until the root.
		// If same root, then same project.
		// No need to compare here.
		return true;
	}

	@Override
	public void terminate() {
		bugTrap.getBugReportManager().deleteBugReportsForSystem(this);
		bugTrap.getNotificationManager().removeObservable(this);
	
		super.terminate();
		project = null;
	}
}
