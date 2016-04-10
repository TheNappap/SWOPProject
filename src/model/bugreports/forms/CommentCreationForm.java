package model.bugreports.forms;

import model.Form;
import model.bugreports.IBugReport;
import model.bugreports.comments.Commentable;

public class CommentCreationForm implements Form {

	//Information needed to create a Comment.
	private IBugReport report;
	private Commentable commentable; //Object on which Comment is made.
	private String text;	//Comment text.
	
	public CommentCreationForm() {
		//Explicitly setting this to null.
		this.commentable	= null;
		this.text			= null;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (getCommentable() == null) throw new NullPointerException("Commentable is null");
		if (getText() == null) throw new NullPointerException("Text is null");
		if (getBugReport() == null) throw new NullPointerException("Bug report is null.");
	}

	//Getters and Setters

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) throw new NullPointerException("Text is null.");
		
		this.text = text;
	}

	public Commentable getCommentable() {
		return commentable;
	}

	public void setCommentable(Commentable commentable) {
		if (commentable == null) throw new NullPointerException("Commentable is null");
		
		this.commentable = commentable;
	}

	public IBugReport getBugReport() {
		return report;
	}

	public void setBugReport(IBugReport report) {
		if (report == null) throw new NullPointerException("Report is null");

		this.report = report;
	}
}