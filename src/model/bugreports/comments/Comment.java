package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.BugReport;
import model.notifications.observers.Observer;
import model.notifications.signalisations.Signalisation;
import model.projects.Subsystem;

/**
 * Class that represents a Comment.
 * 
 */
public class Comment implements Commentable { //A Comment can be commented on.

	//All immutable.
	private final Date creationDate;				//Creation Date of the Comment.
	private final List<Comment> comments;			//Comments to this Comment.
	private final String text;						//Text.
	private final BugReport bugReport;
	
	private List<Observer> observers = new ArrayList<Observer>();
	
	/**  
	 * Constructor.  
	 * @param text The text of this Comment.
	 */  
	public Comment(BugReport bugReport, String text) {
		this.creationDate = new Date();
		this.comments 	  = new ArrayList<Comment>();
		this.text 		  = text;
		this.bugReport	  = bugReport;
	}
	
	@Override
	public void addComment(String commentText) {
		comments.add(new Comment(bugReport, commentText));
	}

	/**
	 * 
	 * @return The text of this Comment.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * 
	 * @return creation Date of this Comment.
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public List<Comment> getComments() {
		return comments;
	}

	@Override
	public void attach(Observer observer) {
		if (!this.observers.contains(observer))
			this.observers.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}

	@Override
	public void notifyObservers(Signalisation signalisation) {
		for (Observer observer : this.observers)
			observer.signal(signalisation);
		
		 ((Subsystem) bugReport.getSubsystem()).signal(signalisation);
	}
	
	/**
	 * Terminates this comment
	 */
	public void terminate() {
		for (Comment comment : comments) {
			comment.terminate();
		}
		comments.clear();
	}
}