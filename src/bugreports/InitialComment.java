package bugreports;

public class InitialComment extends Comment {

	private BugReport commentOn;
	
	InitialComment(String text, BugReport commentOn) {
		super(text);
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