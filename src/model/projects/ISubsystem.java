package model.projects;

import java.util.List;

import model.bugreports.IBugReport;

/**
 * Interface for the Subsystem class.
 */
public interface ISubsystem extends ISystem {

	/**
	 * 
	 * @return The Project this Subsystem belongs to.
	 */
    public IProject getProject();
    
    /**
	 * Merges this subsystem with a given subsystem that is a child, parent or sibling of this subsystem.
	 * The new merged subsystem gets a given name and description.
	 * @param name
	 * @param description
	 * @param iSubsystem
	 */
    public void merge(String name, String description, ISubsystem iSubsystem);

	/**
	 * Returns a list of all subsystems this subsystem can merge with.
	 * @return
     */
	public List<ISubsystem> getMergeableWith();
	
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
	public void split(String nameFor1, String nameFor2, String descriptionFor1, String descriptionFor2,
			List<IBugReport> bugReportsFor1, List<ISubsystem> subsystemsFor1);
}
