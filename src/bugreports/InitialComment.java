package bugreports;

import java.util.Date;

public class InitialComment extends Comment {

	private BugReport commentOn;
	
	InitialComment(String text, BugReport commentOn, Date creationDate) {
		super(text, creationDate);
		setCommentOn(commentOn);
	}
	
	//Getters and Setters
	
	public BugReport commentOn() {
		return commentOn;
	}
	
	void setCommentOn(BugReport bugReport) {
		this.commentOn = bugReport;
	}
}