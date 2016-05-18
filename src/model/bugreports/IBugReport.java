package model.bugreports;

import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.bugreports.comments.ICommentable;
import model.notifications.Observable;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.users.IUser;

/**
 * Interface for the BugReport class.
 */
public interface IBugReport extends Comparable<IBugReport>, ICommentable, Observable {

	/**
	 * 
	 * @return Description of the BugReport
	 */
    String getDescription();
    
    /**
     * 
     * @return Title of the BugReport
     */
    String getTitle();
    
    /**
     * 
     * @return Creation Date of the BugReport.
     */
    Date getCreationDate();
    
    /**
     * 
     * @return Subsystem of the BugReport.
     */
    ISubsystem getSubsystem();
    
    /**
     * 
     * @return BugTag of the BugReport.
     */
    BugTag getBugTag();
    
    /**
     * @return Assignees of the BugReport.
     */
    List<IUser> getAssignees();
    
    /**
     * @return Dependencies of the BugReport
     */
    List<IBugReport> getDependsOn();
    
    /**
     * 
     * @return Issuer of the BugReport.
     */
    IUser getIssuedBy();
    
    /**
     * 
     * @return Tests of the BugReport.
     */
	List<ITest> getTests();
	
	/**
	 * 
	 * @return Patches of the BugReport.
	 */
	List<IPatch> getPatches();
	
	/**
	 * 
	 * @return Error message of the BugReport.
	 */
    String getErrorMessage();
    
    /**
     * 
     * @return Stack Trace of the BugReport
     */
    String getStackTrace();
    
    /**
     * 
     * @return How to reproduce the Bug of the BugReport.
     */
    String getReproduction();
    
    /**
     * 
     * @return Target Milestone of the BugReport.
     */
    TargetMilestone getTargetMilestone();
    
    /**
     * 
     * @return Project of the BugReport.
     */
    IProject getProject();
    
    /**
     * 
     * @return Comments of the BugReport
     */
    List<Comment> getComments();
}
