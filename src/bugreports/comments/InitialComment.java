package bugreports.comments;

import bugreports.BugReport;

public class InitialComment extends Comment {

	private BugReport commentOn;
	
	public InitialComment(String text, BugReport commentOn) {
		super(text);
		setCommentOn(commentOn);
	}
	
	//Getters and Setters
	
	public BugReport commentOn() {
		return commentOn;
	}
	
	public void setCommentOn(BugReport bugReport) {
		this.commentOn = bugReport;
	}

}