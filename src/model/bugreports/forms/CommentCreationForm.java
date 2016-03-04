package model.bugreports.forms;

import model.Form;
import model.bugreports.comments.Commentable;

public class CommentCreationForm implements Form {

	//Information needed to create a Comment.
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
	}

	//Getters and Setters

	public String getText() {
		return text;
	}

	public void setText(String text) {
		assert (text != null) : "Text is null.";
		
		this.text = text;
	}

	public Commentable getCommentable() {
		return commentable;
	}

	public void setCommentable(Commentable commentable) {
		assert (commentable != null) : "Commentable is null.";
		
		this.commentable = commentable;
	}
	
}