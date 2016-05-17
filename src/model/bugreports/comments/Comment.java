package model.bugreports.comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.BugReport;

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
		if (commentText == null)
			throw new IllegalArgumentException("Comment should not be null.");
		
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