package bugreports;

import java.util.Date;

public class InitialComment extends Comment {

	private BugReport commentOn;
	
	InitialComment(String text, Date creationDate) {
		super(text, creationDate);
	}
	
	//Getters and Setters
	
	public BugReport commentOn() {
		return commentOn;
	}
	
	void setBugReport(BugReport bugReport) {
		this.commentOn = bugReport;
	}
}