package model.projects;

import java.util.ArrayList;
import java.util.List;

import model.BugTrap;
import model.Milestone;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.Observable;
import model.notifications.observers.Observer;
import model.notifications.signalisations.Signalisation;
import model.projects.builders.SubsystemBuilder;
import model.projects.health.HealthCalculator;
import model.projects.health.HealthCalculator1;
import model.projects.health.HealthCalculator2;
import model.projects.health.HealthCalculator3;
import model.projects.health.HealthIndicator;

/**
 * This class represents a system in BugTrap.
 * A system is a project or subsystem, both can be composed of subsystems.
 */
public abstract class System implements ISystem, Observable, Observer {

	protected BugTrap bugTrap; // BugTrap

	protected String name;		//System name.
	protected String description;	//System description.
	protected System parent;		//Parent System, if any.
	protected final List<Subsystem> subsystems;	//Subsystems.
	protected AchievedMilestone milestone;

	protected List<Observer> observers = new ArrayList<Observer>();
	
	/**
	 * Constructor.
	 * @param name Name of the System.
	 * @param description Description of the System.
	 * @param parent Parent of this System.
	 * @param subsystems Subsystems of this System.
	 * @param milestone Milestone of the system.
	 */
	public System(BugTrap bugTrap, String name, String description, System parent, List<Subsystem> subsystems, AchievedMilestone milestone) {
		this.bugTrap 		= bugTrap;
		this.name 			= name;
		this.description 	= description;
		this.parent 		= parent;
		this.milestone		= milestone;
		this.subsystems 	= subsystems == null ? new ArrayList<>() : subsystems;
		this.milestone 		= milestone == null ? new AchievedMilestone() : milestone;
	}
	
	/**********************************************
	 * GETTERS AND SETTERS
	 **********************************************/

	@Override
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the System to the given name.
	 * @param name The new name for the System.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description of the System to the given name.
	 * @param description The new name for the System.
	 */
	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public List<ISubsystem> getAllDirectOrIndirectSubsystems() {
		ArrayList<ISubsystem> subs = new ArrayList<ISubsystem>();
		for (ISubsystem s : subsystems) {
			subs.add(s);
			for (ISubsystem ss : s.getAllDirectOrIndirectSubsystems())
				subs.add(ss);
		}
		return subs;
	}
	
	@Override
	public List<IBugReport> getAllBugReports() {
		List<IBugReport> reports = new ArrayList<>();
		reports.addAll(getBugReports());
		for (ISubsystem s : subsystems)
			reports.addAll(s.getAllBugReports());
		return reports;
	}
	
	/**
	 * 
	 * @return A list of the direct bug reports
	 */
	public abstract List<IBugReport> getBugReports();

	@Override
	public AchievedMilestone getAchievedMilestone() {
		return milestone;
	}
	
	/**********************************************
	 * ACHIEVED MILESTONES
	 **********************************************/

	/**
	 * Declare an Achieved Milestone for the System.
	 * @param numbers The numbers for the Achieved Milestone.
	 */
	public void declareAchievedMilestone(List<Integer> numbers) {
		if (numbers == null || numbers.isEmpty()) throw new IllegalArgumentException("Numbers can not be null or empty!");
		
		//Check if new achieved milestone is less than target milestones of bugreports in progress
		List<IBugReport> bugreports = getAllBugReports();
		AchievedMilestone achieved = new AchievedMilestone(numbers);
		for (IBugReport bugreport : bugreports) {
			BugTag tag = bugreport.getBugTag();
			if(tag == BugTag.CLOSED || tag == BugTag.NOTABUG || tag == BugTag.DUPLICATE){
				continue;
			}
			
			Milestone target = ((BugReport) bugreport).getTargetMilestone();
			if(target != null && achieved.compareTo(target) >= 0){
				throw new IllegalArgumentException("The new declared achieved milestone should be less than a target milestone of a bugreport in progress");
			}
		}
		
		//Check if the new milestone is larger than the current AND it is equal or less than the highest milestone of the bugreports
		AchievedMilestone highest = highestAchievedMilestone();
		if ((highest == null) || achieved.compareTo(highest) <= 0 && (this.milestone == null || achieved.compareTo(this.milestone) >= 0)) 
			milestone = achieved;
		else
			throw new IllegalArgumentException("The given milestone should be equal to or less than the highest milestone of its (in)direct subsystems and the declared milestone must be larger than the current milestone.");
	}

	private AchievedMilestone highestAchievedMilestone() {
		AchievedMilestone highest = null;
		for (ISubsystem s : getAllDirectOrIndirectSubsystems()) {
			if (highest == null || s.getAchievedMilestone().compareTo(highest) == 1)
				highest = s.getAchievedMilestone();
		}
		return highest;
	}
	
	/**********************************************
	 * EQUALS
	 **********************************************/

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof System))
			return false;

		System sys = (System)o;

		if (this.getSubsystems().size() != sys.getSubsystems().size())
			return false;

		if (!this.getName().equals(sys.getName()))
			return false;
		if (!this.getDescription().equals(sys.getDescription()))
			return false;
		if ((this.getParent() == null ^ sys.getParent() == null))
			return false;
		if (this.getParent() != null && sys.getParent() != null && !this.parent.simpleEquals(sys.getParent()))
			return false;

		for (ISubsystem sub : this.getSubsystems()) {
			boolean foundEquals = false;
			for (ISubsystem sub2 : sys.getSubsystems()) {
				if (sub.equals(sub2)) {
					foundEquals = true;
					break;
				}
			}

			if (!foundEquals)
				return false;
		}

		return true;
	}

	// Simple equals only travels up and does not compare the subsystems.
	// Otherwise endless loop:
	// Subsystem compare subsystems, those compare parents, those compare subs etc...
	private boolean simpleEquals(Object o) {
		if (!(o instanceof System))
			return false;

		System sys = (System)o;

		if (this.getSubsystems().size() != sys.getSubsystems().size())
			return false;

		if (!this.getName().equals(sys.getName()))
			return false;
		if (!this.getDescription().equals(sys.getDescription()))
			return false;
		if (!this.getAchievedMilestone().equals(sys.getAchievedMilestone()))
			return false;
		if ((this.getParent() == null ^ sys.getParent() == null))
			return false;
		if (this.getParent() != null && sys.getParent() != null && !this.parent.simpleEquals(sys.getParent()))
			return false;

		return true;
	}
	
	/**********************************************
	 * OBSERVERS
	 **********************************************/
	
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
	public void notifyObservers(Signalisation signalisation) {
		if (this.parent != null)
			this.parent.signal(signalisation);

		for (Observer observer : this.observers)
			observer.signal(signalisation);
	}
	
	@Override
	public void signal(Signalisation signalisation) {
		notifyObservers(signalisation);
	}
	
	/**********************************************
	 * HEALTH AND BUGIMPACT
	 **********************************************/

	/**
	 * Returns the bug impact of this system.
	 * @return a double representing the bug impact
	 */
	public abstract double getBugImpact();
	
	/**
	 * Returns a health indicator for this system.
	 * @param calculator
	 * @return an indicator that indicates the health of the system
	 */
	public HealthIndicator getHealthIndicator(HealthCalculator calculator){
		return calculator.calculateHealth(this);
	}
	
	@Override
	public List<HealthIndicator> getHealthIndicators(){
		List<HealthIndicator> indicators = new ArrayList<HealthIndicator>();
		HealthCalculator[]  algorithms = { new HealthCalculator1(), new HealthCalculator2(), new HealthCalculator3()};
		
		for (HealthCalculator healthCalculator : algorithms) {
			indicators.add(getHealthIndicator(healthCalculator));
		}
		
		return indicators;
	}
	
	/**********************************************
	 * OTHER
	 **********************************************/
	
	/**
	 * Get all siblings of the given system. The given system should be a subsystem of this system.
	 */
	public List<ISubsystem> getSiblings(ISubsystem sub) {
		if (!subsystems.contains(sub)) throw new IllegalArgumentException("The given subsystem should be a subsystem of this system.");

		List<ISubsystem> siblings = new ArrayList<>();
		for (ISubsystem s : subsystems)
			if (s != sub)
				siblings.add(s);
		return siblings;
	}

	/**
	 * Creates a subsystem in the system with a given name and description
	 * @param name			The name of the subsystem
	 * @param description	The description of the subsystem
	 */
	public void createSubsystem(String name, String description) {
		if (name == null || description == null)
			throw  new IllegalArgumentException("Arguments should not be null.");
		
		(new SubsystemBuilder(bugTrap))
			.setDescription(description)
			.setName(name)
			.setParent(this)
			.getSubsystem();
	}
	
	/**
	 * Returns the verison of the system
	 * @return The Version of this system.
	 */
	public abstract Version getVersion();

	/**********************************************
	 * OTHER
	 **********************************************/
	
	/**
	 * Terminates this system.
	 * Subclasses should always call the super method
	 */
	public void terminate() {
		parent = null;
		subsystems.clear();
		observers.clear();
	}
}