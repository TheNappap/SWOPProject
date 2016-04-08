package model.bugreports;

import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Commentable;
import model.notifications.Observable;
import model.projects.ISubsystem;
import model.users.IUser;

/**
 * Interface for the BugReport object.
 */
public interface IBugReport extends Comparable<IBugReport>, Commentable, Observable {

    String getDescription();
    String getTitle();
    Date getCreationDate();
    ISubsystem getSubsystem();
    BugTag getBugTag();
    List<IUser> getAssignees();
    List<IBugReport> getDependsOn();
    IUser getIssuedBy();
}
