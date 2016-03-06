package model.bugreports.comments;

import model.bugreports.BugReport;

public class InitialComment extends Comment {

	private final BugReport commentOn; //On which BugReport is this Comment on.
	
	/**  
	 * Constructor.  
	 * @param text Text of this InitialComment.  
	 * @param commentOn BugReport on which this InitialComment is on.  
	 */
	public InitialComment(String text, BugReport commentOn) {
		super(text);
		
		this.commentOn = commentOn;
	}
	
	//Getters and Setters
	
	public BugReport commentOn() {
		return commentOn;
	}

}