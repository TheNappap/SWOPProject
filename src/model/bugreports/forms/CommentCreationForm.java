package model.bugreports.forms;

import model.Form;
import model.bugreports.comments.ICommentable;

public class CommentCreationForm implements Form {

	//Information needed to create a Comment.
	private ICommentable commentable; //Object on which Comment is made.
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
		if (text == null) throw new NullPointerException("Text is null.");
		
		this.text = text;
	}

	public ICommentable getCommentable() {
		return commentable;
	}

	public void setCommentable(ICommentable commentable) {
		if (commentable == null) throw new NullPointerException("Commentable is null");
		
		this.commentable = commentable;
	}
}