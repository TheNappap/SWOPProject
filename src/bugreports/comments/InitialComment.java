package bugreports.comments;

import bugreports.BugReport;

public class InitialComment extends Comment {

	private final BugReport commentOn; //On which BugReport is this Comment on.
	
	public InitialComment(String text, BugReport commentOn) {
		super(text);
		
		this.commentOn = commentOn;
	}
	
	//Getters and Setters
	
	public BugReport commentOn() {
		return commentOn;
	}

}