package model.projects;

import java.util.List;

import model.notifications.Observable;

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
}

