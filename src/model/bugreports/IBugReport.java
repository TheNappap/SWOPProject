package model.bugreports;

import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Commentable;
import model.projects.ISubsystem;
import model.users.IUser;

import java.util.Date;
import java.util.List;

/**
 * Interface for the BugReport object.
 */
public interface IBugReport extends Comparable<IBugReport>, Commentable {

    String getDescription();
    String getTitle();
    Date getCreationDate();
    ISubsystem getSubsystem();
    BugTag getBugTag();
    List<IUser> getAssignees();
    List<IBugReport> getDependsOn();
    IUser getIssuedBy();
}
