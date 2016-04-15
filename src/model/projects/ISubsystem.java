package model.projects;

/**
 * Interface for the Subsystem class.
 */
public interface ISubsystem extends ISystem {

	/**
	 * 
	 * @return The Project this Subsystem belongs to.
	 */
    public IProject getProject();
}
