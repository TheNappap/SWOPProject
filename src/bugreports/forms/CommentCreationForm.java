package bugreports.forms;

import bugreports.BugReport;
import bugreports.Comment;

public class CommentCreationForm implements Form {

	//Information needed to create a Comment.
	private String initialOrReply;	//Is the comment a reply to an existing comment or a fresh one?
	private BugReport commentOn;	//About what BugReport is this comment about.
	private Comment comment;	//If it's a reply, what Comment does it reply to?
	private String text;	//Comment text.
	
	public CommentCreationForm() {
		this.initialOrReply = null;
		this.commentOn		= null;
		this.comment 		= null;
		this.text			= null;
	}
	
	@Override
	public boolean allVarsFilledIn() {
		return getInitialOrReply() != null &&
				getCommentOn() != null &&
				getComment() != null &&
				getText() != null;
	}

	//Getters and Setters
	
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