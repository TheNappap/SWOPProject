package model.projects;

import model.notifications.Observable;

public interface ISubsystem extends ISystem {

    public IProject getProject();
}
