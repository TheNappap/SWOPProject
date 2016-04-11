package model.projects;

import java.util.ArrayList;
import java.util.List;

import model.notifications.Observable;
import model.notifications.Observer;

/**
 * This class represents a system in BugTrap.
 * A system is a project or subsystem, both can be composed of subsystems.
 */
public abstract class System implements ISystem, Observable {

	protected String name;		//System name.
	protected String description;	//System description.
	protected final System parent;		//Parent System, if any.
	protected final List<Subsystem> subsystems;	//Subsystems.
	
	protected List<AchievedMilestone> milestones;

	protected List<Observer> observers = new ArrayList<Observer>();
	
	public System(String name, String description, System parent, List<Subsystem> subsystems, List<AchievedMilestone> milestones) {
		this.name 			= name;
		this.description 	= description;
		this.parent 		= parent;
		this.subsystems		= subsystems;
		this.milestones		= milestones;
	}

	@Override
	public void attach(Observer observer) {
		if (!this.observers.contains(observer))
			this.observers.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		if (this.observers.contains(observer))
			this.observers.remove(observer);
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
	public List<AchievedMilestone> getAchievedMilestones() {
		List<AchievedMilestone> copy = new ArrayList<>(); copy.addAll(milestones);
		return copy;
	}

	public void declareAchievedMilestone(List<Integer> numbers) {
		milestones.add(new AchievedMilestone(numbers));
	}

	public void signalNewBugReport(String subsystemName) {
		if (this.parent != null)
			this.parent.signalNewBugReport(subsystemName);

		for (Observer observer : this.observers)
			if (observer.isCreateBugReportObserver())
				observer.signal("New bug report created for system " + subsystemName);
	}

	public void signalNewComment(String bugReportName) {
		if (this.parent != null)
			this.parent.signalNewComment(bugReportName);

		for (Observer observer : this.observers) {
			if (observer.isCreateCommentObserver()) {
				observer.signal("New comment or reply to comment created on bug report '" + bugReportName + "'");
			}
		}
	}
}