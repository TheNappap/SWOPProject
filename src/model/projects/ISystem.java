package model.projects;

import java.util.List;

import model.bugreports.IBugReport;
import model.notifications.Observable;
import model.projects.health.HealthIndicator;

/**
 * Interface for the System class.
 */
public interface ISystem extends Observable{

	/**
	 * 
	 * @return The name of the System.
	 */
    String getName();
    
    /**
     * 
     * @return The description of the System.
     */
    public String getDescription();
    
    /**
     * 
     * @return The parent of the System.
     */
    public ISystem getParent();
    
    /**
     * 
     * @return The subsystems of the System.
     */
    public List<ISubsystem> getSubsystems();
    
    /**
     * 
     * @return The Achieved Milestone of the System.
     */
    public AchievedMilestone getAchievedMilestone();
    
    /**
     * 
     * @return All direct or indirect Subsystems of the System.
     */
    public List<ISubsystem> getAllDirectOrIndirectSubsystems();

    /**
     *
     * @return List of all bug reports related to this system or its subsystems.
     */
    public List<IBugReport> getBugReports();
    
    /**
     * 
     * @return the health of the system
     */
    public HealthIndicator getHealth();
}

