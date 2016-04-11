package model.projects;

import java.util.List;

/**
 * Interface for the System class.
 */
public interface ISystem {

    public String getName();
    public String getDescription();
    public ISystem getParent();
    public List<ISubsystem> getSubsystems();
    public List<AchievedMilestone> getAchievedMilestones();
    
    
    public List<ISubsystem> getAllDirectOrIndirectSubsystems();
}
