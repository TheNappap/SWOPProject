package model.projects;

import java.util.List;

/**
 * Interface for the system objects.
 */
public interface ISystem {

    String getName();
    String getDescription();
    ISystem getParent();
    List<ISubsystem> getSubsystems();
    Version getVersion();
    List<ISubsystem> getAllDirectOrIndirectSubsystems();
}
