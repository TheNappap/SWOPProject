package bugreports.forms;

import bugreports.BugReport;
import bugreports.Comment;

public class CommentCreationForm implements Form {

	private String initialOrReply;
	private BugReport commentOn;
	private Comment comment;
	private String text;
	
	public CommentCreationForm() {
		this.initialOrReply = null;
		this.commentOn		= null;
		this.comment 		= null;
		this.text			= null;
	}

	//Getters and Setters
	
	@Override
	public boolean allVarsFilledIn() {
		return getInitialOrReply() == null &&
				getCommentOn() == null &&
				getComment() == null &&
				getText() == null;
	}

	public String getInitialOrReply() {
		return initialOrReply;
	}

	public void setInitialOrReply(String initialOrReply) {
		if (!initialOrReply.equals("initial") && !initialOrReply.equals("reply")) throw new NullPointerException("Given initialOrReply must be \"initial\" or \"reply\"");
		
		this.initialOrReply = initialOrReply;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public void setComment(Comment comment) {
		if (getInitialOrReply().equals("initial")) 
			throw new IllegalArgumentException("No comment should be given when this is a InitialComment!");
		if (comment == null) 
			throw new NullPointerException("Given comment is null.");
		
		this.comment = comment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text == null) throw new NullPointerException("Given text is null.");
		
		this.text = text;
	}

	public BugReport getCommentOn() {
		return commentOn;
	}

	public void setCommentOn(BugReport commentOn) {
		if (commentOn == null) throw new NullPointerException("Given commentOn is null.");
		this.commentOn = commentOn;
	}

}